package com.lexicalscope.symb.vm.j;


/**
 * Single-linked intra-procedural graph of instructions.
 *
 * @author tim
 */
public interface Instruction {
   void eval(State ctx);

   Instruction nextIs(Instruction instruction);
   void jmpTarget(Instruction instruction);

   boolean hasNext();
   Instruction next();
   Instruction jmpTarget();
}
