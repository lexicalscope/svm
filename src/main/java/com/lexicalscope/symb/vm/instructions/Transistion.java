package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public interface Transistion {
   State next(Vm vm, State state, AbstractInsnNode abstractInsnNode);
}
