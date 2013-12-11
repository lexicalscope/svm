package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.MethodCallInstruction.*;
import static com.lexicalscope.symb.vm.instructions.ops.Ops.*;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.instructions.ops.AConstNullOp;
import com.lexicalscope.symb.vm.instructions.ops.ANewArrayOp;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOp;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;
import com.lexicalscope.symb.vm.instructions.ops.Load;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOp;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Store;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
   }

   @Override public void instructionFor(
         final AbstractInsnNode abstractInsnNode,
         final InstructionSink instructionSink) {

      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LINE:
         case AbstractInsnNode.FRAME:
         case AbstractInsnNode.LABEL:
            instructionSink.noInstruction(abstractInsnNode);
            return;
      }

      instructionSink.nextInstruction(abstractInsnNode, instructionFor(abstractInsnNode));
   }

   /*
    * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
    */

   private Instruction instructionFor(final AbstractInsnNode abstractInsnNode) {
      switch (abstractInsnNode.getType()) {
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
                  return linearInstruction(putField(fieldInsnNode));
               case Opcodes.GETFIELD:
                  return linearInstruction(getField(fieldInsnNode));
               case Opcodes.GETSTATIC:
                  return loadingInstruction(fieldInsnNode, getStatic(fieldInsnNode));
               case Opcodes.PUTSTATIC:
                  return loadingInstruction(fieldInsnNode, putStatic(fieldInsnNode));
            }
            break;
         case AbstractInsnNode.INSN:
            final InsnNode insnNode = (InsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.ACONST_NULL:
                  return aconst_null();
               case Opcodes.RETURN:
                  return returnVoid();
               case Opcodes.IRETURN:
                  return return1();
               case Opcodes.ARETURN:
                  return return1();
               case Opcodes.IADD:
                  return binaryOp(instructionFactory.iaddOperation());
               case Opcodes.IMUL:
                  return binaryOp(instructionFactory.imulOperation());
               case Opcodes.ISUB:
                  return binaryOp(instructionFactory.isubOperation());
               case Opcodes.DUP:
                  return linearInstruction(dup());
               case Opcodes.ICONST_M1:
                  return iconst(-1);
               case Opcodes.ICONST_0:
                  return iconst_0();
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
         case AbstractInsnNode.LDC_INSN:
            final LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.LDC:
                  if(ldcInsnNode.cst instanceof Integer) {
                     return iconst((int) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof String) {
                     return stringPoolLoad((String) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Type) {
                     final Type toLoad = (Type) ldcInsnNode.cst;
                     if(toLoad.getSort()== Type.OBJECT) {
                        return objectPoolLoad(toLoad);
                     }
                  }
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
                  return newObject(typeInsnNode.desc);
               case Opcodes.ANEWARRAY:
                  return linearInstruction(new ANewArrayOp(typeInsnNode));
            }
            break;
         case AbstractInsnNode.JUMP_INSN:
            final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
            switch (jumpInsnNode.getOpcode()) {
               case Opcodes.IFGE:
                  return instructionFactory.branchIfGe(jumpInsnNode);
               case Opcodes.IFNE:
                  return instructionFactory.branchIfNe(jumpInsnNode);
               case Opcodes.IFNONNULL:
                  return instructionFactory.branchIfNonNull(jumpInsnNode);
               case Opcodes.GOTO:
                  return instructionFactory.branchGoto(jumpInsnNode);
            }
            break;
         case AbstractInsnNode.METHOD_INSN:
            final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESTATIC:
                  return createInvokeStatic(methodInsnNode);
               case Opcodes.INVOKESPECIAL:
                  return createInvokeSpecial(methodInsnNode);
               case Opcodes.INVOKEVIRTUAL:
                  return createInvokeVirtual(methodInsnNode);
            }
            break;
      }

      return new UnsupportedInstruction(abstractInsnNode);
   }

   private LinearInstruction store(final VarInsnNode varInsnNode) {
      return linearInstruction(new Store(varInsnNode.var));
   }

   private Instruction iconst(final int constVal) {
      return nullary(instructionFactory.iconst(constVal));
   }

   public Instruction aconst_null() {
      return linearInstruction(new AConstNullOp());
   }

   public Instruction iconst_0() {
      return iconst(0);
   }

   private Instruction stringPoolLoad(final String constVal) {
      return nullary(instructionFactory.stringPoolLoad(constVal));
   }

   private Instruction objectPoolLoad(final Type constVal) {
      return linearInstruction(new ObjectPoolLoad(constVal));
   }

   private LinearInstruction nullary(final NullaryOperator nullary) {
      return linearInstruction(new NullaryOp(nullary));
   }

   private LinearInstruction load(final VarInsnNode varInsnNode) {
      return linearInstruction(new Load(varInsnNode.var));
   }

   private LinearInstruction linearInstruction(final Vop op) {
      return new LinearInstruction(op);
   }

   private LoadingInstruction loadingInstruction(final FieldInsnNode fieldInsnNode, final Vop op) {
      return loadingInstruction(fieldInsnNode.owner, op);
   }

   private LoadingInstruction loadingInstruction(final TypeInsnNode typeInsnNode, final Vop op) {
      return loadingInstruction(op, typeInsnNode.desc);
   }

   private LoadingInstruction loadingInstruction(final Vop op, final String klassDesc) {
      return loadingInstruction(klassDesc, op);
   }

   private LoadingInstruction loadingInstruction(final String klassDesc, final Vop op) {
      return new LoadingInstruction(klassDesc, op);
   }

   private LinearInstruction binaryOp(final BinaryOperator addOperation) {
      return new LinearInstruction(new BinaryOp(addOperation));
   }

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   @Override public Instruction defineClass(final String klassName) {
      return new LinearInstruction(new DefineClassOp(klassName));
   }

   @Override public StatementBuilder statements() {
      return new StatementBuilder(this);
   }

   public Instruction returnVoid() {
      return new ReturnInstruction(0);
   }

   public Instruction return1() {
      return new ReturnInstruction(1);
   }

   public Instruction newObject(final String klassDesc) {
      return loadingInstruction(klassDesc, newOp(klassDesc));
   }
}
