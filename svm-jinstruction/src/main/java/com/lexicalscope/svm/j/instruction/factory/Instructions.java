package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;

public interface Instructions {
   StatementBuilder statements();
   StatementBuilder before(Instruction nextInstruction);

   InstructionSource source();
}