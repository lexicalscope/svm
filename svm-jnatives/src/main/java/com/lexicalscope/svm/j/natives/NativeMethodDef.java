package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface NativeMethodDef {
   MethodBody instructions(InstructionSource instructions);
   SMethodDescriptor name();
}
