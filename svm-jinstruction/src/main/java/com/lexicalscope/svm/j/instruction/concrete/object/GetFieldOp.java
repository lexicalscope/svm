package com.lexicalscope.svm.j.instruction.concrete.object;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.instruction.factory.BaseInstructions;
import com.lexicalscope.symb.vm.SFieldName;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public final class GetFieldOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final FieldConversion conversion;

   public GetFieldOp(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);

      this.conversion = conversionForGetField(fieldConversionFactory, fieldInsnNode);
   }

   private FieldConversion conversionForGetField(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      FieldConversion conversion;
      switch (fieldType.getSort()) {
         case Type.CHAR:
            conversion = fieldConversionFactory.charToInt();
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
      final Object obj = ctx.pop();

      ctx.push(conversion.convert(ctx.get(obj, offset)));
   }

   @Override
   public String toString() {
      return "GETFIELD " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}