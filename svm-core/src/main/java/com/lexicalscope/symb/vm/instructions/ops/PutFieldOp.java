package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.BaseInstructions.fieldKey;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.concinstructions.ops.FieldConversionFactory;

final class PutFieldOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final PutFieldConversion conversion;

   public PutFieldOp(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
      this.conversion = conversionForGetField(fieldConversionFactory, fieldInsnNode);
   }

   private PutFieldConversion conversionForGetField(final FieldConversionFactory fieldConversionFactory, final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      PutFieldConversion conversion;
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

   @Override
   public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
      // TODO[tim]: link should remove this
      final int offset = statics.load(fieldInsnNode.owner).fieldIndex(name);

      final Object val = stackFrame.pop();
      final Object obj = stackFrame.pop();

      heap.put(obj, offset, conversion.convert(val));
   }

   @Override
   public String toString() {
      return "PUTFIELD " + fieldKey(fieldInsnNode);
   }
}