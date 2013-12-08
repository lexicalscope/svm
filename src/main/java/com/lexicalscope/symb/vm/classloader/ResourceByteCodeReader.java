package com.lexicalscope.symb.vm.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.symb.vm.instructions.Instructions;

public class ResourceByteCodeReader implements ByteCodeReader {
   private final Instructions instructions;

   public ResourceByteCodeReader(final Instructions instructions) {
      this.instructions = instructions;
   }

   @Override
   public SClass load(final SClassLoader classLoader, final String name) {
      if(name == null) { return null; }

      try {
         final ClassNode classNode = new ClassNode();

         final InputStream in = this
               .getClass()
               .getClassLoader()
               .getResourceAsStream(
                     name.replace(".", File.separator) + ".class");

         if (in == null)
            throw new SClassNotFoundException(name);

         try {
            new ClassReader(in).accept(classNode, 0);
         } finally {
            in.close();
         }
         final SClass superclassNode = classLoader.load(classNode.superName);
         return new SClass(classLoader, instructions, classNode, superclassNode);
      } catch (final IOException e) {
         throw new SClassLoadingFailException(name, e);
      }
   }
}
