package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.SMethodDescriptor;
import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.instructions.Instructions;

public interface NativeMethodDef {
   MethodBody instructions(Instructions instructions);
   SMethodDescriptor name();
}
