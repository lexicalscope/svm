package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.MethodCallInstruction.*;
import static com.lexicalscope.symb.vm.instructions.ops.ArrayLoadOp.*;
import static com.lexicalscope.symb.vm.instructions.ops.ArrayStoreOp.*;
import static com.lexicalscope.symb.vm.instructions.ops.Ops.*;

import java.util.List;

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

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.concinstructions.ops.StringPoolLoadOperator;
import com.lexicalscope.symb.vm.concinstructions.predicates.Unconditional;
import com.lexicalscope.symb.vm.instructions.ops.AConstNullOp;
import com.lexicalscope.symb.vm.instructions.ops.AddressToHashCodeOp;
import com.lexicalscope.symb.vm.instructions.ops.ArrayCopyOp;
import com.lexicalscope.symb.vm.instructions.ops.ArrayLengthOp;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Op;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOp;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.CheckCastOp;
import com.lexicalscope.symb.vm.instructions.ops.CurrentThreadOp;
import com.lexicalscope.symb.vm.instructions.ops.CurrentTimeMillisOp;
import com.lexicalscope.symb.vm.instructions.ops.DoubleToRawLongBits;
import com.lexicalscope.symb.vm.instructions.ops.F2IOp;
import com.lexicalscope.symb.vm.instructions.ops.FCmpGOperator;
import com.lexicalscope.symb.vm.instructions.ops.FCmpLOperator;
import com.lexicalscope.symb.vm.instructions.ops.FloatToRawIntBits;
import com.lexicalscope.symb.vm.instructions.ops.GetCallerClass;
import com.lexicalscope.symb.vm.instructions.ops.GetPrimitiveClass;
import com.lexicalscope.symb.vm.instructions.ops.I2FOp;
import com.lexicalscope.symb.vm.instructions.ops.I2LOp;
import com.lexicalscope.symb.vm.instructions.ops.IincOp;
import com.lexicalscope.symb.vm.instructions.ops.InitThreadOp;
import com.lexicalscope.symb.vm.instructions.ops.InstanceOfOp;
import com.lexicalscope.symb.vm.instructions.ops.IorOp;
import com.lexicalscope.symb.vm.instructions.ops.IshlOp;
import com.lexicalscope.symb.vm.instructions.ops.IushrOp;
import com.lexicalscope.symb.vm.instructions.ops.IxorOp;
import com.lexicalscope.symb.vm.instructions.ops.L2IOp;
import com.lexicalscope.symb.vm.instructions.ops.LCmpOp;
import com.lexicalscope.symb.vm.instructions.ops.Load;
import com.lexicalscope.symb.vm.instructions.ops.Load2;
import com.lexicalscope.symb.vm.instructions.ops.LushrOp;
import com.lexicalscope.symb.vm.instructions.ops.NanoTimeOp;
import com.lexicalscope.symb.vm.instructions.ops.NoOp;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Op;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOp;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.PopOp;
import com.lexicalscope.symb.vm.instructions.ops.Store;
import com.lexicalscope.symb.vm.instructions.ops.Store2;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOp;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;

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
               case Opcodes.LLOAD:
               case Opcodes.DLOAD:
                  return load2(varInsnNode);
               case Opcodes.ILOAD:
               case Opcodes.ALOAD:
               case Opcodes.FLOAD:
                  return load(varInsnNode);
               case Opcodes.LSTORE:
                  return store2(varInsnNode);
               case Opcodes.ISTORE:
               case Opcodes.ASTORE:
                  return store(varInsnNode);
            }
            break;
         case AbstractInsnNode.FIELD_INSN:
            final FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.PUTFIELD:
                  return linearInstruction(instructionFactory.putField(fieldInsnNode));
               case Opcodes.GETFIELD:
                  return linearInstruction(instructionFactory.getField(fieldInsnNode));
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
               case Opcodes.FRETURN:
                  return return1();
               case Opcodes.LRETURN:
                  return return2();
               case Opcodes.ARETURN:
                  return return1();
               case Opcodes.IAND:
                  return binaryOp(instructionFactory.iandOperation());
               case Opcodes.LAND:
                  return binary2Op(instructionFactory.landOperation());
               case Opcodes.IADD:
                  return binaryOp(instructionFactory.iaddOperation());
               case Opcodes.IMUL:
                  return binaryOp(instructionFactory.imulOperation());
               case Opcodes.FMUL:
                  return binaryOp(instructionFactory.fmulOperation());
               case Opcodes.FDIV:
                  return binaryOp(instructionFactory.fdivOperation());
               case Opcodes.ISUB:
                  return binaryOp(instructionFactory.isubOperation());
               case Opcodes.INEG:
                  return unaryOp(instructionFactory.inegOperation());
               case Opcodes.DUP:
                  return linearInstruction(dup());
               case Opcodes.DUP_X1:
                  return linearInstruction(dup_x1());
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
               case Opcodes.LCONST_0:
                  return lconst(0);
               case Opcodes.LCONST_1:
                  return lconst(1);
               case Opcodes.FCONST_0:
                  return fconst_0();
               case Opcodes.CASTORE:
                  return linearInstruction(caStore());
               case Opcodes.IASTORE:
               case Opcodes.AASTORE:
                  return linearInstruction(aaStore());
               case Opcodes.CALOAD:
                  return linearInstruction(caLoad());
               case Opcodes.IALOAD:
               case Opcodes.AALOAD:
                  return linearInstruction(aaLoad());
               case Opcodes.ARRAYLENGTH:
                  return linearInstruction(new ArrayLengthOp());
               case Opcodes.ISHL:
                  return linearInstruction(new IshlOp());
               case Opcodes.IUSHR:
                  return linearInstruction(new IushrOp());
               case Opcodes.IOR:
                  return linearInstruction(new IorOp());
               case Opcodes.IXOR:
                  return linearInstruction(new IxorOp());
               case Opcodes.LUSHR:
                  return linearInstruction(new LushrOp());
               case Opcodes.I2L:
                  return linearInstruction(new I2LOp());
               case Opcodes.L2I:
                  return linearInstruction(new L2IOp());
               case Opcodes.I2F:
                  return linearInstruction(new I2FOp());
               case Opcodes.F2I:
                  return linearInstruction(new F2IOp());
               case Opcodes.FCMPG:
                  return binaryOp(new FCmpGOperator());
               case Opcodes.FCMPL:
                  return binaryOp(new FCmpLOperator());
               case Opcodes.LCMP:
                  return linearInstruction(new LCmpOp());
               case Opcodes.POP:
                  return linearInstruction(new PopOp());
            }
            break;
         case AbstractInsnNode.LDC_INSN:
            final LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.LDC:
                  if(ldcInsnNode.cst instanceof Integer) {
                     return iconst((int) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Long) {
                     return lconst((long) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Float) {
                     return fconst((float) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Double) {
                     return dconst((double) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof String) {
                     return stringPoolLoad((String) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Type) {
                     final Type toLoad = (Type) ldcInsnNode.cst;
                     if(toLoad.getSort() == Type.OBJECT || toLoad.getSort() == Type.ARRAY) {
                        return objectPoolLoad(toLoad);
                     }
                  }
                  // System.out.println("!!!!!!!! " + ldcInsnNode.cst + " " + ldcInsnNode.cst.getClass());
            }
            break;
         case AbstractInsnNode.INT_INSN:
            final IntInsnNode intInsnNode = (IntInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.SIPUSH:
               case Opcodes.BIPUSH:
                  return iconst(intInsnNode.operand);
               case Opcodes.NEWARRAY:
                  return linearInstruction(instructionFactory.newArray(initialFieldValue(intInsnNode.operand)));
            }
            break;
         case AbstractInsnNode.IINC_INSN:
            final IincInsnNode iincInsnNode = (IincInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.IINC:
                  return linearInstruction(new IincOp(iincInsnNode.var, iincInsnNode.incr));
            }
            break;
         case AbstractInsnNode.TYPE_INSN:
            final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.NEW:
                  return newObject(typeInsnNode.desc);
               case Opcodes.ANEWARRAY:
                  return linearInstruction(instructionFactory.aNewArray());
               case Opcodes.INSTANCEOF:
                  return linearInstruction(new InstanceOfOp(typeInsnNode.desc));
               case Opcodes.CHECKCAST:
                  return linearInstruction(new CheckCastOp(typeInsnNode.desc));
            }
            break;
         case AbstractInsnNode.JUMP_INSN:
            final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
            switch (jumpInsnNode.getOpcode()) {
               case Opcodes.IFGE:
                  return instructionFactory.branchIfGe(jumpInsnNode);
               case Opcodes.IFGT:
                  return instructionFactory.branchIfGt(jumpInsnNode);
               case Opcodes.IFLE:
                  return instructionFactory.branchIfLe(jumpInsnNode);
               case Opcodes.IFLT:
                  return instructionFactory.branchIfLt(jumpInsnNode);
               case Opcodes.IFEQ:
                  return instructionFactory.branchIfEq(jumpInsnNode);
               case Opcodes.IFNE:
                  return instructionFactory.branchIfNe(jumpInsnNode);
               case Opcodes.IFNULL:
                  return instructionFactory.branchIfNull(jumpInsnNode);
               case Opcodes.IFNONNULL:
                  return instructionFactory.branchIfNonNull(jumpInsnNode);
               case Opcodes.IF_ICMPEQ:
                  return instructionFactory.branchIfICmpEq(jumpInsnNode);
               case Opcodes.IF_ICMPNE:
                  return instructionFactory.branchIfICmpNe(jumpInsnNode);
               case Opcodes.IF_ICMPLE:
                  return instructionFactory.branchIfICmpLe(jumpInsnNode);
               case Opcodes.IF_ICMPLT:
                  return instructionFactory.branchIfICmpLt(jumpInsnNode);
               case Opcodes.IF_ICMPGT:
                  return instructionFactory.branchIfICmpGt(jumpInsnNode);
               case Opcodes.IF_ICMPGE:
                  return instructionFactory.branchIfICmpGe(jumpInsnNode);
               case Opcodes.IF_ACMPNE:
                  return instructionFactory.branchIfACmpNe(jumpInsnNode);
               case Opcodes.GOTO:
                  return new BranchInstruction(new Unconditional());
            }
            break;
         case AbstractInsnNode.METHOD_INSN:
            final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            final SMethodName name = new SMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESTATIC:
                  return createInvokeStatic(name);
               case Opcodes.INVOKESPECIAL:
                  return MethodCallInstruction.createInvokeSpecial(name);
               case Opcodes.INVOKEINTERFACE:
                  return MethodCallInstruction.createInvokeInterface(name);
               case Opcodes.INVOKEVIRTUAL:
                  return createInvokeVirtual(name);
            }
            break;
      }

      return new UnsupportedInstruction(abstractInsnNode);
   }

   private LinearInstruction store(final VarInsnNode varInsnNode) {
      return linearInstruction(new Store(varInsnNode.var));
   }

   private LinearInstruction store2(final VarInsnNode varInsnNode) {
      return linearInstruction(new Store2(varInsnNode.var));
   }

   Instruction iconst(final int constVal) {
      return nullary(instructionFactory.iconst(constVal));
   }

   public Instruction lconst(final long constVal) {
      return nullary2(instructionFactory.lconst(constVal));
   }

   private Instruction fconst(final float constVal) {
      return nullary(instructionFactory.fconst(constVal));
   }

   private Instruction dconst(final double constVal) {
      return nullary2(instructionFactory.dconst(constVal));
   }

   public Instruction aconst_null() {
      return linearInstruction(new AConstNullOp());
   }

   public Instruction iconst_0() {
      return iconst(0);
   }

   public Instruction fconst_0() {
      return fconst(0);
   }

   private Instruction stringPoolLoad(final String constVal) {
      return linearInstruction(new StringPoolLoadOperator(constVal));
   }

   private Instruction objectPoolLoad(final Type constVal) {
      return linearInstruction(new ObjectPoolLoad(constVal));
   }

   private LinearInstruction nullary(final NullaryOperator nullary) {
      return linearInstruction(new NullaryOp(nullary));
   }

   private LinearInstruction nullary2(final Nullary2Operator nullary) {
      return linearInstruction(new Nullary2Op(nullary));
   }

   private LinearInstruction load(final VarInsnNode varInsnNode) {
      return load(varInsnNode.var);
   }

   private LinearInstruction load2(final VarInsnNode varInsnNode) {
      return load2(varInsnNode.var);
   }

   private LinearInstruction load(final int index) {
      return linearInstruction(new Load(index));
   }

   private LinearInstruction load2(final int index) {
      return linearInstruction(new Load2(index));
   }

   public Instruction aload(final int index) {
      return load(index);
   }

   public Instruction fload(final int index) {
      return load(index);
   }

   public Instruction dload(final int index) {
      return load2(index);
   }

   private LinearInstruction linearInstruction(final Vop op) {
      return new LinearInstruction(op);
   }

   private LoadingInstruction loadingInstruction(final FieldInsnNode fieldInsnNode, final Vop op) {
      return loadingInstruction(fieldInsnNode.owner, op);
   }

   private LoadingInstruction loadingInstruction(final String klassDesc, final Vop op) {
      return new LoadingInstruction(klassDesc, op);
   }

   private LinearInstruction binaryOp(final BinaryOperator operation) {
      return new LinearInstruction(new BinaryOp(operation));
   }

   private LinearInstruction binary2Op(final Binary2Operator operation) {
      return new LinearInstruction(new Binary2Op(operation));
   }

   private LinearInstruction unaryOp(final UnaryOperator operation) {
      return new LinearInstruction(new UnaryOp(operation));
   }

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   @Override public Instruction defineClass(final List<String> klassNames) {
      return new LoadingInstruction(klassNames, new NoOp());
   }

   @Override public Instruction initThread() {
      return new LinearInstruction(new InitThreadOp());
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

   public Instruction return2() {
      return new ReturnInstruction(2);
   }

   public Instruction newObject(final String klassDesc) {
      return loadingInstruction(klassDesc, newOp(klassDesc));
   }

   public Instruction addressToHashCode() {
      return linearInstruction(new AddressToHashCodeOp());
   }

   public Instruction nanoTime() {
      return linearInstruction(new NanoTimeOp());
   }

   public Instruction currentTimeMillis() {
      return linearInstruction(new CurrentTimeMillisOp());
   }

   @Override public Instruction createInvokeSpecial(final SMethodName sMethodName) {
      return MethodCallInstruction.createInvokeSpecial(sMethodName);
   }

   public Instruction invokeInterface(final String klassName, final String methodName, final String desc) {
      return invokeInterface(new SMethodName(klassName, methodName, desc));
   }

   private Instruction invokeInterface(final SMethodName sMethodName) {
      return MethodCallInstruction.createInvokeInterface(sMethodName);
   }

   public Instruction currentThread() {
      return linearInstruction(new CurrentThreadOp());
   }

   public Instruction arrayCopy() {
      return linearInstruction(new ArrayCopyOp());
   }

   public Instruction floatToRawIntBits() {
      return linearInstruction(new FloatToRawIntBits());
   }

   public Instruction doubleToRawLongBits() {
      return linearInstruction(new DoubleToRawLongBits());
   }

   public Instruction getCallerClass() {
      return linearInstruction(new GetCallerClass());
   }

   public Instruction getPrimitiveClass() {
      return linearInstruction(new GetPrimitiveClass());
   }

   @Override public Object initialFieldValue(final String desc) {
      final Type type = Type.getType(desc);
      final int sort = type.getSort();
      switch (sort) {
         case Type.OBJECT:
            return null;
         case Type.ARRAY:
            return null;
         case Type.CHAR:
            return (char) '\u0000';
         case Type.BYTE:
            return (byte) 0;
         case Type.SHORT:
            return (short) 0;
         case Type.INT:
            return instructionFactory.initInt();
         case Type.LONG:
            return 0L;
         case Type.FLOAT:
            return 0f;
         case Type.DOUBLE:
            return 0d;
         case Type.BOOLEAN:
            return false;
      }
      throw new UnsupportedOperationException("" + sort);
   }

   private Object initialFieldValue(final int atype) {
      /*
       * Array Type  atype
       * T_BOOLEAN    4
       * T_CHAR       5
       * T_FLOAT      6
       * T_DOUBLE     7
       * T_BYTE       8
       * T_SHORT      9
       * T_INT       10
       * T_LONG      11
       */
      switch (atype) {
         case 4:
            return false;
         case 5:
            return (char) '\u0000';
         case 6:
            return (float) 0f;
         case 7:
            return (double) 0d;
         case 8:
            return (byte) 0;
         case 9:
            return (short) 0;
         case 10:
            return instructionFactory.initInt();
         case 11:
            return (long) 0l;
      }
      throw new UnsupportedOperationException("" + atype);
   }
}
