package com.eurodyn.qlack.extras.translationsconverter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.tools.ant.DirectoryScanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * This class bundles lots of different property files of the type key=value, in an excel file. <br/> These property
 * files can contain lines like 1)key=value 2)#comment 3)[empty lines].
 */
public class Properties2XLS {

  private static final Options options = new Options();

  public static void main(String[] args) throws Exception {
    // Define command-line args.
    options.addOption("props", true,
        "The prefix of the Java-properties file. Linguistic versions will be discovered automatically.");
    options.addOption("outputFile", true, "The XLS file where to write the output.");
    options
        .addOption("mainFile", true, "The main properties file containing all the translations.");

    // Parse the command line.
    CommandLineParser parser = new PosixParser();
    CommandLine cmd;

    cmd = parser.parse(options, args);
    if (!cmd.hasOption("outputFile") || (!cmd.hasOption("props"))) {
      printHelpAndExit();
    }

    Properties2XLS app = new Properties2XLS();
    app.translate(cmd.getOptionValue("outputFile"), cmd.getOptionValue("props"),
        cmd.getOptionValue("mainFile"));
  }

  private static void printHelpAndExit() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        "java -cp QlackFuse-Extras-TranslationsConverter-2.0.jar com.eurodyn.qlack.fuse.extras.trastranslationsconverter.Properties2XLS \n"
            + "-props C:\\translations\\reference_data_translations_ -outputFile C:\\translations\\reference_data_translations.xls "
            + "-mainFile C:\\translations\\reference_data_translations_en.properties\"", options);
    System.exit(1);
  }

  public void translate(String outputFile, String propertiesFile, String mainFile)
      throws IOException {

    System.out.println("Checking which property files are available...");

    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setIncludes(new String[]{propertiesFile + "*.properties"});
    scanner.setCaseSensitive(false);
    scanner.scan();
    String[] files = scanner.getIncludedFiles();
    if (mainFile == null) {
      mainFile = files[0];
    }
    Properties[] props = new Properties[files.length];
    for (int i = 0; i < files.length; i++) {
      System.out.println("Found: " + files[i]);
      props[i] = new Properties();
      props[i].load(new FileInputStream(files[i]));
    }
    System.out.println("Main file is " + mainFile);

    //Create workbook
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb
        .createSheet(propertiesFile.substring(propertiesFile.lastIndexOf(File.separator) + 1));
    sheet.setColumnWidth(0, 10000);
    sheet.setColumnWidth(1, 10000);
    sheet.setZoom(115, 100);

    //Initialise styles
    HSSFFont cellfont = wb.createFont();
    cellfont.setFontName(HSSFFont.FONT_ARIAL);
    cellfont.setFontHeightInPoints((short) 10);
    HSSFCellStyle cellstyle = wb.createCellStyle();
    cellstyle.setFont(cellfont);
    cellstyle.setWrapText(true);
    HSSFFont headcellfont = wb.createFont();
    headcellfont.setFontName(HSSFFont.FONT_ARIAL);
    headcellfont.setFontHeightInPoints((short) 10);
    headcellfont.setBold(true);
    HSSFCellStyle headcellstyle = wb.createCellStyle();
    headcellstyle.setFont(headcellfont);
    headcellstyle.setVerticalAlignment(VerticalAlignment.CENTER);;

    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(mainFile), StandardCharsets.UTF_8))) {
      String line;

      HSSFRow headrow = sheet.createRow(0);
      HSSFCell headcell = headrow.createCell(0);
      headcell.setCellValue("KEYS");
      headcell.setCellStyle(headcellstyle);
      headcell = headrow.createCell(1);
      headcell
          .setCellValue(mainFile.substring(propertiesFile.length(), mainFile.indexOf(".properties")));
      headcell.setCellStyle(headcellstyle);

      int rowcounter = 1;
      while ((line = br.readLine()) != null) {
        HSSFRow row = sheet.createRow(rowcounter);
        if (line.trim().startsWith("#") || StringUtils.isBlank(line)) {
          row.createCell(0).setCellValue(line);
        } else {
          if (!line.contains("=")) {
            throw new RuntimeException(
                "Line:" + rowcounter + " is not a comment and does not contain a '='");
          }
          String key = line.substring(0, line.indexOf('='));
          HSSFCell cell0 = row.createCell(0);
          cell0.setCellValue(key);
          cell0.setCellStyle(cellstyle);

          HSSFCell cell1 = row.createCell(1);
          cell1.setCellValue(line.substring(line.indexOf('=') + 1));
          cell1.setCellStyle(cellstyle);

          int columncounter = 2;

          for (int i = 0; i < files.length; i++) {
            if (files[i].equals(mainFile)) {
              continue;
            }
            headcell = headrow.createCell(columncounter);

            String headCellValue = files[i]
                .substring(propertiesFile.length(), files[i].indexOf(".properties"));
            headcell.setCellValue(headCellValue);
            headcell.setCellStyle(headcellstyle);

            sheet.setColumnWidth(columncounter, (short) 10000);
            HSSFCell cell = row.createCell(columncounter);
            cell.setCellValue(props[i].getProperty(key));
            cell.setCellStyle(cellstyle);
            columncounter++;
          }
        }
        rowcounter++;
      }
    }

    FileOutputStream stream = new FileOutputStream(new File(outputFile));
    wb.write(stream);
    stream.close();
  }
}
