package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;

public interface SClassLoader {
   SClass load(String name);

   SClass load(Class<?> klass);

   SMethod loadMethod(String klass, String name, String desc);

   Instruction instructionFor(AbstractInsnNode abstractInsnNode);

   State initial(String klass);

   State initial(MethodInfo info);

   State initial(String klass, String name, String desc);
}