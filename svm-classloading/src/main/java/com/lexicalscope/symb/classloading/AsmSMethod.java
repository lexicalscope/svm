package com.lexicalscope.symb.classloading;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSwitch;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.j.klass.SMethod;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class AsmSMethod implements SMethod {
   private final SClassLoader classLoader;
   private final SMethodDescriptor methodName;
   private final MethodNode method;
   private final InstructionSource instructions;

   private Instruction entryPoint;
   private int maxLocals;
   private int maxStack;

   public AsmSMethod(
         final SClassLoader classLoader,
         final SMethodDescriptor methodName,
         final InstructionSource instructions,
         final MethodNode method) {
      this.classLoader = classLoader;
      this.methodName = methodName;
      this.instructions = instructions;
      this.method = method;
   }

   @Override
   public int maxLocals() {
      link();
      return maxLocals;
   }

   @Override
   public int maxStack() {
      link();
      return maxStack;
   }

   @Override
   public Instruction entry() {
      link();
      return entryPoint;
   }

   private void link() {
      if(entryPoint != null) {
         return;
      }

      if((method.access & Opcodes.ACC_NATIVE) != 0) {
         linkNativeMethod();
      } else {
         linkJavaMethod();
      }
   }

   private void linkNativeMethod() {
      final MethodBody resolved = classLoader.resolveNative(methodName);

      maxLocals = resolved.maxLocals();
      maxStack = resolved.maxStack();
      entryPoint = resolved.entryPoint();
   }

   private void linkJavaMethod() {
      final List<AbstractInsnNode> unlinked = new ArrayList<>();
      final Map<AbstractInsnNode, Instruction> linked = new LinkedHashMap<>();
      final Instruction[] prev = new Instruction[1];

      final AbstractInsnNode[] asmInstruction = new AbstractInsnNode[]{getEntryPoint()};
      final InstructionSource.InstructionSink instructionSink = new AbstractInstructionSink() {
         @Override public void nextInstruction(final Instruction node) {
            for (final AbstractInsnNode unlinkedInstruction : unlinked) {
               linked.put(unlinkedInstruction, node);
            }
            unlinked.clear();
            linked.put(asmInstruction[0], node);
            if(prev[0] != null) {
               prev[0].nextIs(node);
            }
            prev[0] = node;
         }

         @Override public void noOp() {
            unlinked.add(asmInstruction[0]);
         }
      };

      while(asmInstruction[0] != null) {
         instructionFor(asmInstruction[0], instructionSink);
         asmInstruction[0] = asmInstruction[0].getNext();
      }

      for (final Entry<AbstractInsnNode, Instruction> entry : linked.entrySet()) {
         if(entry.getKey() instanceof JumpInsnNode) {
            final JumpInsnNode asmJumpInstruction = (JumpInsnNode) entry.getKey();
            final AbstractInsnNode asmInstructionAfterTargetLabel = asmJumpInstruction.label.getNext();
            final Instruction jmpTarget = linked.get(asmInstructionAfterTargetLabel);

            assert asmInstructionAfterTargetLabel != null;
            assert jmpTarget != null : asmInstructionAfterTargetLabel;

            entry.getValue().jmpTarget(jmpTarget);
         }
      }

      maxLocals = method.maxLocals;
      maxStack = method.maxStack;
      entryPoint = linked.values().iterator().next();
   }

   private void instructionFor(
         final AbstractInsnNode abstractInsnNode,
         final InstructionSource.InstructionSink sink) {

      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LINE:
         case AbstractInsnNode.FRAME:
         case AbstractInsnNode.LABEL:
            sink.noOp();
            return;
      }

      new InstructionSwitch(instructions).instructionFor(abstractInsnNode, sink);
   }

   private AbstractInsnNode getEntryPoint() {
      return method.instructions.get(0);
   }

   @Override
   public int argSize() {
      return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
   }

   @Override
   public SMethodDescriptor name() {
      return methodName;
   }

   @Override public String toString() {
      return name().toString();
   }
}
