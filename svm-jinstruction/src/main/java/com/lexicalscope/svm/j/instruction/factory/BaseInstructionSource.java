package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

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
import com.lexicalscope.svm.j.instruction.concrete.klass.PutStaticOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.L2IOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LCmpOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LushrOp;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.concrete.object.AConstNullOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOp;
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
   private final Instructions instructions;

   public BaseInstructionSource(
         final Instructions instructions,
         final InstructionFactory instructionFactory,
         final InstructionHelper instructionHelper) {
      this.instructions = instructions;
      this.instructionFactory = instructionFactory;
      this.instructionHelper = instructionHelper;
   }

   @Override
   public InstructionSource invokevirtual(final SMethodDescriptor name, final InstructionSink sink) {
      MethodCallInstruction.invokevirtual(name, sink);
      return this;
   }

   @Override
   public InstructionSource invokeinterface(final SMethodDescriptor name, final InstructionSink sink) {
      MethodCallInstruction.invokeinterface(name, sink);
      return this;
   }

   @Override
   public InstructionSource invokespecial(final SMethodDescriptor name, final InstructionSink sink) {
      MethodCallInstruction.createInvokeSpecial(name, sink);
      return this;
   }

   @Override
   public InstructionSource invokestatic(final SMethodDescriptor name, final InstructionSink sink) {
      MethodCallInstruction.createInvokeStatic(name, sink, instructions);
      return this;
   }

   @Override
   public InstructionSource got0(final InstructionSink sink) {
      return sink(new BranchInstruction(new Unconditional()), sink);
   }

   @Override
   public InstructionSource ifacmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfACmpNe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmpge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpGe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmpgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpGt(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmplt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpLt(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmple(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpLe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpNe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ificmpeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfICmpEq(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifnonnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfNonNull(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfNull(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfNe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfEq(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource iflt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfLt(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifle(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfLe(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfGt(jumpInsnNode), sink);
   }

   @Override
   public InstructionSource ifge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return sink(instructionFactory.branchIfGe(jumpInsnNode), sink);
   }

   private InstructionSource sink(final Vop op, final InstructionSink sink) {
      sink.nextInstruction(op);
      return this;
   }

   @Override
   public InstructionSource checkcast(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return linearInstruction(new CheckCastOp(typeInsnNode.desc), sink);
   }

   @Override
   public InstructionSource instance0f(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return linearInstruction(new InstanceOfOp(typeInsnNode.desc), sink);
   }

   @Override
   public InstructionSource anewarray(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aNewArray(), sink);
   }

   @Override
   public InstructionSource iinc(final IincInsnNode iincInsnNode, final InstructionSink sink) {
      return linearInstruction(new IincOp(iincInsnNode.var, iincInsnNode.incr), sink);
   }

   @Override
   public InstructionSource newarray(final int val, final InstructionSink sink) {
      return linearInstruction(instructionFactory.newArray(instructionHelper.initialFieldValue(val)), sink);
   }

   @Override
   public InstructionSource bipush(final int val, final InstructionSink sink) {
      return iconst(val, sink);
   }

   @Override
   public InstructionSource sipush(final int val, final InstructionSink sink) {
      return iconst(val, sink);
   }

   @Override
   public InstructionSource ldcDouble(final double val, final InstructionSink sink) {
      return linearInstruction(instructionHelper.dconst(val), sink);
   }

   @Override
   public InstructionSource ldcFloat(final float val, final InstructionSink sink) {
      return linearInstruction(fconst(val), sink);
   }

   @Override
   public InstructionSource ldcLong(final long val, final InstructionSink sink) {
      return linearInstruction(instructionHelper.lconst(val), sink);
   }

   @Override
   public InstructionSource ldcInt(final int val, final InstructionSink sink) {
      return iconst(val, sink);
   }

   @Override
   public InstructionSource pop(final InstructionSink sink) {
      return linearInstruction(new PopOp(), sink);
   }

   @Override
   public InstructionSource lcmp(final InstructionSink sink) {
      return linearInstruction(new LCmpOp(), sink);
   }

   @Override
   public InstructionSource fcmpl(final InstructionSink sink) {
      return binaryOp(new FCmpLOperator(), sink);
   }

   @Override
   public InstructionSource fcmpg(final InstructionSink sink) {
      return binaryOp(new FCmpGOperator(), sink);
   }

   @Override
   public InstructionSource f2i(final InstructionSink sink) {
      return linearInstruction(new F2IOp(), sink);
   }

   @Override
   public InstructionSource i2f(final InstructionSink sink) {
      return linearInstruction(new I2FOp(), sink);
   }

   @Override
   public InstructionSource l2i(final InstructionSink sink) {
      return linearInstruction(new L2IOp(), sink);
   }

   @Override
   public InstructionSource i2l(final InstructionSink sink) {
      return linearInstruction(new I2LOp(), sink);
   }

   @Override
   public InstructionSource lushr(final InstructionSink sink) {
      return linearInstruction(new LushrOp(), sink);
   }

   @Override
   public InstructionSource ixor(final InstructionSink sink) {
      return linearInstruction(new IxorOp(), sink);
   }

   @Override
   public InstructionSource ior(final InstructionSink sink) {
      return linearInstruction(new IorOp(), sink);
   }

   @Override
   public InstructionSource iushr(final InstructionSink sink) {
      return linearInstruction(new IushrOp(), sink);
   }

   @Override
   public InstructionSource ishr(final InstructionSink sink) {
      return linearInstruction(new IshrOp(), sink);
   }

   @Override
   public InstructionSource ishl(final InstructionSink sink) {
      return linearInstruction(new IshlOp(), sink);
   }

   @Override
   public InstructionSource arrayLength(final InstructionSink sink) {
      return linearInstruction(new ArrayLengthOp(), sink);
   }

   @Override
   public InstructionSource aaload(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aaLoad(), sink);
   }

   @Override
   public InstructionSource iaload(final InstructionSink sink) {
      return linearInstruction(instructionFactory.iaLoad(), sink);
   }

   @Override
   public InstructionSource caload(final InstructionSink sink) {
      return linearInstruction(ArrayLoadOp.caLoad(), sink);
   }

   @Override
   public InstructionSource aaStore(final InstructionSink sink) {
      return linearInstruction(instructionFactory.aaStore(), sink);
   }

   @Override
   public InstructionSource iaStore(final InstructionSink sink) {
      return linearInstruction(instructionFactory.iaStore(), sink);
   }

   @Override
   public InstructionSource caStore(final InstructionSink sink) {
      return linearInstruction(ArrayStoreOp.caStore(), sink);
   }

   @Override
   public InstructionSource lconst_1(final InstructionSink sink) {
      return linearInstruction(instructionHelper.lconst(1), sink);
   }

   @Override
   public InstructionSource lconst_0(final InstructionSink sink) {
      return linearInstruction(instructionHelper.lconst(0), sink);
   }

   @Override
   public InstructionSource iconst_5(final InstructionSink sink) {
      return iconst(5, sink);
   }

   @Override
   public InstructionSource iconst_4(final InstructionSink sink) {
      return iconst(4, sink);
   }

   @Override
   public InstructionSource iconst_3(final InstructionSink sink) {
      return iconst(3, sink);
   }

   @Override
   public InstructionSource iconst_2(final InstructionSink sink) {
      return iconst(2, sink);
   }

   @Override
   public InstructionSource iconst_1(final InstructionSink sink) {
      return iconst(1, sink);
   }

   @Override
   public InstructionSource iconst_m1(final InstructionSink sink) {
      return iconst(-1, sink);
   }

   @Override
   public InstructionSource dup_x1(final InstructionSink sink) {
      return linearInstruction(new Dup_X1Op(), sink);
   }

   @Override
   public InstructionSource dup(final InstructionSink sink) {
      return linearInstruction(new DupOp(), sink);
   }

   @Override
   public InstructionSource ineg(final InstructionSink sink) {
      return linearInstruction(new UnaryOp(instructionFactory.inegOperation()), sink);
   }

   @Override
   public InstructionSource isub(final InstructionSink sink) {
      return binaryOp(instructionFactory.isubOperation(), sink);
   }

   @Override
   public InstructionSource fdiv(final InstructionSink sink) {
      return binaryOp(instructionFactory.fdivOperation(), sink);
   }

   @Override
   public InstructionSource fmul(final InstructionSink sink) {
      return binaryOp(instructionFactory.fmulOperation(), sink);
   }

   @Override
   public InstructionSource imul(final InstructionSink sink) {
      return binaryOp(instructionFactory.imulOperation(), sink);
   }

   @Override
   public InstructionSource iadd(final InstructionSink sink) {
      return binaryOp(instructionFactory.iaddOperation(), sink);
   }

   @Override
   public InstructionSource land(final InstructionSink sink) {
      return linearInstruction(new Binary2Op(instructionFactory.landOperation()), sink);
   }

   @Override
   public InstructionSource iand(final InstructionSink sink) {
      return binaryOp(instructionFactory.iandOperation(), sink);
   }

   @Override
   public InstructionSource putStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return loadingInstruction(fieldInsnNode.owner, new PutStaticOp(fieldInsnNode), sink);
   }

   @Override
   public InstructionSource getStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return loadingInstruction(fieldInsnNode.owner, new GetStaticOp(fieldInsnNode), sink);
   }

   @Override
   public InstructionSource getField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return linearInstruction(instructionFactory.getField(fieldInsnNode), sink);
   }

   @Override
   public InstructionSource putField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return linearInstruction(instructionFactory.putField(fieldInsnNode), sink);
   }

   @Override
   public InstructionSource store(final int var, final InstructionSink sink) {
      return linearInstruction(new Store(var), sink);
   }

   @Override
   public InstructionSource store2(final int var, final InstructionSink sink) {
      return linearInstruction(new Store2(var), sink);
   }

   @Override public InstructionSource aconst_null(final InstructionSink sink) {
      return linearInstruction(new AConstNullOp(), sink);
   }

   @Override public InstructionSource iconst(final int val, final InstructionSink sink) {
      return linearInstruction(nullary(instructionFactory.iconst(val)), sink);
   }

   @Override public InstructionSource iconst_0(final InstructionSink sink) {
      return iconst(0, sink);
   }
   @Override public InstructionSource fconst_0(final InstructionSink sink) {
      return linearInstruction(fconst(0), sink);
   }

   @Override public InstructionSource stringPoolLoad(final String constVal, final InstructionSink sink) {
      return linearInstruction(new StringPoolLoadOperator(constVal), sink);
   }

   @Override public InstructionSource objectPoolLoad(final Type constVal, final InstructionSink sink) {
      return linearInstruction(new ObjectPoolLoad(constVal), sink);
   }

   @Override public InstructionSource load(final int index, final InstructionSink sink) {
      return linearInstruction(new Load(index), sink);
   }

   @Override public InstructionSource load2(final int index, final InstructionSink sink) {
      return linearInstruction(new Load2(index), sink);
   }

   @Override public InstructionSource returnVoid(final InstructionSink sink) {
      sink.nextInstruction(new ReturnInstruction(0));
      return this;
   }

   @Override public InstructionSource return1(final InstructionSink sink) {
      sink.nextInstruction(new ReturnInstruction(1));
      return this;
   }

   @Override public InstructionSource return2(final InstructionSink sink) {
      sink.nextInstruction(new ReturnInstruction(2));
      return this;
   }

   @Override public InstructionSource newObject(final String klassDesc, final InstructionSink sink) {
      return loadingInstruction(klassDesc, instructionHelper.newOp(klassDesc), sink);
   }

   private InstructionSource binaryOp(final BinaryOperator operation, final InstructionSink sink) {
      sink.linearInstruction(new BinaryOp(operation));
      return this;
   }

   private InstructionSource linearInstruction(final Vop op, final InstructionSink sink) {
      sink.linearInstruction(op);
      return this;
   }

   private InstructionSource loadingInstruction(final String klassDesc, final Vop op, final InstructionSink sink) {
      sink.nextInstruction(instructionHelper.loadingInstruction(klassDesc, op, instructions));
      return this;
   }

   @Override public InstructionSource aload(final int index, final InstructionSink sink) {
      return load(index, sink);
   }

   @Override public InstructionSource fload(final int index, final InstructionSink sink) {
      return load(index, sink);
   }

   @Override public InstructionSource dload(final int index, final InstructionSink sink) {
      return load2(index, sink);
   }

   private NullaryOp fconst(final float constVal) {
      return nullary(instructionFactory.fconst(constVal));
   }

   private NullaryOp nullary(final NullaryOperator nullary) {
      return new NullaryOp(nullary);
   }
}
