package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;

public final class BaseInstructions implements Instructions {
   private final InstructionSource instructionSource;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionSource = new BaseInstructionSource(this, instructionFactory);
   }

   /*
    * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
    */

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   @Override public StatementBuilder statements() {
      return new StatementBuilder(this);
   }

   @Override public StatementBuilder before(final Instruction nextInstruction) {
      return new StatementBuilder(this, nextInstruction);
   }

   @Override public InstructionSource source() {
      return instructionSource;
   }
}
