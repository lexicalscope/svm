package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.instructions.ops.StackOp;

final class InvokeVirtual implements Instruction {
   private final MethodInsnNode methodInsnNode;

   InvokeVirtual(MethodInsnNode methodInsnNode) {
      this.methodInsnNode = methodInsnNode;
   }

   @Override
   public void eval(final SClassLoader cl, final Vm vm, final State state) {
      final SMethod targetMethod = cl.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);

      // TODO[tim]: resolve overridden methods

      state.op(new StackOp<Void>() {
         @Override
         public Void eval(final Stack stack) {
            stack.pushFrame(cl.instructionFor(methodInsnNode.getNext()), targetMethod, targetMethod.argSize());
            return null;
         }
      });
   }

   @Override
   public String toString() {
      return String.format("INVOKESPECIAL %s", methodInsnNode.desc);
   }
}