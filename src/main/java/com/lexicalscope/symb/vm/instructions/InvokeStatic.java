package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;

public class InvokeStatic implements Instruction {
   private final MethodInsnNode methodInsnNode;
   private final SClassLoader classLoader;

   public InvokeStatic(final SClassLoader classLoader, final String klass, final String method, final String desc) {
      this(classLoader, new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }

   public InvokeStatic(final SClassLoader classLoader, final MethodInsnNode methodInsnNode) {
      this.classLoader = classLoader;
      this.methodInsnNode = methodInsnNode;
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      final SMethod targetMethod = classLoader.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);

      state.op(new StackOp<Void>(){
		@Override
		// think it returns +1 as static methods do not have a this
		public Void eval(final Stack stack) {
			stack.pushFrame(instruction.next(), targetMethod, targetMethod.argSize() - 1);
			return null;
		}});
   }

   @Override
   public String toString() {
      return String.format("INVOKESTATIC %s", methodInsnNode.desc);
   }
}
