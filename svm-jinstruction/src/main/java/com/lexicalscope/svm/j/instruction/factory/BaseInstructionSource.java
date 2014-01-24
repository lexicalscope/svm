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
import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
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
   public Vop invokevirtual(final SMethodDescriptor name, final InstructionSink sink) {
      return MethodCallInstruction.createInvokeVirtual(name);
   }

   @Override
   public Vop invokeinterface(final SMethodDescriptor name, final InstructionSink sink) {
      return MethodCallInstruction.createInvokeInterface(name);
   }

   @Override
   public Vop invokespecial(final SMethodDescriptor name, final InstructionSink sink) {
      return MethodCallInstruction.createInvokeSpecial(name);
   }

   @Override
   public Vop invokestatic(final SMethodDescriptor name, final InstructionSink sink) {
      return MethodCallInstruction.createInvokeStatic(name);
   }

   @Override
   public BranchInstruction got0(final InstructionSink sink) {
      return new BranchInstruction(new Unconditional());
   }

   @Override
   public Vop ifacmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfACmpNe(jumpInsnNode);
   }

   @Override
   public Vop ificmpge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpGe(jumpInsnNode);
   }

   @Override
   public Vop ificmpgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpGt(jumpInsnNode);
   }

   @Override
   public Vop ificmplt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpLt(jumpInsnNode);
   }

   @Override
   public Vop ificmple(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpLe(jumpInsnNode);
   }

   @Override
   public Vop ificmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpNe(jumpInsnNode);
   }

   @Override
   public Vop ificmpeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfICmpEq(jumpInsnNode);
   }

   @Override
   public Vop ifnonnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfNonNull(jumpInsnNode);
   }

   @Override
   public Vop ifnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfNull(jumpInsnNode);
   }

   @Override
   public Vop ifne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfNe(jumpInsnNode);
   }

   @Override
   public Vop ifeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfEq(jumpInsnNode);
   }

   @Override
   public Vop iflt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfLt(jumpInsnNode);
   }

   @Override
   public Vop ifle(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfLe(jumpInsnNode);
   }

   @Override
   public Vop ifgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfGt(jumpInsnNode);
   }

   @Override
   public Vop ifge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return instructionFactory.branchIfGe(jumpInsnNode);
   }

   @Override
   public LinearInstruction checkcast(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return linearInstruction(new CheckCastOp(typeInsnNode.desc), sink);
   }

   @Override
   public LinearInstruction instance0f(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return linearInstruction(new InstanceOfOp(typeInsnNode.desc), sink);
   }

   @Override
   public LinearInstruction anewarray(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aNewArray(), sink);
   }

   @Override
   public LinearInstruction iinc(final IincInsnNode iincInsnNode, final InstructionSink sink) {
      return linearInstruction(new IincOp(iincInsnNode.var, iincInsnNode.incr), sink);
   }

   @Override
   public LinearInstruction newarray(final int val, final InstructionSink sink) {
      return linearInstruction(instructionFactory.newArray(instructionHelper.initialFieldValue(val)), sink);
   }

   @Override
   public Vop bipush(final int val, final InstructionSink sink) {
      return instructionHelper.iconst(val);
   }

   @Override
   public Vop sipush(final int val, final InstructionSink sink) {
      return instructionHelper.iconst(val);
   }

   @Override
   public Vop ldcDouble(final double val, final InstructionSink sink) {
      return instructionHelper.dconst(val);
   }

   @Override
   public Vop ldcFloat(final float val, final InstructionSink sink) {
      return instructionHelper.fconst(val);
   }

   @Override
   public Vop ldcLong(final long val, final InstructionSink sink) {
      return instructionHelper.lconst(val);
   }

   @Override
   public Vop ldcInt(final int val, final InstructionSink sink) {
      return instructionHelper.iconst(val);
   }

   @Override
   public LinearInstruction pop(final InstructionSink sink) {
      return linearInstruction(new PopOp(), sink);
   }

   @Override
   public LinearInstruction lcmp(final InstructionSink sink) {
      return linearInstruction(new LCmpOp(), sink);
   }

   @Override
   public LinearInstruction fcmpl(final InstructionSink sink) {
      return binaryOp(new FCmpLOperator(), sink);
   }

   @Override
   public LinearInstruction fcmpg(final InstructionSink sink) {
      return binaryOp(new FCmpGOperator(), sink);
   }

   @Override
   public LinearInstruction f2i(final InstructionSink sink) {
      return linearInstruction(new F2IOp(), sink);
   }

   @Override
   public LinearInstruction i2f(final InstructionSink sink) {
      return linearInstruction(new I2FOp(), sink);
   }

   @Override
   public LinearInstruction l2i(final InstructionSink sink) {
      return linearInstruction(new L2IOp(), sink);
   }

   @Override
   public LinearInstruction i2l(final InstructionSink sink) {
      return linearInstruction(new I2LOp(), sink);
   }

   @Override
   public LinearInstruction lushr(final InstructionSink sink) {
      return linearInstruction(new LushrOp(), sink);
   }

   @Override
   public LinearInstruction ixor(final InstructionSink sink) {
      return linearInstruction(new IxorOp(), sink);
   }

   @Override
   public LinearInstruction ior(final InstructionSink sink) {
      return linearInstruction(new IorOp(), sink);
   }

   @Override
   public LinearInstruction iushr(final InstructionSink sink) {
      return linearInstruction(new IushrOp(), sink);
   }

   @Override
   public LinearInstruction ishr(final InstructionSink sink) {
      return linearInstruction(new IshrOp(), sink);
   }

   @Override
   public LinearInstruction ishl(final InstructionSink sink) {
      return linearInstruction(new IshlOp(), sink);
   }

   @Override
   public LinearInstruction arrayLength(final InstructionSink sink) {
      return linearInstruction(new ArrayLengthOp(), sink);
   }

   @Override
   public LinearInstruction aaload(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aaLoad(), sink);
   }

   @Override
   public LinearInstruction iaload(final InstructionSink sink) {
      return linearInstruction(instructionFactory.iaLoad(), sink);
   }

   @Override
   public LinearInstruction caload(final InstructionSink sink) {
      return linearInstruction(ArrayLoadOp.caLoad(), sink);
   }

   @Override
   public LinearInstruction aaStore(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aaStore(), sink);
   }

   @Override
   public LinearInstruction iaStore(final InstructionSink sink) {
      return linearInstruction(instructionFactory.iaStore(), sink);
   }

   @Override
   public LinearInstruction caStore(final InstructionSink sink) {
      return linearInstruction(ArrayStoreOp.caStore(), sink);
   }

   @Override
   public Vop lconst_1(final InstructionSink sink) {
      return instructionHelper.lconst(1);
   }

   @Override
   public Vop lconst_0(final InstructionSink sink) {
      return instructionHelper.lconst(0);
   }

   @Override
   public Vop iconst_5(final InstructionSink sink) {
      return instructionHelper.iconst(5);
   }

   @Override
   public Vop iconst_4(final InstructionSink sink) {
      return instructionHelper.iconst(4);
   }

   @Override
   public Vop iconst_3(final InstructionSink sink) {
      return instructionHelper.iconst(3);
   }

   @Override
   public Vop iconst_2(final InstructionSink sink) {
      return instructionHelper.iconst(2);
   }

   @Override
   public Vop iconst_1(final InstructionSink sink) {
      return instructionHelper.iconst(1);
   }

   @Override
   public Vop iconst_m1(final InstructionSink sink) {
      return instructionHelper.iconst(-1);
   }

   @Override
   public LinearInstruction dup_x1(final InstructionSink sink) {
      return linearInstruction(new Dup_X1Op(), sink);
   }

   @Override
   public LinearInstruction dup(final InstructionSink sink) {
      return linearInstruction(new DupOp(), sink);
   }

   @Override
   public LinearInstruction ineg(final InstructionSink sink) {
      return instructionHelper.unaryOp(instructionFactory.inegOperation());
   }

   @Override
   public LinearInstruction isub(final InstructionSink sink) {
      return binaryOp(instructionFactory.isubOperation(), sink);
   }

   @Override
   public LinearInstruction fdiv(final InstructionSink sink) {
      return binaryOp(instructionFactory.fdivOperation(), sink);
   }

   @Override
   public LinearInstruction fmul(final InstructionSink sink) {
      return binaryOp(instructionFactory.fmulOperation(), sink);
   }

   @Override
   public LinearInstruction imul(final InstructionSink sink) {
      return binaryOp(instructionFactory.imulOperation(), sink);
   }

   @Override
   public LinearInstruction iadd(final InstructionSink sink) {
      return binaryOp(instructionFactory.iaddOperation(), sink);
   }

   @Override
   public LinearInstruction land(final InstructionSink sink) {
      return instructionHelper.binary2Op(instructionFactory.landOperation());
   }

   @Override
   public LinearInstruction iand(final InstructionSink sink) {
      return binaryOp(instructionFactory.iandOperation(), sink);
   }

   @Override
   public LoadingInstruction putStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return loadingInstruction(fieldInsnNode.owner, new PutStaticOp(fieldInsnNode), sink);
   }

   @Override
   public LoadingInstruction getStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return loadingInstruction(fieldInsnNode.owner, new GetStaticOp(fieldInsnNode), sink);
   }

   @Override
   public LinearInstruction getField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return linearInstruction(instructionFactory.getField(fieldInsnNode), sink);
   }

   @Override
   public LinearInstruction putField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return linearInstruction(instructionFactory.putField(fieldInsnNode), sink);
   }

   @Override
   public LinearInstruction store(final int var, final InstructionSink sink) {
      return linearInstruction(new Store(var), sink);
   }

   @Override
   public LinearInstruction store2(final int var, final InstructionSink sink) {
      return linearInstruction(new Store2(var), sink);
   }

   @Override public Vop aconst_null(final InstructionSink sink) {
      return linearInstruction(new AConstNullOp(), sink);
   }

   @Override public Vop iconst_0(final InstructionSink sink) {
      return instructionHelper.iconst(0);
   }

   @Override public Vop fconst_0(final InstructionSink sink) {
      return instructionHelper.fconst(0);
   }

   @Override public Vop stringPoolLoad(final String constVal, final InstructionSink sink) {
      return linearInstruction(new StringPoolLoadOperator(constVal), sink);
   }

   @Override public Vop objectPoolLoad(final Type constVal, final InstructionSink sink) {
      return linearInstruction(new ObjectPoolLoad(constVal), sink);
   }

   @Override public LinearInstruction load(final int index, final InstructionSink sink) {
      return linearInstruction(new Load(index), sink);
   }

   @Override public LinearInstruction load2(final int index, final InstructionSink sink) {
      return linearInstruction(new Load2(index), sink);
   }

   @Override public Vop returnVoid(final InstructionSink sink) {
      return new ReturnInstruction(0);
   }

   @Override public Vop return1(final InstructionSink sink) {
      return new ReturnInstruction(1);
   }

   @Override public Vop return2(final InstructionSink sink) {
      return new ReturnInstruction(2);
   }

   @Override public Vop newObject(final String klassDesc, final InstructionSink sink) {
      return loadingInstruction(klassDesc, instructionHelper.newOp(klassDesc), sink);
   }

   private LinearInstruction binaryOp(final BinaryOperator operation, final InstructionSink sink) {
      return instructionHelper.binaryOp(operation);
   }

   private LinearInstruction linearInstruction(final Vop op, final InstructionSink sink) {
      return instructionHelper.linearInstruction(op);
   }

   private LoadingInstruction loadingInstruction(final String klassDesc, final Vop op, final InstructionSink sink) {
      return instructionHelper.loadingInstruction(klassDesc, op);
   }
}
