package com.lexicalscope.svm.j.instruction.concrete.klass;

import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.svm.j.instruction.concrete.InstructionInternal;
import com.lexicalscope.svm.j.instruction.concrete.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.MethodCallInstruction;
import com.lexicalscope.symb.code.AsmSMethodName;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.SClass;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

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

   @Override public void eval(final State ctx) {
      final List<SClass> definedClasses = new DefineClassOp(klassNames).eval(ctx);
      if(definedClasses.isEmpty()){
         new LinearInstruction(op).eval(ctx);
      } else {
         Instruction replacementInstruction = ctx.instruction();
         for (final SClass klass : Lists.reverse(definedClasses)) {
            if(klass.hasStaticInitialiser())
            {
               replacementInstruction = replaceCurrentInstructionWithInvocationOfStaticInitaliser(replacementInstruction, klass);
            }
         }
         ctx.advanceTo(replacementInstruction);
      }
   }

   private Instruction replaceCurrentInstructionWithInvocationOfStaticInitaliser(final Instruction currentInstruction, final SClass klass) {
      final InstructionInternal classConstructorInstruction = new InstructionInternal(MethodCallInstruction.createClassDefaultConstructor(klass.name()));
      final InstructionInternal staticInitialiserInstruction = new InstructionInternal(MethodCallInstruction.createInvokeStatic(new AsmSMethodName(klass.name(), JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC)));
      staticInitialiserInstruction.nextIs(classConstructorInstruction).nextIs(currentInstruction);
      return staticInitialiserInstruction;
   }

   @Override public String toString() {
      return "(loading " + klassNames + ") " + op.toString();
   }
}
