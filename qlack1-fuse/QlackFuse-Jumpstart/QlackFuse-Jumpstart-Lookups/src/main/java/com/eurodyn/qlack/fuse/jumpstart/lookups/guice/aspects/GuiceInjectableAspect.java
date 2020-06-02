package com.eurodyn.qlack.fuse.jumpstart.lookups.guice.aspects;

import com.eurodyn.qlack.fuse.jumpstart.lookups.guice.injectors.InjectTypeListener;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * An aspect with an anonymous pointcut to automatically initialise Guice injector in classes annotated with
 *
 * @author European Dynamics SA.
 * @Injectable. Remember that to correctly use this in your projects you need to: 1/ Have aspectjrt as a dependency. 2/
 * Configure the aspectj-maven-plugin plugin in your pom.xml's segment. 3/ Configure the plugin to use an aspect library
 * from this project. i.e.
 * <plugin>
 * <groupId>org.codehaus.mojo</groupId>
 * <artifactId>aspectj-maven-plugin</artifactId>
 * <version>1.3.1</version>
 * <executions>
 * <execution>
 * <goals>
 * <goal>compile</goal>
 * </goals>
 * </execution>
 * </executions>
 * <configuration>
 * <complianceLevel>1.6</complianceLevel>
 * <verbose>true</verbose>
 * <showWeaveInfo>false</showWeaveInfo>
 * <aspectLibraries>
 * <aspectLibrary>
 * <groupId>com.eurodyn.qlack.fuse.jumpstart</groupId>
 * <artifactId>QlackFuse-Jumpstart-Lookups</artifactId>
 * </aspectLibrary>
 * </aspectLibraries>
 * </configuration>
 * </plugin>
 */
@Aspect
public class GuiceInjectableAspect {

  private static Injector injector = Guice.createInjector(new GuiceModule());

  @Before("within(@com.eurodyn.qlack.fuse.jumpstart.lookups.guice.annotations.Injectable *) && "
      + "execution(new(..)) && !this(GuiceInjectableAspect)")
  public void injectable(JoinPoint thisJoinPoint) {
    injector.injectMembers(thisJoinPoint.getThis());
  }

  private static class GuiceModule implements Module {

    @Override
    public void configure(Binder binder) {
      binder.bindListener(Matchers.any(), new InjectTypeListener());
    }
  }
}
