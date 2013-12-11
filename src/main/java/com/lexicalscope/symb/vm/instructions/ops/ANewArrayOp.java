package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;

public class ANewArrayOp implements Vop {
   private final TypeInsnNode typeInsnNode;

   public ANewArrayOp(final TypeInsnNode typeInsnNode) {
      this.typeInsnNode = typeInsnNode;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object top = stackFrame.pop();
      // need to deal with symbolic lengths
      final int arrayLength;
      if(top instanceof IConstSymbol) {
         arrayLength = ((IConstSymbol) top).val();
      } else {
         arrayLength = (int) top;
      }
      stackFrame.push(heap.newObject(new Allocatable() {
         @Override public int fieldCount() {
            return arrayLength;
         }
      }));
   }
}
