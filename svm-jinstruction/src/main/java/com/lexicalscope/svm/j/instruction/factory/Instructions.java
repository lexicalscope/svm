package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;

public interface Instructions {
   Object initialFieldValue(String desc);

   StatementBuilder statements();
   StatementBuilder before(Instruction nextInstruction);

   void loadArg(Object object, InstructionSource.InstructionSink sink);

   InstructionSource source();
}