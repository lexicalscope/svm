package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.MethodBody;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

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

   MethodBody resolveNative(SMethodDescriptor methodName);

   Instruction instrument(SMethodDescriptor name, Instruction methodEntry);
}