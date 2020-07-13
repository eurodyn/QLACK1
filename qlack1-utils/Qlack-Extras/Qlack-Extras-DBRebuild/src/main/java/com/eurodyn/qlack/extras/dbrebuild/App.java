package com.eurodyn.qlack.extras.dbrebuild;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

  private static final Options options = new Options();
  private String dbdriver = null;
  private String dburl = null;
  private String dbuser = null;
  private String dbpassword = null;
  private String properties = null;
  private boolean dbVersionAvailable = false;
  private boolean verbose = true;

  private App() {
  }

  private static void printHelpAndExit() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        "mvn -o exec:java -Dexec.mainClass=\"com.eurodyn.qlack.fuse.extras.dbrebuild.App\" -Dexec.args=\"...\"",
        options);
    System.exit(1);
  }

  public static void main(String[] args) {
    // Define command-line args.
    options.addOption("dbdriver", true, "The fully classified name of the JDBC driver.");
    options.addOption("dburl", true, "The connection URL of the database server.");
    options.addOption("dbuser", true, "The username for database server authentication.");
    options.addOption("dbpassword", true, "The user password for database server authentication.");
    options.addOption("scripts", true, "The series of script to run.");
    options.addOption("properties", true, "The properties file with the placeholder values.");
    options.addOption("silent", false, "Turns verbose mode off.");

    // Parse the command line.
    CommandLineParser parser = new PosixParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
      if (!cmd.hasOption("scripts")) {
        System.out.println("-scripts argument is required.");
        printHelpAndExit();
      }
    } catch (ParseException ex) {
      printHelpAndExit();
    }

    // Create a new instance to start operations.
    App app = new App();

    // Set values.
    if (cmd.hasOption("silent")) {
      app.verbose = false;
    }
    if (cmd.hasOption("dbdriver")) {
      app.dbdriver = cmd.getOptionValue("dbdriver");
    }
    if (cmd.hasOption("dburl")) {
      app.dburl = cmd.getOptionValue("dburl");
    }
    if (cmd.hasOption("dbuser")) {
      app.dbuser = cmd.getOptionValue("dbuser");
    }
    if (cmd.hasOption("dbpassword")) {
      app.dbpassword = cmd.getOptionValue("dbpassword");
    }
    if (cmd.hasOption("properties")) {
      app.properties = cmd.getOptionValue("properties");
    }

    try {
      // Execute regular scripts.
      app.runScripts(cmd.getOptionValue("scripts"));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(dburl, dbuser, dbpassword);

    if (!dbVersionAvailable) {
      checkDbVersionTable(con);
    }

    return con;
  }

  private void checkDbVersionTable(Connection con) throws SQLException {
    DatabaseMetaData dbmd = con.getMetaData();
    try (ResultSet rs = dbmd.getTables(null, null, "db_version", new String[]{"TABLE"})) {
      if (rs.next()) {
        dbVersionAvailable = true;
      }
    }
  }

  private void runScripts(String scripts) throws SQLException, IOException {
    try {
      Class.forName(dbdriver).newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    for (String nextScript : scripts.split(",")) {
      if (verbose) {
        System.out.println("Requested to run " + nextScript + "_vXXXX.sql.");
      }

      // Find parent dir.
      String dirPath = nextScript.indexOf(System.getProperty("file.separator")) > -1
          ? nextScript.substring(0, nextScript.lastIndexOf(System.getProperty("file.separator")))
          : System.getProperty("file.separator");
      if (verbose) {
        System.out.println("\tScripts root dir is " + dirPath + ".");
      }

      // Find scripts prefix.
      String scriptsPrefix = nextScript.indexOf(System.getProperty("file.separator")) > -1
          ? nextScript.substring(nextScript.lastIndexOf(System.getProperty("file.separator")) + 1)
          : nextScript;
      if (verbose) {
        System.out.println("\tScripts prefix is " + scriptsPrefix + ".");
      }

      // Get available scripts and sort them.
      String[] files = new File(dirPath).list(new DirFilter(scriptsPrefix + "_v\\d{4}\\.sql"));
      if ((files != null) && (files.length > 0)) {
        Arrays.sort(files, new AlphabeticComparator());
        Connection con = getConnection();
        try {
          for (String scriptFile : files) {
            if (verbose) {
              System.out.print("\t" + scriptFile + " - checking...");
              System.out.flush();
            }
            if (shouldRun(scriptFile)) {
              if (verbose) {
                System.out.println(" - running...");
              }

              Reader reader = readFile(dirPath, scriptFile);

              ScriptRunner runner = new ScriptRunner(con);
              runner.runScript(reader);
              if (verbose) {
                System.out.println(" - finished");
              }


            } else {
              if (verbose) {
                System.out.println(" - skipped");
              }
            }
          }
        } finally {
          if (con != null) {
            con.close();
          }
        }
      } else {
        System.out.println("\tDid not find any scripts to run.");
      }
    }
  }

  /**
   *
   */
  private boolean shouldRun(String scriptName) throws SQLException {
    boolean retVal = true;

    if (dbVersionAvailable) {
      // Extract component name and version from script name.
      String component = scriptName.substring(0, scriptName.lastIndexOf('_'));
      String version = scriptName
          .substring(scriptName.lastIndexOf("_v") + 2, scriptName.lastIndexOf(".sql"));
      try (Connection con = getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt
          .executeQuery("select * from db_version where dbversion = '"
              + version + "' and component = '" + component + "'")) {
        if (rs.first()) {
          retVal = false;
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }

    return retVal;
  }

  private Reader readFile(String dirPath, String scriptFile)
      throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(dirPath
        + System.getProperty("file.separator") + scriptFile))) {

      if (properties != null) {
        PropertiesLoader loader = new PropertiesLoader(properties);

        Map<String, String> tokens = loader.getPropertiesMap();
        if (tokens != null && !tokens.isEmpty()) {
          // Create pattern of the format
          String patternString = "&(" + StringUtils.join(tokens.keySet(), "|") + ")";
          Pattern pattern = Pattern.compile(patternString);

          String line = null;
          StringBuilder stringBuilder = new StringBuilder();
          String ls = System.getProperty("line.separator");
          while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);

            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
              matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
            }
            matcher.appendTail(sb);

            stringBuilder.append(sb.toString());
            stringBuilder.append(ls);

          }
          return new StringReader(stringBuilder.toString());
        }
      }

      return reader;
    }
  }
}
