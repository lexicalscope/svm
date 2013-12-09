package com.lexicalscope.symb.vm.classloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SMethod {
   private final SClassLoader classLoader;
	private final MethodNode method;
	private final Instructions instructions;
   private Instruction entryPoint;

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

	public Instruction entry() {
	   if(entryPoint == null) link();
		return entryPoint;
	}

	private void link() {
	   final Map<AbstractInsnNode, Instruction> linked = new HashMap<>();

	   AbstractInsnNode asmInstruction = getEntryPoint();
	   entryPoint = instructions.instructionFor(classLoader, asmInstruction, null);
	   linked.put(asmInstruction, entryPoint);

	   Instruction prev = entryPoint;

	   while(asmInstruction.getNext() != null) {
	      asmInstruction = asmInstruction.getNext();
	      prev = instructions.instructionFor(classLoader, asmInstruction, prev);

	      linked.put(asmInstruction, prev);
	   }

	   for (final Entry<AbstractInsnNode, Instruction> entry : linked.entrySet()) {
         if(entry.getKey() instanceof JumpInsnNode) {
            final JumpInsnNode key = (JumpInsnNode) entry.getKey();
            entry.getValue().jmpTarget(linked.get(key.label.getNext()));
         }
      }
   }

   private AbstractInsnNode getEntryPoint() {
      return method.instructions.get(0);
   }

   public int argSize() {
		return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
	}
}
