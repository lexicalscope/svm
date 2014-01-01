package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.JavaConstants.*;
import static com.lexicalscope.symb.vm.instructions.ops.Ops.advanceTo;
import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class LoadingInstruction implements Instruction {
   private final List<String> klassNames;
   private final Vop op;

   public LoadingInstruction(final List<String> klassNames, final Vop op) {
      this.klassNames = klassNames;
      this.op = op;
   }

   public LoadingInstruction(final String klassName, final Vop op) {
      this(asList(klassName), op);
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      final List<SClass> definedClasses = state.op(new DefineClassOp(klassNames));
      if(definedClasses.isEmpty()){
         state.op(advanceTo(instruction.next()));
         state.op(op);
      } else {
         InstructionNode currentInstruction = instruction;
         for (final SClass klass : Lists.reverse(definedClasses)) {
            if(klass.hasStaticInitialiser())
            {
               currentInstruction = replaceCurrentInstructionWithInvocationOfStaticInitaliser(currentInstruction, klass);
            }
         }
         state.op(advanceTo(currentInstruction));
      }

   }

   private InstructionNode replaceCurrentInstructionWithInvocationOfStaticInitaliser(final InstructionNode currentInstruction, final SClass klass) {
      final InstructionInternalNode injectedInstruction = new InstructionInternalNode(MethodCallInstruction.createInvokeStatic(klass.name(), CLINIT, NOARGS_VOID_DESC));
      injectedInstruction.next(currentInstruction);
      return injectedInstruction;
   }

   @Override public String toString() {
      return op.toString();
   }
}
