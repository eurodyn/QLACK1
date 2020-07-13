package com.eurodyn.qlack.extras.hibernate;

import org.hibernate.cfg.reveng.dialect.JDBCMetaDataDialect;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author European Dynamics SA
 */
public class CustomJDBCMetaDataDialect extends JDBCMetaDataDialect {

  private static final Logger logger = Logger.getLogger(CustomJDBCMetaDataDialect.class.getName());

  public static HashMap<String, HashMap> tablesStructure = new HashMap<>();
  public static HashMap<String, String> tablesPKs = new HashMap<>();

  public CustomJDBCMetaDataDialect() {
    super();
  }

  /**
   * Check if this table's PK is VARCHAR2(36).
   *
   * @return boolean true/false
   */
  public static boolean shouldUUIDAnnotated(String tableName) {
    boolean retVal = false;

    try {
      // First find the PK column of this table.
      String pk = tablesPKs.get(tableName);

      // And check the datatype of this column
      HashMap tableMap = tablesStructure.get(tableName);
      HashMap columnMap = (HashMap) tableMap.get(pk);
      int columnType = Integer.parseInt((String) columnMap.get("type"));
      int columnSize = Integer.parseInt((String) columnMap.get("size"));

      retVal = (columnType == 12 && columnSize == 36);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
    }

    return retVal;
  }

  // Note: This one works only for tables with a single-column PK.

  /**
   * @return Iterator
   */
  @Override
  public Iterator getColumns(final String catalog, final String schema, final String table,
      String column) {

    try {
      ResultSet tableRs;
      tableRs = getMetaData().getColumns(catalog, schema, table, column);
      HashMap tableColumns = new HashMap();
      while (tableRs.next()) {
        HashMap<String, String> columnMap = new HashMap<String, String>();
        columnMap.put("type", tableRs.getString("DATA_TYPE"));
        columnMap.put("size", tableRs.getString("COLUMN_SIZE"));
        tableColumns.put(tableRs.getString("COLUMN_NAME"), columnMap);
      }
      tablesStructure.put(table, tableColumns);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
    }

    return super.getColumns(catalog, schema, table, column);
  }

  /**
   * @return Iterator
   */
  @Override
  public Iterator getPrimaryKeys(final String catalog, final String schema, final String table) {

    try {
      ResultSet tableRs = getMetaData().getPrimaryKeys(catalog, schema, table);
      if (tableRs.next()) {
        tablesPKs.put(table, tableRs.getString("COLUMN_NAME"));
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
    }

    return super.getPrimaryKeys(catalog, schema, table);
  }
}
