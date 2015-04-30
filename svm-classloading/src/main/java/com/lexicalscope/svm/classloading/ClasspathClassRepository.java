package com.lexicalscope.svm.classloading;

import static com.lexicalscope.svm.vm.j.KlassInternalName.internalName;

import java.net.URL;
import java.net.URLClassLoader;

import com.lexicalscope.svm.vm.j.KlassInternalName;

public final class ClasspathClassRepository implements ClassSource {
   private final ClassLoader classLoader;

   public ClasspathClassRepository() {
      this(ClasspathClassRepository.class.getClassLoader());
   }

   public ClasspathClassRepository(final ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public URL loadFromRepository(final String name) {
      return loadFromRepository(internalName(name));
   }

   @Override public URL loadFromRepository(final KlassInternalName name) {
      assert !name.string().contains(".");
      return classLoader
            .getResource(name + ".class");
   }

   public static ClasspathClassRepository classpathClassRepostory(final Class<?> loadFromWhereverThisWasLoaded) {
      return new ClasspathClassRepository(new URLClassLoader(new URL[]{JarClassRepository.urlOfJarFile(loadFromWhereverThisWasLoaded)}, null));
   }
}
