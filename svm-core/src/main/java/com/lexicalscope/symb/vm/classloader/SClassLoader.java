package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.classloader.asm.AsmSClass;

public interface SClassLoader {
   /**
    * @param classLoaded reports any additional loaded classes (superclasses, etc)
    * @return the loaded class
    */
   AsmSClass load(String name, ClassLoaded classLoaded);

   /**
    * @param classLoaded reports any additional loaded classes (superclasses, etc)
    * @return the loaded class
    */
   SClass load(Class<?> klass, ClassLoaded classLoaded);
   AsmSClass load(Class<?> klass);

   Snapshotable<?> initialMeta();

   MethodBody resolveNative(SMethodDescriptor methodName);

   InstructionNode defineBootstrapClassesInstruction();
   InstructionNode initThreadInstruction();
   InstructionNode loadArgsInstruction(Object[] args);

   Object init(String desc);
}