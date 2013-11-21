package com.lexicalscope.symb.vm.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class SClassLoader {
   public SClass load(final String name) {
      try {
         final ClassNode classNode = new ClassNode();

         final InputStream in = this.getClass().getClassLoader().getResourceAsStream(name.replace(".", File.separator) + ".class");

         if(in == null) {
            throw new SClassNotFoundException(name);
         }

         try {
            new ClassReader(in).accept(classNode, 0);
         } finally {
            in.close();
         }
         return new SClass(classNode);
      } catch(final IOException e) {
         throw new SClassLoadingFailException(name, e);
      }
   }
}
