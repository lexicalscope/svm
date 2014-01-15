package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
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

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      // TODO[tim]: link should remove this
      final int offset = statics.load(fieldInsnNode.owner).fieldIndex(name);
      final Object obj = stackFrame.pop();

      stackFrame.push(conversion.convert(heap.get(obj, offset)));
   }

   @Override
   public String toString() {
      return "GETFIELD " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}