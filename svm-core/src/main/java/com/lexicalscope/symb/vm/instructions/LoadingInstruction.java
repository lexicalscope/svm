package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.JavaConstants.*;
import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.AsmSMethodName;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class LoadingInstruction implements Vop {
   private final List<String> klassNames;
   private final Vop op;

   public LoadingInstruction(final List<String> klassNames, final Vop op) {
      this.klassNames = klassNames;
      this.op = op;
   }

   public LoadingInstruction(final String klassName, final Vop op) {
      this(asList(klassName), op);
   }

   @Override public void eval(final StateImpl ctx) {
      final List<SClass> definedClasses = new DefineClassOp(klassNames).eval(ctx);
      if(definedClasses.isEmpty()){
         new LinearInstruction(op).eval(ctx);
      } else {
         InstructionNode replacementInstruction = ctx.instruction();
         for (final SClass klass : Lists.reverse(definedClasses)) {
            if(klass.hasStaticInitialiser())
            {
               replacementInstruction = replaceCurrentInstructionWithInvocationOfStaticInitaliser(replacementInstruction, klass);
            }
         }
         ctx.advanceTo(replacementInstruction);
      }
   }

   private InstructionNode replaceCurrentInstructionWithInvocationOfStaticInitaliser(final InstructionNode currentInstruction, final SClass klass) {
      final InstructionInternalNode classConstructorInstruction = new InstructionInternalNode(MethodCallInstruction.createClassDefaultConstructor(klass.name()));
      final InstructionInternalNode staticInitialiserInstruction = new InstructionInternalNode(MethodCallInstruction.createInvokeStatic(new AsmSMethodName(klass.name(), CLINIT, NOARGS_VOID_DESC)));
      staticInitialiserInstruction.next(classConstructorInstruction).next(currentInstruction);
      return staticInitialiserInstruction;
   }

   @Override public String toString() {
      return "(loading " + klassNames + ") " + op.toString();
   }
}
