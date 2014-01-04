package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;

public interface NativeMethods {
   MethodBody resolveNative(Instructions instructions, SMethodName methodName);
}
