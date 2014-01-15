package com.lexicalscope.symb.vm;


/**
 * Single-linked intra-procedural graph of instructions.
 *
 * @author tim
 */
public interface InstructionNode {
   void eval(Vm vm, State state);

   InstructionNode next(InstructionNode instruction);
   void jmpTarget(InstructionNode instruction);

   InstructionNode next();
   InstructionNode jmpTarget();
}
