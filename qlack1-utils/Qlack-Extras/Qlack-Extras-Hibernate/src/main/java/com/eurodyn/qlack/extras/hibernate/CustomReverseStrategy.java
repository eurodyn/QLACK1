package com.eurodyn.qlack.extras.hibernate;

import java.util.List;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.Column;

/**
 * @author European Dynamics SA
 */
public class CustomReverseStrategy extends DelegatingReverseEngineeringStrategy {

  ReverseEngineeringStrategy delegate;

  /**
   *
   */
  public CustomReverseStrategy(ReverseEngineeringStrategy delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  /**
   * Returns the name of the property to map a FK after. Instead of the default hibernate-tools
   * behaviour to name the property after the FK table, it maps the property after the name of the
   * column of the actual table.
   *
   * @return String
   */
  @Override
  public String foreignKeyToEntityName(String keyname,
      TableIdentifier fromTable,
      List fromColumnNames,
      TableIdentifier referencedTable,
      List referencedColumnNames,
      boolean uniqueReference) {
    String retVal =
        delegate.foreignKeyToEntityName(keyname, fromTable, fromColumnNames,
            referencedTable,
            referencedColumnNames,
            uniqueReference);

    if (fromColumnNames.size() == 1) {
      Column c = (Column) fromColumnNames.get(0);
      retVal = toUpperCamelCase(c.getName());
      retVal =
          retVal.substring(0, 1).toLowerCase() + retVal.substring(1);
    }

    return retVal;
  }

  /**
   * Checks if the PK of a table is of type VARCHAR2(36) and annotates it with a custom UUID
   * generator.
   *
   * @return String
   */
  @Override
  public String getTableIdentifierStrategyName(TableIdentifier tableIdentifier) {
    String retVal = delegate.getTableIdentifierStrategyName(tableIdentifier);

    if (CustomJDBCMetaDataDialect.shouldUUIDAnnotated(tableIdentifier.getName())) {
      retVal = "com.eurodyn.qlack.fuse.extras.hibernate.HibernateUUIDGenerator";
    }

    return retVal;
  }

  private static String toUpperCamelCase(String s) {
    if ("".equals(s)) {
      return s;
    }
    StringBuffer result = new StringBuffer();

    boolean capitalize = true;
    boolean lastCapital = false;
    boolean lastDecapitalized = false;
    String p = null;
    for (int i = 0; i < s.length(); i++) {
      String c = s.substring(i, i + 1);
      if ("_".equals(c) || " ".equals(c) || "-".equals(c)) {
        capitalize = true;
        continue;
      }

      if (c.toUpperCase().equals(c)) {
        if (lastDecapitalized && !lastCapital) {
          capitalize = true;
        }
        lastCapital = true;
      } else {
        lastCapital = false;
      }

      //if(forceFirstLetter && result.length()==0) capitalize = false;

      if (capitalize) {
        if (p == null || !p.equals("_")) {
          result.append(c.toUpperCase());
          capitalize = false;
          p = c;
        } else {
          result.append(c.toLowerCase());
          capitalize = false;
          p = c;
        }
      } else {
        result.append(c.toLowerCase());
        lastDecapitalized = true;
        p = c;
      }
    }
    String r = result.toString();
    return r;
  }
}
