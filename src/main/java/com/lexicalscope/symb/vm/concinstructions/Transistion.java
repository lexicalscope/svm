package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Transistion {
   void next(SClassLoader cl, State state, AbstractInsnNode abstractInsnNode);
}
