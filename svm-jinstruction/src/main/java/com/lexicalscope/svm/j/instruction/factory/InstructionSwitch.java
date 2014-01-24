package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.svm.j.instruction.UnsupportedInstruction;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class InstructionSwitch {
   private final InstructionSource s;

   public InstructionSwitch(final InstructionSource s) {
      this.s = s;
   }

   public Vop instructionFor(final AbstractInsnNode abstractInsnNode) {
      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.VAR_INSN:
            final VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.LLOAD:
               case Opcodes.DLOAD:
                  return s.load2(varInsnNode.var);
               case Opcodes.ILOAD:
               case Opcodes.ALOAD:
               case Opcodes.FLOAD:
                  return s.load(varInsnNode.var);
               case Opcodes.LSTORE:
                  return s.store2(varInsnNode.var);
               case Opcodes.ISTORE:
               case Opcodes.ASTORE:
                  return s.store(varInsnNode.var);
            }
            break;
         case AbstractInsnNode.FIELD_INSN:
            final FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.PUTFIELD:
                  return s.putField(fieldInsnNode);
               case Opcodes.GETFIELD:
                  return s.getField(fieldInsnNode);
               case Opcodes.GETSTATIC:
                  return s.getStaticField(fieldInsnNode);
               case Opcodes.PUTSTATIC:
                  return s.putStaticField(fieldInsnNode);
            }
            break;
         case AbstractInsnNode.INSN:
            final InsnNode insnNode = (InsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.ACONST_NULL:
                  return s.aconst_null();
               case Opcodes.RETURN:
                  return s.returnVoid();
               case Opcodes.IRETURN:
                  return s.return1();
               case Opcodes.FRETURN:
                  return s.return1();
               case Opcodes.LRETURN:
                  return s.return2();
               case Opcodes.ARETURN:
                  return s.return1();
               case Opcodes.IAND:
                  return s.iand();
               case Opcodes.LAND:
                  return s.land();
               case Opcodes.IADD:
                  return s.iadd();
               case Opcodes.IMUL:
                  return s.imul();
               case Opcodes.FMUL:
                  return s.fmul();
               case Opcodes.FDIV:
                  return s.fdiv();
               case Opcodes.ISUB:
                  return s.isub();
               case Opcodes.INEG:
                  return s.ineg();
               case Opcodes.DUP:
                  return s.dup();
               case Opcodes.DUP_X1:
                  return s.dup_x1();
               case Opcodes.ICONST_M1:
                  return s.iconst_m1();
               case Opcodes.ICONST_0:
                  return s.iconst_0();
               case Opcodes.ICONST_1:
                  return s.iconst_1();
               case Opcodes.ICONST_2:
                  return s.iconst_2();
               case Opcodes.ICONST_3:
                  return s.iconst_3();
               case Opcodes.ICONST_4:
                  return s.iconst_4();
               case Opcodes.ICONST_5:
                  return s.iconst_5();
               case Opcodes.LCONST_0:
                  return s.lconst_0();
               case Opcodes.LCONST_1:
                  return s.lconst_1();
               case Opcodes.FCONST_0:
                  return s.fconst_0();
               case Opcodes.CASTORE:
                  return s.caStore();
               case Opcodes.IASTORE:
                  return s.iaStore();
               case Opcodes.AASTORE:
                  return s.aaStore();
               case Opcodes.CALOAD:
                  return s.caload();
               case Opcodes.IALOAD:
                  return s.iaload();
               case Opcodes.AALOAD:
                  return s.aaload();
               case Opcodes.ARRAYLENGTH:
                  return s.arrayLength();
               case Opcodes.ISHL:
                  return s.ishl();
               case Opcodes.ISHR:
                   return s.ishr();
               case Opcodes.IUSHR:
                  return s.iushr();
               case Opcodes.IOR:
                  return s.ior();
               case Opcodes.IXOR:
                  return s.ixor();
               case Opcodes.LUSHR:
                  return s.lushr();
               case Opcodes.I2L:
                  return s.i2l();
               case Opcodes.L2I:
                  return s.l2i();
               case Opcodes.I2F:
                  return s.i2f();
               case Opcodes.F2I:
                  return s.f2i();
               case Opcodes.FCMPG:
                  return s.fcmpg();
               case Opcodes.FCMPL:
                  return s.fcmpl();
               case Opcodes.LCMP:
                  return s.lcmp();
               case Opcodes.POP:
                  return s.pop();
            }
            break;
         case AbstractInsnNode.LDC_INSN:
            final LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.LDC:
                  if(ldcInsnNode.cst instanceof Integer) {
                     return s.ldcInt((int) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Long) {
                     return s.ldcLong((long) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Float) {
                     return s.ldcFloat((float) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Double) {
                     return s.ldcDouble((double) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof String) {
                     return s.stringPoolLoad((String) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Type) {
                     final Type toLoad = (Type) ldcInsnNode.cst;
                     if(toLoad.getSort() == Type.OBJECT || toLoad.getSort() == Type.ARRAY) {
                        return s.objectPoolLoad(toLoad);
                     }
                  }
                  // System.out.println("!!!!!!!! " + ldcInsnNode.cst + " " + ldcInsnNode.cst.getClass());
            }
            break;
         case AbstractInsnNode.INT_INSN:
            final IntInsnNode intInsnNode = (IntInsnNode) abstractInsnNode;
            final int val = intInsnNode.operand;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.SIPUSH:
                  return s.sipush(val);
               case Opcodes.BIPUSH:
                  return s.bipush(val);
               case Opcodes.NEWARRAY:
                  return s.newarray(val);
            }
            break;
         case AbstractInsnNode.IINC_INSN:
            final IincInsnNode iincInsnNode = (IincInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.IINC:
                  return s.iinc(iincInsnNode);
            }
            break;
         case AbstractInsnNode.TYPE_INSN:
            final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.NEW:
                  return s.newObject(typeInsnNode.desc);
               case Opcodes.ANEWARRAY:
                  return s.anewarray();
               case Opcodes.INSTANCEOF:
                  return s.instance0f(typeInsnNode);
               case Opcodes.CHECKCAST:
                  return s.checkcast(typeInsnNode);
            }
            break;
         case AbstractInsnNode.JUMP_INSN:
            final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
            switch (jumpInsnNode.getOpcode()) {
               case Opcodes.IFGE:
                  return s.ifge(jumpInsnNode);
               case Opcodes.IFGT:
                  return s.ifgt(jumpInsnNode);
               case Opcodes.IFLE:
                  return s.ifle(jumpInsnNode);
               case Opcodes.IFLT:
                  return s.iflt(jumpInsnNode);
               case Opcodes.IFEQ:
                  return s.ifeq(jumpInsnNode);
               case Opcodes.IFNE:
                  return s.ifne(jumpInsnNode);
               case Opcodes.IFNULL:
                  return s.ifnull(jumpInsnNode);
               case Opcodes.IFNONNULL:
                  return s.ifnonnull(jumpInsnNode);
               case Opcodes.IF_ICMPEQ:
                  return s.ificmpeq(jumpInsnNode);
               case Opcodes.IF_ICMPNE:
                  return s.ificmpne(jumpInsnNode);
               case Opcodes.IF_ICMPLE:
                  return s.ificmple(jumpInsnNode);
               case Opcodes.IF_ICMPLT:
                  return s.ificmplt(jumpInsnNode);
               case Opcodes.IF_ICMPGT:
                  return s.ificmpgt(jumpInsnNode);
               case Opcodes.IF_ICMPGE:
                  return s.ificmpge(jumpInsnNode);
               case Opcodes.IF_ACMPNE:
                  return s.ifacmpne(jumpInsnNode);
               case Opcodes.GOTO:
                  return s.got0();
            }
            break;
         case AbstractInsnNode.METHOD_INSN:
            final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            final SMethodDescriptor name = new AsmSMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESTATIC:
                  return s.invokestatic(name);
               case Opcodes.INVOKESPECIAL:
                  return s.invokespecial(name);
               case Opcodes.INVOKEINTERFACE:
                  return s.invokeinterface(name);
               case Opcodes.INVOKEVIRTUAL:
                  return s.invokevirtual(name);
            }
            break;
      }

      return new UnsupportedInstruction(abstractInsnNode);
   }
}
