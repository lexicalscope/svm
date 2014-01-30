package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;
   private final InstructionSource instructionSource;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
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

   @Override public Object initialFieldValue(final String desc) {
      final Type type = Type.getType(desc);
      final int sort = type.getSort();
      switch (sort) {
         case Type.OBJECT:
            return null;
         case Type.ARRAY:
            return null;
         case Type.CHAR:
            return (char) '\u0000';
         case Type.BYTE:
            return (byte) 0;
         case Type.SHORT:
            return (short) 0;
         case Type.INT:
            return instructionFactory.initInt();
         case Type.LONG:
            return 0L;
         case Type.FLOAT:
            return 0f;
         case Type.DOUBLE:
            return 0d;
         case Type.BOOLEAN:
            return false;
      }
      throw new UnsupportedOperationException("" + sort);
   }

   @Override public InstructionSource source() {
      return instructionSource;
   }
}
