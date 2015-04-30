package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.KlassInternalName;
import com.lexicalscope.svm.vm.j.MethodBody;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface SClassLoader extends MethodInstrumentor {
   /**
    * @return the loaded class
    */
   SClass load(KlassInternalName name);

   /**
    * @return the loaded class
    */
   SClass load(Class<?> klass);

   MethodBody resolveNative(SMethodDescriptor methodName);
}