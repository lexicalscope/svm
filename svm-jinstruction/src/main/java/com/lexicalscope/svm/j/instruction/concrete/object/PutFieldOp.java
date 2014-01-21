package com.lexicalscope.svm.j.instruction.concrete.object;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.instruction.factory.BaseInstructions;
import com.lexicalscope.symb.klass.SFieldName;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public final class PutFieldOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final FieldConversion conversion;

   public PutFieldOp(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
      this.conversion = conversionForGetField(fieldConversionFactory, fieldInsnNode);
   }

   private FieldConversion conversionForGetField(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      FieldConversion conversion;
      switch (fieldType.getSort()) {
         case Type.CHAR:
            conversion = fieldConversionFactory.intToChar();
            break;

         case Type.INT:
            conversion = fieldConversionFactory.intToInt();
            break;

         default:
            conversion = fieldConversionFactory.noConversion();
            break;
      }
      return conversion;
   }

   @Override public void eval(final State ctx) {
      // TODO[tim]: link should remove this
      final int offset = ctx.load(fieldInsnNode.owner).fieldIndex(name);

      final Object val = ctx.pop();
      final Object obj = ctx.pop();

      ctx.put(obj, offset, conversion.convert(val));
   }

   @Override
   public String toString() {
      return "PUTFIELD " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}