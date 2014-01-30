package com.lexicalscope.symb.classloading;

import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface SClassLoader {
   /**
    * @param classLoaded reports any additional loaded classes (superclasses, etc)
    * @return the loaded class
    */
   SClass load(String name, ClassLoaded classLoaded);

   /**
    * @param classLoaded reports any additional loaded classes (superclasses, etc)
    * @return the loaded class
    */
   SClass load(Class<?> klass, ClassLoaded classLoaded);
   SClass load(Class<?> klass);

   Snapshotable<?> initialMeta();

   MethodBody resolveNative(SMethodDescriptor methodName);

   Instruction defineBootstrapClassesInstruction();
   Instruction initThreadInstruction();
   Instruction loadArgsInstruction(Object[] args);
}