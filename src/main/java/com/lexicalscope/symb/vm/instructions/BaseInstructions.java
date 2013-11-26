package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.concinstructions.Label;
import com.lexicalscope.symb.vm.concinstructions.LineNumber;
import com.lexicalscope.symb.vm.concinstructions.LinearInstruction;
import com.lexicalscope.symb.vm.concinstructions.Return;
import com.lexicalscope.symb.vm.concinstructions.Terminate;
import com.lexicalscope.symb.vm.concinstructions.UnsupportedInstruction;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOp;
import com.lexicalscope.symb.vm.instructions.ops.Iload;
import com.lexicalscope.symb.vm.instructions.transformers.StackFrameTransformer;

public final class BaseInstructions implements Instructions {
	private final InstructionFactory instructionFactory;

	public BaseInstructions(InstructionFactory instructionFactory) {
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
		case AbstractInsnNode.VAR_INSN:
			final VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
			switch (abstractInsnNode.getOpcode()) {
			case Opcodes.ILOAD:
				return new LinearInstruction(varInsnNode,
						new StackFrameTransformer(new Iload(varInsnNode.var)));
			}
		case AbstractInsnNode.INSN:
			switch (abstractInsnNode.getOpcode()) {
			case Opcodes.RETURN:
				return new Return(0);
			case Opcodes.IRETURN:
				return new Return(1);
			}
		default:
			switch (abstractInsnNode.getOpcode()) {
			case Opcodes.IADD:
				return new LinearInstruction(abstractInsnNode,
						new StackFrameTransformer(new BinaryOp(instructionFactory.addOperation())));
			default:
				return new UnsupportedInstruction(abstractInsnNode);
			}
		}
	}
}
