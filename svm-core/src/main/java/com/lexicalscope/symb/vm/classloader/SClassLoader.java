package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.svm.j.statementBuilder.MethodBody;
import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.SClass;
import com.lexicalscope.symb.vm.SMethodDescriptor;
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

   Instruction defineBootstrapClassesInstruction();
   Instruction initThreadInstruction();
   Instruction loadArgsInstruction(Object[] args);

   Object init(String desc);
}