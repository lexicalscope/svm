package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class PutStaticOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final boolean doubleWord;

   PutStaticOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
      final int sort = Type.getType(fieldInsnNode.desc).getSort();
      doubleWord = sort == Type.DOUBLE || sort == Type.LONG;
   }

   @Override public void eval(final StateImpl ctx) {
      final SClass klass = ctx.load(fieldInsnNode.owner);
      final Object staticsAddress = ctx.whereMyStaticsAt(klass);
      final int offset = klass.staticFieldIndex(name);

      Object value;
      if(doubleWord) {
         value = ctx.popDoubleWord();
      } else {
         value = ctx.pop();
      }
      ctx.put(staticsAddress, offset, value);
   }

   @Override
   public String toString() {
      return "PUTSTATIC " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}