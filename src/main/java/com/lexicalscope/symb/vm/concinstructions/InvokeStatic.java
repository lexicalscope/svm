package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;

public class InvokeStatic implements Instruction {
   private final MethodInsnNode methodInsnNode;

   public InvokeStatic(final String klass, final String method, final String desc) {
      this(new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }

   public InvokeStatic(final MethodInsnNode methodInsnNode) {
      this.methodInsnNode = methodInsnNode;
   }

   @Override
   public void eval(final SClassLoader cl, final State state) {
      final SMethod targetMethod = cl.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);

      state.op(new StackOp<Void>(){
		@Override
		// think it returns +1 as static methods do not have a this
		public Void eval(final Stack stack) {
			stack.pushFrame(cl.instructionFor(methodInsnNode.getNext()), targetMethod, targetMethod.argSize() - 1);
			return null;
		}});
   }

   @Override
   public String toString() {
      return String.format("INVOKESTATIC %s", methodInsnNode.desc);
   }
}
