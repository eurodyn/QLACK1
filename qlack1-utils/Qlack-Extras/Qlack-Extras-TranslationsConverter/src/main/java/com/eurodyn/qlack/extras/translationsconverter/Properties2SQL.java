package com.eurodyn.qlack.extras.translationsconverter;

import com.eurodyn.qlack.commons.fileio.FileWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.LocaleUtils;
import org.apache.tools.ant.DirectoryScanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

/**
 * A helper application to read multiple .properties file with translations and produce an .sql script with translations
 * for the Lexicon module.
 */
public class Properties2SQL {

  private static final Options options = new Options();

  public static void main(String[] args) throws Exception {
    // Define command-line args.
    options.addOption("props", true,
        "The prefix of the Java-properties file. Linguistic versions will be discovered automatically.");
    options.addOption("modifier", true, "The name of the person providing these translations.");
    options.addOption("groupID", true, "The ID of the group where these translations belong "
        + "to. It is not necessary to specify this parameter.");
    options.addOption("noescaping", false, "Disables escaping of string for MessageFormat.");
    options.addOption("dburl", true, "The database URL to connect to.");
    options.addOption("dbuser", true, "The user for the database host to connect to.");
    options.addOption("dbpassword", true, "The password for the database host to connect to.");
    options.addOption("dbdriver", true, "The JDBC driver for the database host to connect to.");
    options.addOption("outputFile", true,
        "The file to output the produced sql script (optional). If none is provided then "
            + "a file with the prefix of the Java-properties file will be created in the same path as the properties file.");
    options.addOption("charset", true, "The charset to use for reading the properties file.");

    // Parse the command line.
    CommandLineParser parser = new PosixParser();
    CommandLine cmd;
    try {
      cmd = parser.parse(options, args);
      if ((!cmd.hasOption("props"))
          || (!cmd.hasOption("modifier"))
          || (!cmd.hasOption("dburl"))
          || (!cmd.hasOption("dbuser"))
          || (!cmd.hasOption("dbpassword"))
          || (!cmd.hasOption("dbdriver"))) {
        printHelpAndExit();
      }

      Properties2SQL app = new Properties2SQL();
      app.translate(cmd.getOptionValue("props"),
          cmd.getOptionValue("modifier"),
          cmd.hasOption("groupID") ? cmd.getOptionValue("groupID") : null,
          cmd.hasOption("noescaping"),
          cmd.getOptionValue("dburl"), cmd.getOptionValue("dbuser"),
          cmd.getOptionValue("dbpassword"),
          cmd.getOptionValue("dbdriver"), cmd.getOptionValue("outputFile"),
          cmd.getOptionValue("charset"));
    } catch (ParseException ex) {
      printHelpAndExit();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private static void printHelpAndExit() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        "java -cp QlackFuse-Extras-TranslationsConverter-2.0.jar;C:\\JavaLibs\\mysql-connector-java-5.1.21.jar "
            + "com.eurodyn.qlack.fuse.extras.trastranslationsconverter.Properties2SQL \n"
            + "-props C:\\translations\\reference_data_translations_ -modifier qlack -dburl jdbc:mysql://localhost:3306/qlack "
            + "-dbuser root -dbpassword root -dbdriver com.mysql.jdbc.Driver", options);
    System.exit(1);
  }

  public void translate(String propertiesFile, String modifier, String groupID, boolean escaping,
      String dburl, String dbuser, String dbpass, String dbdriver, String outputFile,
      String charset)
      throws Exception {
    // First find what linguistic versions of the translations are available.
    System.out.println("Checking which property files are available...");
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setIncludes(new String[]{propertiesFile + "*.properties"});
    scanner.setCaseSensitive(false);
    scanner.scan();
    String[] files = scanner.getIncludedFiles();
    Map<String, Locale> fileLocales = new HashMap<>(files.length);
    Map<String, String> dbLocales = new HashMap<>(files.length);
    for (String f : files) {
      // Remove prefix and suffix to find the locale.
      fileLocales.put(f, LocaleUtils
          .toLocale(f.substring(propertiesFile.length(), f.length() - ".properties".length())));
      System.out.println("\tFound: " + f + ", locale: " + fileLocales.get(f));
    }

    // Match locales found with the ones on the database.
    System.out.println("Matching locales found with Lexicon module...");
    Class.forName(dbdriver).newInstance();
    try (Connection conn = DriverManager.getConnection(dburl, dbuser, dbpass)) {
      System.out.println("\tConnected to the database");
      try (PreparedStatement pstmt = conn.prepareStatement("select id from lex_language where locale = ?")) {
        for (String key : fileLocales.keySet()) {
          System.out.println("\tTrying " + fileLocales.get(key).toString());
          pstmt.setString(1, fileLocales.get(key).toString());
          try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
              System.out.println("\t\tMatched to language with id " + rs.getString("id"));
              dbLocales.put(fileLocales.get(key).toString(), rs.getString("id"));
            }
          }
        }
      }
    }
    System.out.println("\tDisconnected from database.");

    // Create the SQLs.
    if (outputFile == null) {
      outputFile = propertiesFile + ".sql";
    }
    System.out.println("Generating output on " + outputFile);
    Map<String, String> processedKeys = new HashMap<>();
    StringBuilder sb = new StringBuilder();
    Properties props = new Properties();
    for (Entry<String, Locale> file : fileLocales.entrySet()) {
      Locale locale = fileLocales.get(file.getKey());
      String lgID = dbLocales.get(locale.toString());
      if (lgID == null) {
        System.out.println(
            "\t *** WARNING: Locale " + locale + " could not be matched with a language ID "
                + "in Lexicon. " + file.getKey() + " WILL NOT be included in the SQL script");
        continue;
      }
      System.out.println("\t Processing " + file + ", locale " + locale + ", language ID " + lgID);
      FileInputStream fis = new FileInputStream(file.getKey());

      InputStreamReader is = null;
      if (charset != null && Charset.availableCharsets().get(charset) != null) {
        System.out.println("Using charset " + charset + " to read file " + file.getKey());
        is = new InputStreamReader(fis, StandardCharsets.UTF_8);
      } else {
        System.out.println(
            "Using default charset " + Charset.defaultCharset().toString() + " to read file "
                + file.getKey());
        is = new InputStreamReader(fis);
      }

      props.load(is);
      for (Object o : props.keySet()) {
        String key = (String) o;
        String val = props.getProperty(key);
        String keyID = UUID.randomUUID().toString();

        if (!processedKeys.containsKey(key)) {
          sb.append("INSERT INTO lex_key (id, name, group_id, created_by, created_on)" + " VALUES ("
              + "'")
              .append(keyID).append("', " + "'").append(key).append("', ")
              .append(groupID != null ? "'" + groupID + "', " : "null, ")
              .append("'").append(modifier).append("', ").append(System.currentTimeMillis())
              .append(");\n");
          processedKeys.put(key, keyID);
        }
        sb.append("INSERT INTO lex_data (id, key_id, value, language_id, created_by,")
            .append("created_on, approved, approved_by)").append(" VALUES('")
            .append(UUID.randomUUID().toString()).append("', '").append(processedKeys.get(key))
            .append("', '").append(val).append("', '").append(lgID).append("', '").append(modifier)
            .append("', ")
            .append(System.currentTimeMillis()).append(", 1, 'system');\n");
      }

      is.close();
    }

    System.out.println("Creating file " + outputFile);
    FileWriter.writeBinary(outputFile, sb.toString().getBytes(StandardCharsets.UTF_8));
  }
}
