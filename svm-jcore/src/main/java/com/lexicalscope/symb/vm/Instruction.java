package com.lexicalscope.symb.vm;


/**
 * Single-linked intra-procedural graph of instructions.
 *
 * @author tim
 */
public interface Instruction extends Vop {
   Instruction nextIs(Instruction instruction);
   void jmpTarget(Instruction instruction);

   Instruction next();
   Instruction jmpTarget();
}