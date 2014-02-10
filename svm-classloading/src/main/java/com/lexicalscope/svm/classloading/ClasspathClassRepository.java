package com.lexicalscope.svm.classloading;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public final class ClasspathClassRepository implements ClassSource {
   private final ClassLoader classLoader;

   public ClasspathClassRepository() {
      this(ClasspathClassRepository.class.getClassLoader());
   }

   public ClasspathClassRepository(final ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   @Override public URL loadFromRepository(final String name) {
      return classLoader
            .getResource(name.replace(".", File.separator) + ".class");
   }

   public static ClasspathClassRepository classpathClassRepostory(final Class<?> loadFromWhereverThisWasLoaded) {
      return new ClasspathClassRepository(new URLClassLoader(new URL[]{JarClassRepository.urlOfJarFile(loadFromWhereverThisWasLoaded)}, null));
   }
}
