package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.vm.DequeStack;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.InvokeStatic;

public class AsmSClassLoader implements SClassLoader {
	private final Instructions instructions;
   private final InstructionFactory instructionFactory;
   private final ByteCodeReader byteCodeReader;

	public AsmSClassLoader(final InstructionFactory instructionFactory) {
		this.instructionFactory = instructionFactory;
      this.instructions = new BaseInstructions(instructionFactory);
      this.byteCodeReader = new CachingByteCodeReader(instructions);
	}

	public AsmSClassLoader() {
		this(new ConcInstructionFactory());
	}

   @Override
   public SClass load(final String name) {
      return byteCodeReader.load(this, name);
   }

   @Override
   public SClass load(final Class<?> klass) {
      return load(klass.getCanonicalName().replaceAll("\\.", "/"));
   }

	@Override
   public SMethod loadMethod(final String klass, final String name,
			final String desc) {
		return load(klass).staticMethod(name, desc);
	}

	@Override
   public Instruction instructionFor(final AbstractInsnNode abstractInsnNode) {
		return instructions.instructionFor(this, abstractInsnNode);
	}

	@Override
   public State initial(final String klass) {
		return initial(klass, "main", "([Ljava/lang/String;)V");
	}

	@Override
   public State initial(final MethodInfo info) {
		return initial(info.klass(), info.name(), info.desc());
	}

	@Override
   public State initial(final String klass, final String name,
			final String desc) {
		final SMethod method = loadMethod(klass, name, desc);
		return new State(new DequeStack(new InvokeStatic(klass, name, desc), 0,
				method.argSize()), new FastHeap(), instructionFactory.initialMeta());
	}
}
