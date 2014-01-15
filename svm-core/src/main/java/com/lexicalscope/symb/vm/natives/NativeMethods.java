package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.MethodBody;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;
import com.lexicalscope.symb.vm.instructions.Instructions;

public interface NativeMethods {
   MethodBody resolveNative(Instructions instructions, SMethodDescriptor methodName);
}
