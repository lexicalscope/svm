package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public interface Transistion {
   void next(Vm vm, State state, AbstractInsnNode abstractInsnNode);
}
