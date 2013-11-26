package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
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
   public void eval(final Vm vm, final State state) {
      final SMethod targetMethod = vm.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);

      state.op(new StackOp<Void>(){
		@Override
		// think it returns +1 as static methods do not have a this
		public Void eval(Stack stack) {
			stack.pushFrame(instructionFor(methodInsnNode.getNext()), targetMethod, targetMethod.argSize() - 1);
			return null;
		}});
   }


   @Override
   public String toString() {
      return String.format("INVOKESTATIC %s", methodInsnNode.desc);
   }
}
