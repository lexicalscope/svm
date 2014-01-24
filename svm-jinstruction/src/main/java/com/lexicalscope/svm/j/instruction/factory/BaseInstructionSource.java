package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
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
import com.lexicalscope.svm.j.instruction.concrete.klass.GetStaticOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.InstanceOfOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.PutStaticOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.L2IOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LCmpOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LushrOp;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.concrete.object.AConstNullOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
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
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class BaseInstructionSource implements InstructionSource {
   private final InstructionFactory instructionFactory;
   private final InstructionHelper instructionHelper;

   public BaseInstructionSource(
         final InstructionFactory instructionFactory,
         final InstructionHelper instructionHelper) {
      this.instructionFactory = instructionFactory;
      this.instructionHelper = instructionHelper;
   }

   @Override
   public Vop invokevirtual(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeVirtual(name);
   }

   @Override
   public Vop invokeinterface(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeInterface(name);
   }

   @Override
   public Vop invokespecial(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeSpecial(name);
   }

   @Override
   public Vop invokestatic(final SMethodDescriptor name) {
      return MethodCallInstruction.createInvokeStatic(name);
   }

   @Override
   public BranchInstruction got0() {
      return new BranchInstruction(new Unconditional());
   }

   @Override
   public Vop ifacmpne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfACmpNe(jumpInsnNode);
   }

   @Override
   public Vop ificmpge(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpGe(jumpInsnNode);
   }

   @Override
   public Vop ificmpgt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpGt(jumpInsnNode);
   }

   @Override
   public Vop ificmplt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpLt(jumpInsnNode);
   }

   @Override
   public Vop ificmple(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpLe(jumpInsnNode);
   }

   @Override
   public Vop ificmpne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpNe(jumpInsnNode);
   }

   @Override
   public Vop ificmpeq(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfICmpEq(jumpInsnNode);
   }

   @Override
   public Vop ifnonnull(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNonNull(jumpInsnNode);
   }

   @Override
   public Vop ifnull(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNull(jumpInsnNode);
   }

   @Override
   public Vop ifne(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfNe(jumpInsnNode);
   }

   @Override
   public Vop ifeq(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfEq(jumpInsnNode);
   }

   @Override
   public Vop iflt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfLt(jumpInsnNode);
   }

   @Override
   public Vop ifle(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfLe(jumpInsnNode);
   }

   @Override
   public Vop ifgt(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfGt(jumpInsnNode);
   }

   @Override
   public Vop ifge(final JumpInsnNode jumpInsnNode) {
      return instructionFactory.branchIfGe(jumpInsnNode);
   }

   @Override
   public LinearInstruction checkcast(final TypeInsnNode typeInsnNode) {
      return linearInstruction(new CheckCastOp(typeInsnNode.desc));
   }

   @Override
   public LinearInstruction instance0f(final TypeInsnNode typeInsnNode) {
      return linearInstruction(new InstanceOfOp(typeInsnNode.desc));
   }

   @Override
   public LinearInstruction anewarray() {
      return linearInstruction(instructionFactory.aNewArray());
   }

   @Override
   public LinearInstruction iinc(final IincInsnNode iincInsnNode) {
      return linearInstruction(new IincOp(iincInsnNode.var, iincInsnNode.incr));
   }

   @Override
   public LinearInstruction newarray(final int val) {
      return linearInstruction(instructionFactory.newArray(instructionHelper.initialFieldValue(val)));
   }

   @Override
   public Vop bipush(final int val) {
      return instructionHelper.iconst(val);
   }

   @Override
   public Vop sipush(final int val) {
      return instructionHelper.iconst(val);
   }

   @Override
   public Vop ldcDouble(final double val) {
      return instructionHelper.dconst(val);
   }

   @Override
   public Vop ldcFloat(final float val) {
      return instructionHelper.fconst(val);
   }

   @Override
   public Vop ldcLong(final long val) {
      return instructionHelper.lconst(val);
   }

   @Override
   public Vop ldcInt(final int val) {
      return instructionHelper.iconst(val);
   }

   @Override
   public LinearInstruction pop() {
      return linearInstruction(new PopOp());
   }

   @Override
   public LinearInstruction lcmp() {
      return linearInstruction(new LCmpOp());
   }

   @Override
   public LinearInstruction fcmpl() {
      return binaryOp(new FCmpLOperator());
   }

   @Override
   public LinearInstruction fcmpg() {
      return binaryOp(new FCmpGOperator());
   }

   @Override
   public LinearInstruction f2i() {
      return linearInstruction(new F2IOp());
   }

   @Override
   public LinearInstruction i2f() {
      return linearInstruction(new I2FOp());
   }

   @Override
   public LinearInstruction l2i() {
      return linearInstruction(new L2IOp());
   }

   @Override
   public LinearInstruction i2l() {
      return linearInstruction(new I2LOp());
   }

   @Override
   public LinearInstruction lushr() {
      return linearInstruction(new LushrOp());
   }

   @Override
   public LinearInstruction ixor() {
      return linearInstruction(new IxorOp());
   }

   @Override
   public LinearInstruction ior() {
      return linearInstruction(new IorOp());
   }

   @Override
   public LinearInstruction iushr() {
      return linearInstruction(new IushrOp());
   }

   @Override
   public LinearInstruction ishr() {
      return linearInstruction(new IshrOp());
   }

   @Override
   public LinearInstruction ishl() {
      return linearInstruction(new IshlOp());
   }

   @Override
   public LinearInstruction arrayLength() {
      return linearInstruction(new ArrayLengthOp());
   }

   @Override
   public LinearInstruction aaload() {
      return linearInstruction(instructionFactory.aaLoad());
   }

   @Override
   public LinearInstruction iaload() {
      return linearInstruction(instructionFactory.iaLoad());
   }

   @Override
   public LinearInstruction caload() {
      return linearInstruction(ArrayLoadOp.caLoad());
   }

   @Override
   public LinearInstruction aaStore() {
      return linearInstruction(instructionFactory.aaStore());
   }

   @Override
   public LinearInstruction iaStore() {
      return linearInstruction(instructionFactory.iaStore());
   }

   @Override
   public LinearInstruction caStore() {
      return linearInstruction(ArrayStoreOp.caStore());
   }

   @Override
   public Vop lconst_1() {
      return instructionHelper.lconst(1);
   }

   @Override
   public Vop lconst_0() {
      return instructionHelper.lconst(0);
   }

   @Override
   public Vop iconst_5() {
      return instructionHelper.iconst(5);
   }

   @Override
   public Vop iconst_4() {
      return instructionHelper.iconst(4);
   }

   @Override
   public Vop iconst_3() {
      return instructionHelper.iconst(3);
   }

   @Override
   public Vop iconst_2() {
      return instructionHelper.iconst(2);
   }

   @Override
   public Vop iconst_1() {
      return instructionHelper.iconst(1);
   }

   @Override
   public Vop iconst_m1() {
      return instructionHelper.iconst(-1);
   }

   @Override
   public LinearInstruction dup_x1() {
      return linearInstruction(new Dup_X1Op());
   }

   @Override
   public LinearInstruction dup() {
      return linearInstruction(new DupOp());
   }

   @Override
   public LinearInstruction ineg() {
      return instructionHelper.unaryOp(instructionFactory.inegOperation());
   }

   @Override
   public LinearInstruction isub() {
      return binaryOp(instructionFactory.isubOperation());
   }

   @Override
   public LinearInstruction fdiv() {
      return binaryOp(instructionFactory.fdivOperation());
   }

   @Override
   public LinearInstruction fmul() {
      return binaryOp(instructionFactory.fmulOperation());
   }

   @Override
   public LinearInstruction imul() {
      return binaryOp(instructionFactory.imulOperation());
   }

   @Override
   public LinearInstruction iadd() {
      return binaryOp(instructionFactory.iaddOperation());
   }

   @Override
   public LinearInstruction land() {
      return instructionHelper.binary2Op(instructionFactory.landOperation());
   }

   @Override
   public LinearInstruction iand() {
      return binaryOp(instructionFactory.iandOperation());
   }

   @Override
   public LoadingInstruction putStaticField(final FieldInsnNode fieldInsnNode) {
      return loadingInstruction(fieldInsnNode.owner, new PutStaticOp(fieldInsnNode));
   }

   @Override
   public LoadingInstruction getStaticField(final FieldInsnNode fieldInsnNode) {
      return loadingInstruction(fieldInsnNode.owner, new GetStaticOp(fieldInsnNode));
   }

   @Override
   public LinearInstruction getField(final FieldInsnNode fieldInsnNode) {
      return linearInstruction(instructionFactory.getField(fieldInsnNode));
   }

   @Override
   public LinearInstruction putField(final FieldInsnNode fieldInsnNode) {
      return linearInstruction(instructionFactory.putField(fieldInsnNode));
   }

   @Override
   public LinearInstruction store(final int var) {
      return linearInstruction(new Store(var));
   }

   @Override
   public LinearInstruction store2(final int var) {
      return linearInstruction(new Store2(var));
   }

   @Override public Vop aconst_null() {
      return linearInstruction(new AConstNullOp());
   }

   @Override public Vop iconst_0() {
      return instructionHelper.iconst(0);
   }

   @Override public Vop fconst_0() {
      return instructionHelper.fconst(0);
   }

   @Override public Vop stringPoolLoad(final String constVal) {
      return linearInstruction(new StringPoolLoadOperator(constVal));
   }

   @Override public Vop objectPoolLoad(final Type constVal) {
      return linearInstruction(new ObjectPoolLoad(constVal));
   }

   @Override public LinearInstruction load(final int index) {
      return linearInstruction(new Load(index));
   }

   @Override public LinearInstruction load2(final int index) {
      return linearInstruction(new Load2(index));
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
      return loadingInstruction(klassDesc, instructionHelper.newOp(klassDesc));
   }

   private LinearInstruction binaryOp(final BinaryOperator operation) {
      return instructionHelper.binaryOp(operation);
   }

   private LinearInstruction linearInstruction(final Vop op) {
      return instructionHelper.linearInstruction(op);
   }

   private LoadingInstruction loadingInstruction(final String klassDesc, final Vop op) {
      return instructionHelper.loadingInstruction(klassDesc, op);
   }
}
