package com.lexicalscope.symb.vm.j;

import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethod;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface MethodResolver {
   SMethod virtualMethod(SMethodDescriptor sMethodName);
   SMethod declaredMethod(SMethodName sMethodName);
}