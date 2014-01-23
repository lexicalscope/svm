package com.lexicalscope.svm.j.instruction.factory;

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

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.UnsupportedInstruction;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayCopyOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayLengthOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayLoadOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayStoreOp;
import com.lexicalscope.svm.j.instruction.concrete.branch.BranchInstruction;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Unconditional;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.F2IOp;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FCmpGOperator;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FCmpLOperator;
import com.lexicalscope.svm.j.instruction.concrete.integer.I2FOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.I2LOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IincOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IorOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IshlOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IshrOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IushrOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IxorOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.CheckCastOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.GetPrimitiveClass;
import com.lexicalscope.svm.j.instruction.concrete.klass.GetStaticOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.InstanceOfOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.PutStaticOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.L2IOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LCmpOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LushrOp;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentThreadOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentTimeMillisOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.DoubleToRawLongBits;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.FloatToRawIntBits;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.GetCallerClass;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.NanoTimeOp;
import com.lexicalscope.svm.j.instruction.concrete.object.AConstNullOp;
import com.lexicalscope.svm.j.instruction.concrete.object.AddressToHashCodeOp;
import com.lexicalscope.svm.j.instruction.concrete.object.NewObjectOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.pool.ObjectPoolLoad;
import com.lexicalscope.svm.j.instruction.concrete.pool.StringPoolLoadOperator;
import com.lexicalscope.svm.j.instruction.concrete.stack.DupOp;
import com.lexicalscope.svm.j.instruction.concrete.stack.Dup_X1Op;
import com.lexicalscope.svm.j.instruction.concrete.stack.Load;
import com.lexicalscope.svm.j.instruction.concrete.stack.Load2;
import com.lexicalscope.svm.j.instruction.concrete.stack.PopOp;
import com.lexicalscope.svm.j.instruction.concrete.stack.ReturnInstruction;
import com.lexicalscope.svm.j.instruction.concrete.stack.Store;
import com.lexicalscope.svm.j.instruction.concrete.stack.Store2;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.VopAdapter;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

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
                  return putField(fieldInsnNode);
               case Opcodes.GETFIELD:
                  return getField(fieldInsnNode);
               case Opcodes.GETSTATIC:
                  return getStaticField(fieldInsnNode);
               case Opcodes.PUTSTATIC:
                  return putStaticField(fieldInsnNode);
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
                  return iand();
               case Opcodes.LAND:
                  return land();
               case Opcodes.IADD:
                  return iadd();
               case Opcodes.IMUL:
                  return imul();
               case Opcodes.FMUL:
                  return fmul();
               case Opcodes.FDIV:
                  return fdiv();
               case Opcodes.ISUB:
                  return isub();
               case Opcodes.INEG:
                  return ineg();
               case Opcodes.DUP:
                  return dup();
               case Opcodes.DUP_X1:
                  return dup_x1();
               case Opcodes.ICONST_M1:
                  return iconst_m1();
               case Opcodes.ICONST_0:
                  return iconst_0();
               case Opcodes.ICONST_1:
                  return iconst_1();
               case Opcodes.ICONST_2:
                  return iconst_2();
               case Opcodes.ICONST_3:
                  return iconst_3();
               case Opcodes.ICONST_4:
                  return iconst_4();
               case Opcodes.ICONST_5:
                  return iconst_5();
               case Opcodes.LCONST_0:
                  return lconst_0();
               case Opcodes.LCONST_1:
                  return lconst_1();
               case Opcodes.FCONST_0:
                  return fconst_0();
               case Opcodes.CASTORE:
                  return caStore();
               case Opcodes.IASTORE:
                  return iaStore();
               case Opcodes.AASTORE:
                  return aaStore();
               case Opcodes.CALOAD:
                  return caload();
               case Opcodes.IALOAD:
                  return iaload();
               case Opcodes.AALOAD:
                  return aaload();
               case Opcodes.ARRAYLENGTH:
                  return arrayLength();
               case Opcodes.ISHL:
                  return ishl();
               case Opcodes.ISHR:
                   return ishr();
               case Opcodes.IUSHR:
                  return iushr();
               case Opcodes.IOR:
                  return ior();
               case Opcodes.IXOR:
                  return ixor();
               case Opcodes.LUSHR:
                  return lushr();
               case Opcodes.I2L:
                  return i2l();
               case Opcodes.L2I:
                  return l2i();
               case Opcodes.I2F:
                  return i2f();
               case Opcodes.F2I:
                  return f2i();
               case Opcodes.FCMPG:
                  return fcmpg();
               case Opcodes.FCMPL:
                  return fcmpl();
               case Opcodes.LCMP:
                  return lcmp();
               case Opcodes.POP:
                  return pop();
            }
            break;
         case AbstractInsnNode.LDC_INSN:
            final LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.LDC:
                  if(ldcInsnNode.cst instanceof Integer) {
                     return ldcInt((int) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Long) {
                     return ldcLong((long) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Float) {
                     return ldcFloat((float) ldcInsnNode.cst);
                  } else if(ldcInsnNode.cst instanceof Double) {
                     return ldcDouble((double) ldcInsnNode.cst);
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
            final int val = intInsnNode.operand;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.SIPUSH:
                  return sipush(val);
               case Opcodes.BIPUSH:
                  return bipush(val);
               case Opcodes.NEWARRAY:
                  return newarray(val);
            }
            break;
         case AbstractInsnNode.IINC_INSN:
            final IincInsnNode iincInsnNode = (IincInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.IINC:
                  return iinc(iincInsnNode);
            }
            break;
         case AbstractInsnNode.TYPE_INSN:
            final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.NEW:
                  return newObject(typeInsnNode.desc);
               case Opcodes.ANEWARRAY:
                  return anewarray();
               case Opcodes.INSTANCEOF:
                  return instance0f(typeInsnNode);
               case Opcodes.CHECKCAST:
                  return checkcast(typeInsnNode);
            }
            break;
         case AbstractInsnNode.JUMP_INSN:
            final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
            switch (jumpInsnNode.getOpcode()) {
               case Opcodes.IFGE:
                  return ifge(jumpInsnNode);
               case Opcodes.IFGT:
                  return ifgt(jumpInsnNode);
               case Opcodes.IFLE:
                  return ifle(jumpInsnNode);
               case Opcodes.IFLT:
                  return iflt(jumpInsnNode);
               case Opcodes.IFEQ:
                  return ifeq(jumpInsnNode);
               case Opcodes.IFNE:
                  return ifne(jumpInsnNode);
               case Opcodes.IFNULL:
                  return ifnull(jumpInsnNode);
               case Opcodes.IFNONNULL:
                  return ifnonnull(jumpInsnNode);
               case Opcodes.IF_ICMPEQ:
                  return ificmpeq(jumpInsnNode);
               case Opcodes.IF_ICMPNE:
                  return ificmpne(jumpInsnNode);
               case Opcodes.IF_ICMPLE:
                  return ificmple(jumpInsnNode);
               case Opcodes.IF_ICMPLT:
                  return ificmplt(jumpInsnNode);
               case Opcodes.IF_ICMPGT:
                  return ificmpgt(jumpInsnNode);
               case Opcodes.IF_ICMPGE:
                  return ificmpge(jumpInsnNode);
               case Opcodes.IF_ACMPNE:
                  return ifacmpne(jumpInsnNode);
               case Opcodes.GOTO:
                  return got0();
            }
            break;
         case AbstractInsnNode.METHOD_INSN:
            final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            final SMethodDescriptor name = new AsmSMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
            switch (abstractInsnNode.getOpcode()) {
               case Opcodes.INVOKESTATIC:
                  return invokestatic(name);
               case Opcodes.INVOKESPECIAL:
                  return invokespecial(name);
               case Opcodes.INVOKEINTERFACE:
                  return invokeinterface(name);
               case Opcodes.INVOKEVIRTUAL:
                  return invokevirtual(name);
            }
            break;
      }

      return new UnsupportedInstruction(abstractInsnNode);
   }

   public Vop invokevirtual(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeVirtual(name);
   }

   public Vop invokeinterface(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeInterface(name);
   }

   public Vop invokespecial(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeSpecial(name);
   }

   public Vop invokestatic(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeStatic(name);
   }

   public BranchInstruction got0() {
      return new BranchInstruction(new Unconditional());
   }

   public Vop ifacmpne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfACmpNe(jumpInsnNode);
   }

   public Vop ificmpge(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpGe(jumpInsnNode);
   }

   public Vop ificmpgt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpGt(jumpInsnNode);
   }

   public Vop ificmplt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpLt(jumpInsnNode);
   }

   public Vop ificmple(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpLe(jumpInsnNode);
   }

   public Vop ificmpne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpNe(jumpInsnNode);
   }

   public Vop ificmpeq(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpEq(jumpInsnNode);
   }

   public Vop ifnonnull(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNonNull(jumpInsnNode);
   }

   public Vop ifnull(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNull(jumpInsnNode);
   }

   public Vop ifne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNe(jumpInsnNode);
   }

   public Vop ifeq(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfEq(jumpInsnNode);
   }

   public Vop iflt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfLt(jumpInsnNode);
   }

   public Vop ifle(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfLe(jumpInsnNode);
   }

   public Vop ifgt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfGt(jumpInsnNode);
   }

   public Vop ifge(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfGe(jumpInsnNode);
   }

   public LinearInstruction checkcast(final TypeInsnNode typeInsnNode) {
      return linearInstruction(new CheckCastOp(typeInsnNode.desc));
   }

   public LinearInstruction instance0f(final TypeInsnNode typeInsnNode) {
      return linearInstruction(new InstanceOfOp(typeInsnNode.desc));
   }

   public LinearInstruction anewarray() {
      return linearInstruction(instructionFactory.aNewArray());
   }

   public LinearInstruction iinc(final IincInsnNode iincInsnNode) {
      return linearInstruction(new IincOp(iincInsnNode.var, iincInsnNode.incr));
   }

   public LinearInstruction newarray(final int val) {
      return linearInstruction(instructionFactory.newArray(initialFieldValue(val)));
   }

   public Vop bipush(final int val) {
      return iconst(val);
   }

   public Vop sipush(final int val) {
      return iconst(val);
   }

   public Vop ldcDouble(final double val) {
      return dconst(val);
   }

   public Vop ldcFloat(final float val) {
      return fconst(val);
   }

   public Vop ldcLong(final long val) {
      return lconst(val);
   }

   public Vop ldcInt(final int val) {
      return iconst(val);
   }

   public LinearInstruction pop() {
      return linearInstruction(new PopOp());
   }

   public LinearInstruction lcmp() {
      return linearInstruction(new LCmpOp());
   }

   public LinearInstruction fcmpl() {
      return binaryOp(new FCmpLOperator());
   }

   public LinearInstruction fcmpg() {
      return binaryOp(new FCmpGOperator());
   }

   public LinearInstruction f2i() {
      return linearInstruction(new F2IOp());
   }

   public LinearInstruction i2f() {
      return linearInstruction(new I2FOp());
   }

   public LinearInstruction l2i() {
      return linearInstruction(new L2IOp());
   }

   public LinearInstruction i2l() {
      return linearInstruction(new I2LOp());
   }

   public LinearInstruction lushr() {
      return linearInstruction(new LushrOp());
   }

   public LinearInstruction ixor() {
      return linearInstruction(new IxorOp());
   }

   public LinearInstruction ior() {
      return linearInstruction(new IorOp());
   }

   public LinearInstruction iushr() {
      return linearInstruction(new IushrOp());
   }

   public LinearInstruction ishr() {
      return linearInstruction(new IshrOp());
   }

   public LinearInstruction ishl() {
      return linearInstruction(new IshlOp());
   }

   public LinearInstruction arrayLength() {
      return linearInstruction(new ArrayLengthOp());
   }

   public LinearInstruction aaload() {
      return linearInstruction(instructionFactory.aaLoad());
   }

   public LinearInstruction iaload() {
      return linearInstruction(instructionFactory.iaLoad());
   }

   public LinearInstruction caload() {
      return linearInstruction(ArrayLoadOp.caLoad());
   }

   public LinearInstruction aaStore() {
      return linearInstruction(instructionFactory.aaStore());
   }

   public LinearInstruction iaStore() {
      return linearInstruction(instructionFactory.iaStore());
   }

   public LinearInstruction caStore() {
      return linearInstruction(ArrayStoreOp.caStore());
   }

   public Vop lconst_1() {
      return lconst(1);
   }

   public Vop lconst_0() {
      return lconst(0);
   }

   public Vop iconst_5() {
      return iconst(5);
   }

   public Vop iconst_4() {
      return iconst(4);
   }

   public Vop iconst_3() {
      return iconst(3);
   }

   public Vop iconst_2() {
      return iconst(2);
   }

   public Vop iconst_1() {
      return iconst(1);
   }

   public Vop iconst_m1() {
      return iconst(-1);
   }

   public LinearInstruction dup_x1() {
      return linearInstruction(new Dup_X1Op());
   }

   public LinearInstruction dup() {
      return linearInstruction(new DupOp());
   }

   public LinearInstruction ineg() {
      return unaryOp(instructionFactory.inegOperation());
   }

   public LinearInstruction isub() {
      return binaryOp(instructionFactory.isubOperation());
   }

   public LinearInstruction fdiv() {
      return binaryOp(instructionFactory.fdivOperation());
   }

   public LinearInstruction fmul() {
      return binaryOp(instructionFactory.fmulOperation());
   }

   public LinearInstruction imul() {
      return binaryOp(instructionFactory.imulOperation());
   }

   public LinearInstruction iadd() {
      return binaryOp(instructionFactory.iaddOperation());
   }

   public LinearInstruction land() {
      return binary2Op(instructionFactory.landOperation());
   }

   public LinearInstruction iand() {
      return binaryOp(instructionFactory.iandOperation());
   }

   public LoadingInstruction putStaticField(final FieldInsnNode fieldInsnNode) {
      return loadingInstruction(fieldInsnNode, new PutStaticOp(fieldInsnNode));
   }

   public LoadingInstruction getStaticField(final FieldInsnNode fieldInsnNode) {
      return loadingInstruction(fieldInsnNode, new GetStaticOp(fieldInsnNode));
   }

   public LinearInstruction getField(final FieldInsnNode fieldInsnNode) {
      return linearInstruction(instructionFactory.getField(fieldInsnNode));
   }

   public LinearInstruction putField(final FieldInsnNode fieldInsnNode) {
      return linearInstruction(instructionFactory.putField(fieldInsnNode));
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
      return invokespecial(sMethodName);
   }

   @Override public Vop invokeInterface(final String klassName, final String methodName, final String desc) {
      return invokeInterface(new AsmSMethodName(klassName, methodName, desc));
   }

   private Vop invokeInterface(final SMethodDescriptor sMethodName) {
      return invokeinterface(sMethodName);
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
