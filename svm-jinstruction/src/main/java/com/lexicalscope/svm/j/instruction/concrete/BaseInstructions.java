package com.lexicalscope.svm.j.instruction.concrete;

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

import com.lexicalscope.symb.code.AsmSMethodName;
import com.lexicalscope.symb.vm.SMethodDescriptor;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.predicates.Unconditional;

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

   private Vop instructionFor(final AbstractInsnNode abstractInsnNode) {
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
                  return loadingInstruction(fieldInsnNode, new GetStaticOp(fieldInsnNode));
               case Opcodes.PUTSTATIC:
                  return loadingInstruction(fieldInsnNode, new PutStaticOp(fieldInsnNode));
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
                  return linearInstruction(new DupOp());
               case Opcodes.DUP_X1:
                  return linearInstruction(new Dup_X1Op());
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
                  return linearInstruction(ArrayStoreOp.caStore());
               case Opcodes.IASTORE:
                  return linearInstruction(instructionFactory.iaStore());
               case Opcodes.AASTORE:
                  return linearInstruction(instructionFactory.aaStore());
               case Opcodes.CALOAD:
                  return linearInstruction(ArrayLoadOp.caLoad());
               case Opcodes.IALOAD:
                  return linearInstruction(instructionFactory.iaLoad());
               case Opcodes.AALOAD:
                  return linearInstruction(instructionFactory.aaLoad());
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
            final SMethodDescriptor name = new AsmSMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESTATIC:
                  return MethodCallInstruction.createInvokeStatic(name);
               case Opcodes.INVOKESPECIAL:
                  return MethodCallInstruction.createInvokeSpecial(name);
               case Opcodes.INVOKEINTERFACE:
                  return MethodCallInstruction.createInvokeInterface(name);
               case Opcodes.INVOKEVIRTUAL:
                  return MethodCallInstruction.createInvokeVirtual(name);
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

   @Override public Vop iconst(final int constVal) {
      return nullary(instructionFactory.iconst(constVal));
   }

   @Override public Vop lconst(final long constVal) {
      return nullary2(instructionFactory.lconst(constVal));
   }

   private Vop fconst(final float constVal) {
      return nullary(instructionFactory.fconst(constVal));
   }

   private Vop dconst(final double constVal) {
      return nullary2(instructionFactory.dconst(constVal));
   }

   @Override public Vop aconst_null() {
      return linearInstruction(new AConstNullOp());
   }

   @Override public Vop iconst_0() {
      return iconst(0);
   }

   @Override public Vop fconst_0() {
      return fconst(0);
   }

   private Vop stringPoolLoad(final String constVal) {
      return linearInstruction(new StringPoolLoadOperator(constVal));
   }

   private Vop objectPoolLoad(final Type constVal) {
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

   @Override public Vop aload(final int index) {
      return load(index);
   }

   @Override public Vop fload(final int index) {
      return load(index);
   }

   @Override public Vop dload(final int index) {
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

   @Override public Vop defineClass(final List<String> klassNames) {
      return new LoadingInstruction(klassNames, new NoOp());
   }

   @Override public Vop initThread() {
      return new LinearInstruction(new InitThreadOp());
   }

   @Override public StatementBuilder statements() {
      return new StatementBuilder(this);
   }

   @Override public Vop returnVoid() {
      return new ReturnInstruction(0);
   }

   @Override public Vop return1() {
      return new ReturnInstruction(1);
   }

   @Override public Vop return2() {
      return new ReturnInstruction(2);
   }

   @Override public Vop newObject(final String klassDesc) {
      return loadingInstruction(klassDesc, newOp(klassDesc));
   }

   @Override public Vop addressToHashCode() {
      return linearInstruction(new AddressToHashCodeOp());
   }

   @Override public Vop nanoTime() {
      return linearInstruction(new NanoTimeOp());
   }

   @Override public Vop currentTimeMillis() {
      return linearInstruction(new CurrentTimeMillisOp());
   }

   @Override public Vop createInvokeSpecial(final SMethodDescriptor sMethodName) {
      return MethodCallInstruction.createInvokeSpecial(sMethodName);
   }

   @Override public Vop invokeInterface(final String klassName, final String methodName, final String desc) {
      return invokeInterface(new AsmSMethodName(klassName, methodName, desc));
   }

   private Vop invokeInterface(final SMethodDescriptor sMethodName) {
      return MethodCallInstruction.createInvokeInterface(sMethodName);
   }

   @Override public Vop currentThread() {
      return linearInstruction(new CurrentThreadOp());
   }

   @Override public Vop arrayCopy() {
      return linearInstruction(new ArrayCopyOp());
   }

   @Override public Vop floatToRawIntBits() {
      return linearInstruction(new FloatToRawIntBits());
   }

   @Override public Vop doubleToRawLongBits() {
      return linearInstruction(new DoubleToRawLongBits());
   }

   @Override public Vop getCallerClass() {
      return linearInstruction(new GetCallerClass());
   }

   @Override public Vop getPrimitiveClass() {
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

   @Override
   public Vop nop() {
      return new LinearInstruction(new NoOp());
   }

   @Override public Vop loadArg(final Object object) {
      return instructionFactory.loadArg(object);
   }

   private Vop newOp(final String klassDesc) {
      return new VopAdapter(new NewObjectOp(klassDesc));
   }
}
