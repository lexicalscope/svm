package com.lexicalscope.svm.vm.j;

import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface MethodResolver {
   SMethod virtualMethod(SMethodDescriptor sMethodName);
   SMethod declaredMethod(SMethodName sMethodName);
}