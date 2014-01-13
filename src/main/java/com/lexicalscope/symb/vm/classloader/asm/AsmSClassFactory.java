package com.lexicalscope.symb.vm.classloader.asm;

import java.net.URL;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmSClassFactory {

   public static AsmSClass newSClass(final SClassLoader classLoader, final Instructions instructions, final URL loadedFromUrl, final ClassNode classNode, final AsmSClass superclass, final List<AsmSClass> interfaces) {
      final AsmSClassBuilder sClassBuilder = new AsmSClassBuilder(classLoader, superclass);
      final ClassNodeAdapter classNodeAdapter = new ClassNodeAdapter(classNode);
      sClassBuilder.withName(classNode.name).initialiseFieldMaps(classNodeAdapter.fields());
      return new AsmSClass(classLoader, instructions, loadedFromUrl, classNode, superclass, interfaces, sClassBuilder);
   }
}
