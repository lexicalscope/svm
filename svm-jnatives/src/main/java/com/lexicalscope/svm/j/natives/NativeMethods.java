package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.klass.SMethodDescriptor;
import com.lexicalscope.symb.vm.MethodBody;

public interface NativeMethods {
   MethodBody resolveNative(Instructions instructions, SMethodDescriptor methodName);
}
