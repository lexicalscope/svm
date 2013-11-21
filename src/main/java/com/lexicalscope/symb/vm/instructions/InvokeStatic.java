package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;

public class InvokeStatic implements Instruction {
   private final MethodInsnNode methodInsnNode;

   public InvokeStatic(final String klass, final String method, final String desc) {
      this(new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }

   public InvokeStatic(final MethodInsnNode methodInsnNode) {
      this.methodInsnNode = methodInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      final SClass targetClass = vm.loadClass(methodInsnNode.owner);
      final SMethod targetMethod = targetClass.staticMethod(methodInsnNode.name, methodInsnNode.desc);

      final int argSize = argSize();

      return state.push(instructionFor(methodInsnNode.getNext()), targetMethod.entry(), argSize);
   }

   private int argSize() {
      // think it returns +1 as static methods do not have a this
      return (Type.getMethodType(methodInsnNode.desc).getArgumentsAndReturnSizes() >> 2) - 1;
   }

   @Override
   public String toString() {
      return String.format("INVOKESTATIC %d", argSize());
   }
}
