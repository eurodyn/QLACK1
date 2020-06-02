package com.eurodyn.qlack.extras.translationsconverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tools.ant.DirectoryScanner;

public class XLS2SQL {

  private static final Options options = new Options();

  private final String OUTPUT_ENCODING = "UTF8";

  private void execute(String inputFile, String outputPath) throws IOException {
    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(new File(inputFile)));
    HSSFWorkbook wb = new HSSFWorkbook(fs);

    for (int i = 0; i < wb.getNumberOfSheets(); i++) {
      extractSheet(wb, i, outputPath);
    }
  }

  private void extractSheet(HSSFWorkbook wb, int sheetIndex, String outputPath)
      throws IOException {
    HSSFSheet sheet = wb.getSheetAt(sheetIndex);
    int lastRowNum = sheet.getLastRowNum();
    System.out.println("Processing sheet " + sheetIndex + ", last row is row number " + (lastRowNum
        + 1)); //+1 since lastRowNum is zero-based

    HSSFRow headRow = sheet.getRow(0);
    int lastHeadCellNum = headRow.getLastCellNum()
        - 1;         //Since getLastCellNum() returns the number of cells plus one

    System.out.println("Sheet: " + sheetIndex + ". " + (lastHeadCellNum + 1)
        + " head cells found.");   //+1 since lastHeadCellNum is zero-based
    String[] filenames = new String[lastHeadCellNum + 1];
    String[] headCellValues = new String[lastHeadCellNum + 1];

    FileOutputStream[] outputs = new FileOutputStream[lastHeadCellNum + 1];

    //Initialise outputs and filenames
    for (int fcount = 1; fcount <= lastHeadCellNum;
        fcount++) {         //No file will be created for the first column since it contains the keys
      HSSFCell currentHeadCell = headRow.getCell(fcount);
      String headCellValue = currentHeadCell.getStringCellValue();
      headCellValues[fcount] = headCellValue;
      filenames[fcount] = outputPath + File.separator + wb.getSheetName(sheetIndex) +
          headCellValue + ".properties";
      outputs[fcount] = new FileOutputStream(new File(filenames[fcount]));
      System.out.println(filenames[fcount] + " created");
    }

    for (int i = 1; i <= lastRowNum; i++) {
      HSSFRow row = sheet.getRow(i);

      if (row == null) {
        System.out.println("Row: " + i + " is null. Adding a newline to each file");
        for (int j = 1; j <= lastHeadCellNum; j++) {
          outputs[j].write("\n".getBytes(OUTPUT_ENCODING));
        }
        continue;
      }

      HSSFCell firstCell = row.getCell(0);

      for (int j = 1; j <= lastHeadCellNum; j++) {
        if (firstCell == null) {
          outputs[j].write("\n".getBytes(OUTPUT_ENCODING));
          continue;
        }
        String firstCellContent = firstCell.getStringCellValue();

        if ((firstCellContent.startsWith("#")) || (firstCellContent.trim().length() == 0)) {
          outputs[j].write((firstCellContent + "\n").getBytes(OUTPUT_ENCODING));
        } else {
          //if the translation exists for an english word x ,it is used. otherwise lang_x is used
          String value = "";
          if (row.getCell(j) != null) {
            value = row.getCell(j).getStringCellValue();
          }

          if ((row.getCell(j) == null) || (StringUtils.isEmpty(value))) {
            value = headCellValues[j] + "_" + row.getCell(1).getStringCellValue();
          }

          String outputLine = firstCellContent + "=" + value + "\n";
          outputs[j].write(outputLine.getBytes(OUTPUT_ENCODING));
        }
      }
    }

    for (int fcount = 1; fcount <= lastHeadCellNum; fcount++) {
      outputs[fcount].flush();
      outputs[fcount].close();
      System.out.println(filenames[fcount] + "  closed");
    }
  }


  public static void main(String[] args) throws Exception {
    // Define command-line args.
    options.addOption("inputFile", true, "The XLS file to parse.");
    options.addOption("outputPath", true, "The path where to output the sql files.");
    options.addOption("modifier", true, "The name of the person providing these translations.");
    options.addOption("groupIDs", true,
        "A comma separated list of the IDs of the groups where these translations belong "
            + "to. It is not necessary to specify this parameter.");
    options.addOption("noescaping", false, "Disables escaping of string for MessageFormat.");
    options.addOption("dburl", true, "The database URL to connect to.");
    options.addOption("dbuser", true, "The user for the database host to connect to.");
    options.addOption("dbpassword", true, "The password for the database host to connect to.");
    options.addOption("dbdriver", true, "The JDBC driver for the database host to connect to.");
    options.addOption("suffix", true, "An optional suffix to append to the resulting sql file(s).");

    // Parse the command line.
    CommandLineParser parser = new PosixParser();
    CommandLine cmd;
    try {
      cmd = parser.parse(options, args);
      if ((!cmd.hasOption("inputFile"))
          || (!cmd.hasOption("outputPath"))
          || (!cmd.hasOption("modifier"))
          || (!cmd.hasOption("dburl"))
          || (!cmd.hasOption("dbuser"))
          || (!cmd.hasOption("dbpassword"))
          || (!cmd.hasOption("dbdriver"))) {
        printHelpAndExit();
      }

      XLS2Properties xls2prop = new XLS2Properties();
      String outputPath = cmd.getOptionValue("outputPath");

      String[] sheetNames = xls2prop.translate(cmd.getOptionValue("inputFile"), outputPath);
      String[] groupIds =
          cmd.hasOption("groupIDs") ? cmd.getOptionValue("groupIDs").split(",") : null;
      Properties2SQL prop2sql = new Properties2SQL();
      for (int i = 0; i < sheetNames.length; i++) {
        String name = sheetNames[i];
        String groupId = ((groupIds != null) && (groupIds.length > i)) ? groupIds[i] : null;
        prop2sql.translate(outputPath + File.separator + name,
            cmd.getOptionValue("modifier"), groupId,
            cmd.hasOption("noescaping"),
            cmd.getOptionValue("dburl"), cmd.getOptionValue("dbuser"),
            cmd.getOptionValue("dbpassword"),
            cmd.getOptionValue("dbdriver"),
            outputPath + File.separator + name + cmd.getOptionValue("suffix") + ".sql",
            cmd.getOptionValue("charset"));

        System.out.println(
            "Deleting generated properties files: " + outputPath + File.separator + name
                + "*.properties");
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{outputPath + File.separator + name + "*.properties"});
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        for (String f : files) {
          File file = new File(f);
          file.delete();
        }
      }
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
            + "com.eurodyn.qlack.fuse.extras.trastranslationsconverter.XLS2SQL \n"
            + "-groupIDs 9e70c7fa-a366-42cb-906b-87e05e7078ca,b69892cc-c20a-4fb1-9344-c2570fcb412b -modifier qlack "
            + "-dburl jdbc:mysql://localhost:3306/qlack -dbuser root -dbpassword root \n"
            + "-dbdriver com.mysql.jdbc.Driver -inputFile C:\\translations\\ui_translations.xls -outputPath C:\\translations -suffix v0001",
        options);
    System.exit(1);
  }
}
