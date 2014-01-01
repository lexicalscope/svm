package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.JavaConstants.*;
import static com.lexicalscope.symb.vm.instructions.ops.Ops.advanceTo;

import java.util.List;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class LoadingInstruction implements Instruction {
   private final String klassDesc;
   private final Vop op;

   public LoadingInstruction(final String klassDesc, final Vop op) {
      this.klassDesc = klassDesc;
      this.op = op;
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      final List<SClass> definedClasses = state.op(new DefineClassOp(klassDesc));
      if(definedClasses.isEmpty()){
         state.op(advanceTo(instruction.next()));
         state.op(op);
      } else {
         InstructionNode currentInstruction = instruction;
         for (final SClass klass : definedClasses) {
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
