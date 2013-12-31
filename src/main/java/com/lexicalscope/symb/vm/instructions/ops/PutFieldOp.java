package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.BaseInstructions.fieldKey;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SFieldName;

final class PutFieldOp implements Vop {
   private interface PutFieldConversion {
      Object convert(Object val);
   }

   private static final class IntToInt implements PutFieldConversion {
      @Override public Object convert(final Object val) {
         assert val instanceof Integer;
         return val;
      }
   }

   private static final class NoConversion implements PutFieldConversion {
      @Override public Object convert(final Object val) {
         return val;
      }
   }

   private static final class IntToChar implements PutFieldConversion {
      @Override public Object convert(final Object val) {
         return (char)(int) val;
      }
   }

   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final PutFieldConversion conversion;

   public PutFieldOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
      this.conversion = conversionForGetField(fieldInsnNode);
   }

   private PutFieldConversion conversionForGetField(final FieldInsnNode fieldInsnNode) {
      final Type fieldType = Type.getType(fieldInsnNode.desc);
      PutFieldConversion conversion;
      switch (fieldType.getSort()) {
         case Type.CHAR:
            conversion = new IntToChar();
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

   @Override
   public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
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