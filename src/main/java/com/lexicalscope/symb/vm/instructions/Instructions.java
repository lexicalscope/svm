package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.ops.IAddOp;

public class Instructions {

   public static Instruction instructionFor(final AbstractInsnNode abstractInsnNode) {
      if(abstractInsnNode == null) return new Terminate();

      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LABEL:  return new Label((LabelNode) abstractInsnNode);
         case AbstractInsnNode.LINE:  return new LineNumber((LineNumberNode) abstractInsnNode);

      default:
         switch (abstractInsnNode.getOpcode()) {
         case Opcodes.RETURN: return new Return(abstractInsnNode);
         case Opcodes.IRETURN: return new Ireturn((InsnNode) abstractInsnNode);
         case Opcodes.ILOAD:  return new Iload((VarInsnNode) abstractInsnNode);
         case Opcodes.IADD:   return new LinearInstruction(abstractInsnNode, new OperandsTransformer(new IAddOp()));
         default:
            return new UnsupportedInstruction(abstractInsnNode);
         }
      }
   }
}
