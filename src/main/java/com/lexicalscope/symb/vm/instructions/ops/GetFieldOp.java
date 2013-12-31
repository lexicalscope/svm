package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetFieldOp implements Vop {
   private interface GetFieldConversion {
      Object convert(Object val);
   }

   private static final class NoConversion implements GetFieldConversion {
      @Override public Object convert(final Object val) {
         return val;
      }
   }

   private static final class CharToInt implements GetFieldConversion {
      @Override public Object convert(final Object val) {
         return (int)(char) val;
      }
   }

   private final class IntToInt implements GetFieldConversion {
      @Override public Object convert(final Object val) {
         assert val instanceof Integer : name;
         return val;
      }
   }

   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final GetFieldConversion conversion;

   public GetFieldOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);

      this.conversion = conversionForGetField(fieldInsnNode);
   }

   private GetFieldConversion conversionForGetField(final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      GetFieldConversion conversion;
      switch (fieldType.getSort()) {
         case Type.CHAR:
            conversion = new CharToInt();
            break;

         case Type.INT:
            conversion = new IntToInt();
            break;

         default:
            conversion = new NoConversion();
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