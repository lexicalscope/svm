package com.lexicalscope.symb.vm;


/**
 * Single-linked intra-procedural graph of instructions.
 *
 * @author tim
 */
public interface InstructionNode extends Vop {
   InstructionNode next(InstructionNode instruction);
   void jmpTarget(InstructionNode instruction);

   InstructionNode next();
   InstructionNode jmpTarget();
}
