package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.JavaConstants.CLINIT;
import static com.lexicalscope.symb.vm.JavaConstants.NOARGS_VOID_DESC;
import static org.objectweb.asm.Type.getInternalName;

import java.util.List;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.instructions.MethodCallInstruction;

public final class DefineClassOp implements Op<Boolean> {
   private final String klassName;

   public DefineClassOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public Boolean eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      boolean jumpToInitaliser = false;
      if (!statics.isDefined(klassName)) {
         InstructionNode currentInstruction = stackFrame.instruction();

         final List<SClass> klasses = statics.defineClass(klassName);

         final SClass classClass = statics.load(getInternalName(Class.class));

         for (final SClass klass : klasses) {
            allocateStatics(heap, statics, classClass, klass);

            if(klass.hasStaticInitialiser())
            {
               final InstructionInternalNode injectedInstruction = new InstructionInternalNode(MethodCallInstruction.createInvokeStatic(klass.name(), CLINIT, NOARGS_VOID_DESC));
               injectedInstruction.next(currentInstruction);
               currentInstruction = injectedInstruction;
               stackFrame.advance(injectedInstruction);
               jumpToInitaliser = true;
            }
         }
      }
      return jumpToInitaliser;
   }

   static void allocateStatics(final Heap heap, final Statics statics, final SClass classClass, final SClass klass) {
      if(klass.statics().fieldCount() > 0) {
         final Object staticsAddress = heap.newObject(klass.statics());
         heap.put(staticsAddress, SClass.OBJECT_CLASS_OFFSET, classClass);
         statics.staticsAt(klass, staticsAddress);
      }
   }

   @Override public String toString() {
      return "Define Class " + klassName;
   }
}