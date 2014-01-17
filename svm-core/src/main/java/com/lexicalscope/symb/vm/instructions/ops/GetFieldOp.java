package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.concinstructions.ops.FieldConversionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetFieldOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final PutFieldConversion conversion;

   public GetFieldOp(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);

      this.conversion = conversionForGetField(fieldConversionFactory, fieldInsnNode);
   }

   private PutFieldConversion conversionForGetField(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      PutFieldConversion conversion;
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

   @Override public void eval(final Context ctx) {
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