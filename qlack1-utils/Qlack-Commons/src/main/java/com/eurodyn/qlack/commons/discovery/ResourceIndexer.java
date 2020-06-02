package com.eurodyn.qlack.commons.discovery;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author European Dynamics SA
 */
public class ResourceIndexer {

  /**
   * Returns the list of resources (i.e. classes, properties, generally any kind of file) found under a specific
   * package. This method does not scan inside JAR files (so use it with care when using it in a web application
   * deployed on WLS since it packs the webapp clases in a jar file).
   *
   * @param pckgname The package to search under.
   * @param startsWith Only resources starting with this argument will be returned. Set this argument as null to bypass
   * this filter.
   * @param endsWith Only resources ending with this argument will be returned. Note that this argument refers to the
   * name of the resource and not its file extension. Set this argument as null to bypass this filter.
   * @param fileExtension Only resources with this file extension will be returned. Set this argument as null to bypass
   * this filter.
   * @param dirInfo If true, the directory in which the resource was found will be prepended to the results.
   * @return A list with all the resources matching the search parameters.
   */
  public static List<String> getResourcesForPackage(String pckgname, String startsWith,
      String endsWith, String fileExtension, boolean dirInfo) throws ClassNotFoundException {
    // This will hold a list of directories matching the pckgname. There may be more than one if a package is split over multiple jars/paths
    ArrayList<File> directories = new ArrayList<>();
    try {
      ClassLoader cld = Thread.currentThread().getContextClassLoader();
      if (cld == null) {
        throw new ClassNotFoundException("Can't get class loader.");
      }
      String path = pckgname.replace('.', '/');
      // Ask for all resources for the path
      Enumeration<URL> resources = cld.getResources(path);
      while (resources.hasMoreElements()) {
        URL nextResource = resources.nextElement();
        // Skip jar files.
        if (!nextResource.getPath().contains(".jar")) {
          directories.add(new File(URLDecoder.decode(nextResource.getPath(), StandardCharsets.UTF_8)));
        }
      }
    } catch (NullPointerException x) {
      throw new ClassNotFoundException(
          pckgname + " does not appear to be a valid package (Null pointer exception)");
    } catch (UnsupportedEncodingException encex) {
      throw new ClassNotFoundException(
          pckgname + " does not appear to be a valid package (Unsupported encoding)");
    } catch (IOException ioex) {
      throw new ClassNotFoundException(
          "IOException was thrown when trying to get all resources for " + pckgname);
    }

    ArrayList<String> resources = new ArrayList<>();
    // For every directory identified capture all the .fileExtension files
    for (File directory : directories) {
      if (directory.exists()) {
        // Get the list of the files contained in the package
        String[] files = directory.list();
        if (files != null) {
          for (String file : files) {
            if (((endsWith == null) || (
                ((file.lastIndexOf(".") > -1) && ((file.substring(0, file.lastIndexOf(".")))
                    .endsWith(endsWith)))
                    || (file.endsWith(endsWith))))
                && ((fileExtension == null) || (file.endsWith(fileExtension)))
                && ((startsWith == null) || (file.startsWith(startsWith)))) {
              resources.add(
                  (dirInfo ? directory.toString() + File.separatorChar : "") + file);
            }
          }
        }
      } else {
        throw new ClassNotFoundException(
            pckgname + " (" + directory.getPath() + ") does not appear to be a valid package.");
      }
    }
    return resources;
  }
}
