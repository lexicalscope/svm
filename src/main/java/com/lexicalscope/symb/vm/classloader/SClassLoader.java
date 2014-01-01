package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Snapshotable;

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

   MethodBody resolveNative(SMethodName methodName);

   /**
    * Instruction which defines the class of the class Class
    */
   InstructionNode defineClassClassInstruction();

   InstructionNode initThreadInstruction();

   Object init(String desc);
}