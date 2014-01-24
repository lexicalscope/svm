package com.lexicalscope.svm.j.instruction.concrete.klass;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class LoadingInstruction implements Vop {
   private final List<String> klassNames;
   private final Vop op;
   private final Instructions instructions;

   public LoadingInstruction(final List<String> klassNames, final Vop op, final Instructions instructions) {
      this.klassNames = klassNames;
      this.op = op;
      this.instructions = instructions;
   }

   public LoadingInstruction(final String klassName, final Vop op, final Instructions instructions) {
      this(asList(klassName), op, instructions);
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
      final List<InstructionInternal> instructionsx = new ArrayList<>();
      final InstructionSink sink = new InstructionSink() {
         @Override public void noInstruction() { }

         @Override public void nextInstruction(final Vop node) {
            instructionsx.add(new InstructionInternal(node));
         }
      };
      MethodCallInstruction.createInvokeStatic(new AsmSMethodName(klass.name(), JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC), sink, instructions);
      MethodCallInstruction.createClassDefaultConstructor(klass.name(), sink);

      instructionsx.get(0).nextIs(instructionsx.get(1)).nextIs(currentInstruction);
      return instructionsx.get(0);
   }

   @Override public String toString() {
      return "(loading " + klassNames + ") " + op.toString();
   }
}
