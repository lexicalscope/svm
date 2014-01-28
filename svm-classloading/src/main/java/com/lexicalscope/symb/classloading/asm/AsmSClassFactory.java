package com.lexicalscope.symb.classloading.asm;

import java.net.URL;
import java.util.List;

import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class AsmSClassFactory {

   public static AsmSClass newSClass(final SClassLoader classLoader, final Instructions instructions, final URL loadedFromUrl, final ClassNode classNode, final SClass superclass, final List<SClass> interfaces) {
      final ClassNodeAdapter classNodeAdapter = new ClassNodeAdapter(classNode);

      final AsmSClassBuilder asmSClassBuilder = new AsmSClassBuilder(classLoader, instructions, superclass)
            .withName(classNode.name)
            .withFields(classNodeAdapter.fields())
            .withMethods(classNodeAdapter.methods());

      return new AsmSClass(
            loadedFromUrl,
            classNode.name,
            superclass,
            interfaces,
            asmSClassBuilder.declaredFields(),
            asmSClassBuilder.declaredMethods());
   }
}
