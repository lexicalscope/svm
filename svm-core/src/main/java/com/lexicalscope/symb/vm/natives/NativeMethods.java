package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.builder.MethodBody;
import com.lexicalscope.svm.j.instruction.concrete.Instructions;
import com.lexicalscope.symb.vm.SMethodDescriptor;

public interface NativeMethods {
   MethodBody resolveNative(Instructions instructions, SMethodDescriptor methodName);
}
