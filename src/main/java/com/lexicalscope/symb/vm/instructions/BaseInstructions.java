package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOp;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Iload;
import com.lexicalscope.symb.vm.instructions.transformers.StackFrameTransformer;

public final class BaseInstructions implements Instructions {
	private final InstructionFactory instructionFactory;

	public BaseInstructions(final InstructionFactory instructionFactory) {
		this.instructionFactory = instructionFactory;
	}

	@Override
	public Instruction instructionFor(final AbstractInsnNode abstractInsnNode) {
		if (abstractInsnNode == null)
			return new Terminate();

		switch (abstractInsnNode.getType()) {
		case AbstractInsnNode.LABEL:
			return new Label((LabelNode) abstractInsnNode);
		case AbstractInsnNode.LINE:
			return new LineNumber((LineNumberNode) abstractInsnNode);
		case AbstractInsnNode.FRAME:
			return new Frame((FrameNode) abstractInsnNode);
		case AbstractInsnNode.VAR_INSN:
			final VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
			switch (abstractInsnNode.getOpcode()) {
			case Opcodes.ILOAD:
				return new LinearInstruction(varInsnNode,
						new StackFrameTransformer(new Iload(varInsnNode.var)));
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
				return binaryOp(abstractInsnNode, instructionFactory.iaddOperation());
			case Opcodes.IMUL:
				return binaryOp(abstractInsnNode, instructionFactory.imulOperation());
			case Opcodes.ICONST_M1:
				return new LinearInstruction(abstractInsnNode,
						new StackFrameTransformer(new NullaryOp(
								instructionFactory.iconst_m1())));
			}
			break;
		case AbstractInsnNode.TYPE_INSN:
         final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
         switch (abstractInsnNode.getOpcode()) {
         case Opcodes.NEW:
            return new LinearInstruction(abstractInsnNode, new StateTransformer() {
               @Override
               public void transform(final State state, final Instruction nextInstruction) {
                  state.op(new HeapVop(){
                     @Override
                     public void eval(final StackFrame stackFrame, final Heap heap) {
                        stackFrame.advance(nextInstruction);
                        stackFrame.push(heap.newObject());
                     }});
               }

               @Override
               public String toString() {
                  return String.format("NEW %s", typeInsnNode.desc);
               }
            });
         }
			break;
		case AbstractInsnNode.JUMP_INSN:
			final JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
			switch (jumpInsnNode.getOpcode()) {
			case Opcodes.IFGE:
				return instructionFactory.branchIfge(jumpInsnNode);
			}
			break;
		}
		return new UnsupportedInstruction(abstractInsnNode);
	}

	private LinearInstruction binaryOp(final AbstractInsnNode abstractInsnNode,
			final BinaryOperator addOperation) {
		return new LinearInstruction(abstractInsnNode,
				new StackFrameTransformer(new BinaryOp(
						addOperation)));
	}
}
