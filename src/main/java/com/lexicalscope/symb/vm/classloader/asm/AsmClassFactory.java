package com.lexicalscope.symb.vm.classloader.asm;

import java.net.URL;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmClassFactory {

   public static AsmSClass newSClass(final SClassLoader classLoader, final Instructions instructions, final URL loadedFromUrl, final ClassNode classNode, final AsmSClass superclass, final List<AsmSClass> interfaces) {
      final AsmClassBuilder sClassBuilder = new AsmClassBuilder(classLoader, superclass);
      sClassBuilder.initialiseFieldMaps(classNode);
      return new AsmSClass(classLoader, instructions, loadedFromUrl, classNode, superclass, interfaces, sClassBuilder);
   }

}
