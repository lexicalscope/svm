package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.MethodBody;
import com.lexicalscope.symb.vm.SMethodDescriptor;

public interface NativeMethodDef {
   MethodBody instructions(Instructions instructions);
   SMethodDescriptor name();
}
