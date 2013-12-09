package com.lexicalscope.symb.vm.classloader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.Instructions.InstructionSink;

public class SMethod {
   private final SClassLoader classLoader;
	private final MethodNode method;
	private final Instructions instructions;
   private InstructionNode entryPoint;

	public SMethod(
	      final SClassLoader classLoader,
	      final Instructions instructions,
	      final MethodNode method) {
		this.classLoader = classLoader;
      this.instructions = instructions;
		this.method = method;
	}

	public int maxLocals() {
		return method.maxLocals;
	}

	public int maxStack() {
		return method.maxStack;
	}

	public InstructionNode entry() {
	   if(entryPoint == null) link();
		return entryPoint;
	}

	private void link() {
	   final Map<AbstractInsnNode, InstructionNode> linked = new LinkedHashMap<>();
	   final InstructionNode[] prev = new InstructionNode[1];

	   final InstructionSink instructionSink = new InstructionSink() {
         @Override public void nextInstruction(final AbstractInsnNode asmInstruction, final InstructionNode node) {
            linked.put(asmInstruction, node);
            if(prev[0] != null) prev[0].next(node);
            prev[0] = node;
         }
	   };

	   AbstractInsnNode asmInstruction = getEntryPoint();
	   while(asmInstruction != null) {
	      instructions.instructionFor(classLoader, asmInstruction, prev[0], instructionSink);
	      asmInstruction = asmInstruction.getNext();
	   }

	   for (final Entry<AbstractInsnNode, InstructionNode> entry : linked.entrySet()) {
         if(entry.getKey() instanceof JumpInsnNode) {
            final JumpInsnNode key = (JumpInsnNode) entry.getKey();
            entry.getValue().jmpTarget(linked.get(key.label.getNext()));
         }
      }

	   entryPoint = linked.values().iterator().next();
   }

   private AbstractInsnNode getEntryPoint() {
      return method.instructions.get(0);
   }

   public int argSize() {
		return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
	}
}
