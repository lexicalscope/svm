package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.classloader.AsmSMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;

public interface NativeMethodDef {
   MethodBody instructions(Instructions instructions);
   AsmSMethodName name();
}
