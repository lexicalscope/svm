package com.lexicalscope.symb.vm.classloader.asm;

import java.net.URL;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmSClassFactory {

   public static AsmSClass newSClass(final SClassLoader classLoader, final Instructions instructions, final URL loadedFromUrl, final ClassNode classNode, final AsmSClass superclass, final List<AsmSClass> interfaces) {
      final ClassNodeAdapter classNodeAdapter = new ClassNodeAdapter(classNode);

      return new AsmSClass(
            loadedFromUrl,
            classNode.name,
            superclass,
            interfaces,
            new AsmSClassBuilder(classLoader, instructions, superclass)
            .withName(classNode.name)
            .withFields(classNodeAdapter.fields())
            .withMethods(classNodeAdapter.methods()));
   }
}
