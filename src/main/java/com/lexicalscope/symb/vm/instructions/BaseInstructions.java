package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.*;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.TerminateInstruction;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOp;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.DupOp;
import com.lexicalscope.symb.vm.instructions.ops.Load;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOp;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Store;
import com.lexicalscope.symb.vm.instructions.transformers.HeapTransformer;
import com.lexicalscope.symb.vm.instructions.transformers.StackFrameTransformer;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
   }

   @Override public InstructionNode instructionFor(final SClassLoader classLoader, final AbstractInsnNode abstractInsnNode, final InstructionNode previous) {
      if (abstractInsnNode == null)
         return new TerminateInstruction();
      return new InstructionInternalNode(classLoader, instructionTransformFor(classLoader, abstractInsnNode), previous);
   }

   private Instruction instructionTransformFor(final SClassLoader classLoader, final AbstractInsnNode abstractInsnNode) {
      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LABEL:
            return noop("LABEL");
         case AbstractInsnNode.LINE:
            return new LineNumber((LineNumberNode) abstractInsnNode);
         case AbstractInsnNode.FRAME:
            return noop("FRAME");
         case AbstractInsnNode.VAR_INSN:
            final VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.ILOAD:
               case Opcodes.ALOAD:
                  return load(varInsnNode);
               case Opcodes.ISTORE:
               case Opcodes.ASTORE:
                  return store(varInsnNode);
            }
            break;
         case AbstractInsnNode.FIELD_INSN:
            final FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.PUTFIELD:
                  return heapOp(putField(classLoader, fieldInsnNode));
               case Opcodes.GETFIELD:
                  return heapOp(getField(classLoader, fieldInsnNode));
            }
            break;
         case AbstractInsnNode.INSN:
            final InsnNode insnNode = (InsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.RETURN:
                  return new Return(0);
               case Opcodes.IRETURN:
                  return new Return(1);
               case Opcodes.IADD:
                  return binaryOp(instructionFactory.iaddOperation());
               case Opcodes.IMUL:
                  return binaryOp(instructionFactory.imulOperation());
               case Opcodes.ISUB:
                  return binaryOp(instructionFactory.isubOperation());
               case Opcodes.DUP:
                  return stackOp(dup());
               case Opcodes.ICONST_M1:
                  return iconst(-1);
               case Opcodes.ICONST_0:
                  return iconst(0);
               case Opcodes.ICONST_1:
                  return iconst(1);
               case Opcodes.ICONST_2:
                  return iconst(2);
               case Opcodes.ICONST_3:
                  return iconst(3);
               case Opcodes.ICONST_4:
                  return iconst(4);
               case Opcodes.ICONST_5:
                  return iconst(5);
            }
            break;
         case AbstractInsnNode.INT_INSN:
            final IntInsnNode intInsnNode = (IntInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.BIPUSH:
                  return iconst(intInsnNode.operand);
            }
            break;
         case AbstractInsnNode.TYPE_INSN:
            final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.NEW:
                  return heapOp(newOp(classLoader, typeInsnNode));
            }
            break;
         case AbstractInsnNode.JUMP_INSN:
            final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
            switch (jumpInsnNode.getOpcode()) {
               case Opcodes.IFGE:
                  return instructionFactory.branchIfge(jumpInsnNode);
            }
            break;
         case AbstractInsnNode.METHOD_INSN:
            final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESPECIAL:
                  return new InvokeSpecial(classLoader, methodInsnNode);
               case Opcodes.INVOKEVIRTUAL:
                  return new InvokeVirtual(classLoader, methodInsnNode);
            }
            break;
      }

      return new UnsupportedInstruction(abstractInsnNode);
   }

   private LinearInstruction noop(final String description) {
      return new LinearInstruction(new Noop(description));
   }

   private Instruction stackOp(final DupOp stackOp) {
      return linearInstruction(new StackFrameTransformer(stackOp));
   }

   private LinearInstruction heapOp(final HeapVop heapOp) {
      return linearInstruction(new HeapTransformer(heapOp));
   }

   private LinearInstruction store(final VarInsnNode varInsnNode) {
      return linearInstruction(new StackFrameTransformer(new Store(varInsnNode.var)));
   }

   private Instruction iconst(final int constVal) {
      return nullary(instructionFactory.iconst(constVal));
   }

   private LinearInstruction nullary(final NullaryOperator nullary) {
      return linearInstruction(new StackFrameTransformer(new NullaryOp(nullary)));
   }

   private LinearInstruction load(final VarInsnNode varInsnNode) {
      return linearInstruction(new StackFrameTransformer(new Load(varInsnNode.var)));
   }

   private LinearInstruction linearInstruction(final StateTransformer transformer) {
      return new LinearInstruction(transformer);
   }

   private LinearInstruction binaryOp(final BinaryOperator addOperation) {
      return new LinearInstruction(new StackFrameTransformer(new BinaryOp(addOperation)));
   }

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }
}
