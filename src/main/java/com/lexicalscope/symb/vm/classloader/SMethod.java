package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SMethod {
   private final MethodNode method;

   public SMethod(final MethodNode method) {
      this.method = method;
   }

   public int maxLocals() {
      return method.maxLocals;
   }

   public int maxStack() {
      return method.maxStack;
   }

   public Instruction entry() {
      return Instructions.instructionFor(method.instructions.get(0));
   }

   public int argSize() {
      return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
   }
}
