package com.lexicalscope.svm.classloading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSwitch;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.MethodBody;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

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
         new MethodLinker().linkJavaMethod();
      }
   }

   private void linkNativeMethod() {
      final MethodBody resolved = classLoader.resolveNative(methodName);

      maxLocals = resolved.maxLocals();
      maxStack = resolved.maxStack();
      entryPoint = resolved.entryPoint();
   }

   private class MethodLinker {
      private final Multimap<AbstractInsnNode, Instruction> linked = LinkedListMultimap.create();
      private AbstractInsnNode asmInstruction = getEntryPoint();

      private void linkJavaMethod() {
         final List<AbstractInsnNode> unlinked = new ArrayList<>();
         final Instruction[] prev = new Instruction[1];

         final InstructionSource.InstructionSink instructionSink = new AbstractInstructionSink(instructions) {
            @Override public void nextInstruction(final InstructionInternal node) {
               for (final AbstractInsnNode unlinkedInstruction : unlinked) {
                  assert unlinkedInstruction != null;
                  linked.put(unlinkedInstruction, node);
               }
               unlinked.clear();
               assert asmInstruction != null;
               linked.put(asmInstruction, node);
               if(prev[0] != null) {
                  prev[0].append(node);
               }
               prev[0] = node;
            }

            @Override public void noOp() {
               unlinked.add(asmInstruction);
            }
         };

         instructions.methodentry(methodName, instructionSink);
         while(asmInstruction != null) {
            instructionFor(asmInstruction, instructionSink);
            asmInstruction = asmInstruction.getNext();
         }

         for (final Entry<AbstractInsnNode, Instruction> entry : linked.entries()) {
            if(entry.getKey() instanceof JumpInsnNode) {
               final JumpInsnNode asmJumpInstruction = (JumpInsnNode) entry.getKey();
               final AbstractInsnNode asmInstructionAfterTargetLabel = asmJumpInstruction.label.getNext();
               assert linked.get(asmInstructionAfterTargetLabel).size() == 1;
               final Instruction jmpTarget = linked.get(asmInstructionAfterTargetLabel).iterator().next();

               assert asmInstructionAfterTargetLabel != null;
               assert jmpTarget != null : asmInstructionAfterTargetLabel;

               entry.getValue().jmpTarget(jmpTarget);
            }
         }

         maxLocals = method.maxLocals;
         maxStack = method.maxStack;
         entryPoint = classLoader.instrument(name(), linked.values().iterator().next());
      }
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

      new InstructionSwitch(instructions).instructionFor(abstractInsnNode, methodName, sink);
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
