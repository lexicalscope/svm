package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public interface MethodCallVop extends Vop {
   SMethodDescriptor getMethodName();
}
