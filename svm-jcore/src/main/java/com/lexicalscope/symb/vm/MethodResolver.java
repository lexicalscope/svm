package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.klass.SMethod;
import com.lexicalscope.symb.klass.SMethodDescriptor;
import com.lexicalscope.symb.stack.trace.SMethodName;

public interface MethodResolver {
   SMethod virtualMethod(SMethodDescriptor sMethodName);
   SMethod declaredMethod(SMethodName sMethodName);
   String name();
}