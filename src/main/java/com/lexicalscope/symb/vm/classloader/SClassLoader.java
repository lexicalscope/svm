package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.State;

public interface SClassLoader {
   SClass load(String name);

   SClass load(Class<?> klass);

   SMethod loadMethod(String klass, String name, String desc);

   State initial(MethodInfo info);
}