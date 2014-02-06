package com.lexicalscope.svm.j.instruction;

import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface MethodCallVop extends Vop {
   SMethodDescriptor getMethodName();
}
