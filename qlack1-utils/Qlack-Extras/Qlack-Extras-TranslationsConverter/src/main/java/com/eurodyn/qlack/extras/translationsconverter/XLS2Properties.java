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

/**
 * This class takes the columns of an excel file and puts them into properties files of the type
 * key=value. <br/>
 * <ul>
 * <li>The first column is always recognized as the column containing the keys.
 * <li>The second to the last column contain the values of these keys.
 * <li>The headers of the value-containing columns are the languages which will be used as
 * suffixes for the names of the produced properties files
 * </ul>
 */
public class XLS2Properties {

  private static final Options options = new Options();

  private final String OUTPUT_ENCODING = "UTF8";

  public String[] translate(String inputFile, String outputPath) throws IOException {
    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(new File(inputFile)));
    HSSFWorkbook wb = new HSSFWorkbook(fs);

    String[] result = new String[wb.getNumberOfSheets()];

    for (int i = 0; i < wb.getNumberOfSheets(); i++) {
      extractSheet(wb, i, outputPath);
      result[i] = wb.getSheetName(i);
    }

    return result;
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
          //if the translation exists for a key x ,it is used. otherwise lang_x is used
          String value = "";
          if (row.getCell(j) != null) {
            value = row.getCell(j).getStringCellValue();
          }

          if ((row.getCell(j) == null) || (StringUtils.isEmpty(value))) {
            value = headCellValues[j] + "_" + row.getCell(0).getStringCellValue();
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
    options.addOption("outputPath", true,
        "The path where to output the translations properties files.");

    // Parse the command line.
    CommandLineParser parser = new PosixParser();
    CommandLine cmd;
    try {
      cmd = parser.parse(options, args);
      if (!cmd.hasOption("inputFile")) {
        printHelpAndExit();
      }

      XLS2Properties app = new XLS2Properties();
      app.translate(cmd.getOptionValue("inputFile"), cmd.getOptionValue("outputPath"));
    } catch (ParseException ex) {
      printHelpAndExit();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private static void printHelpAndExit() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        "java -cp QlackFuse-Extras-TranslationsConverter-2.0.jar; com.eurodyn.qlack.fuse.extras.trastranslationsconverter.XLS2Properties \n"
            + "-inputFile C:\\translations\\ui_translations.xls -outputPath C:\\translations"
        , options);
    System.exit(1);
  }
}
