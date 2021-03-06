package com.eurodyn.qlack.extras.dbrebuild;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @author European Dynamnics
 */
public class DirFilter implements FilenameFilter {

  private final Pattern pattern;

  public DirFilter(String regex) {
    pattern = Pattern.compile(regex);
  }

  public boolean accept(File dir, String name) {
    return pattern.matcher(new File(name).getName()).matches();
  }
}
