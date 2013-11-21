package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SMethod {
   private final MethodNode method;

   public SMethod(final MethodNode method) {
      this.method = method;
   }

   public Instruction entry() {
      return Instructions.instructionFor(method.instructions.get(0));
   }
}
