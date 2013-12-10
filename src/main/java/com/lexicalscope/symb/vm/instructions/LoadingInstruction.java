package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.nextInstruction;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class LoadingInstruction implements Instruction {
   private final SClassLoader classLoader;
   private final String klassName;
   private final Vop op;

   public LoadingInstruction(final SClassLoader classLoader, final String klassName, final Vop op) {
      this.classLoader = classLoader;
      this.klassName = klassName;
      this.op = op;
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      if(!classLoader.load(klassName).initialised(state)) {
//         vm.initKlass(klassName);
      } else {
         state.op(nextInstruction(instruction));
         state.op(op);
      }
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
