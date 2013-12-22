package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.StackVop;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class MethodCallInstruction implements Instruction {
   public interface MethodInvokation {
      int argSize(SMethod targetMethod);

      String name();

      boolean load(State state, String klassName);
   }

   public static class VirtualMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }

      @Override public boolean load(final State state, final String klassName) { return false; }
   }

   public static class SpecialMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }

      @Override public boolean load(final State state, final String klassName) { return false; }
   }

   public static class StaticMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize() - 1;
      }

      @Override public String name() {
         return "INVOKESTATIC";
      }

      @Override public boolean load(final State state, final String klassName) {
         return state.op(new DefineClassOp(klassName));
      }
   }

   private final MethodInvokation methodInvokation;
   private final SMethodName sMethodName;

   public MethodCallInstruction(final SMethodName sMethodName, final MethodInvokation methodInvokation) {
      this.sMethodName = sMethodName;
      this.methodInvokation = methodInvokation;
   }

   public MethodCallInstruction(final MethodInsnNode methodInsnNode, final MethodInvokation methodInvokation) {
      this(new SMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc), methodInvokation);
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      if(!methodInvokation.load(state, sMethodName.klassName())){
         state.op(new StackVop() {
            @Override public void eval(final Stack stack, final Statics statics) {
               // TODO[tim]: virtual does not resolve overridden methods
               final SMethod targetMethod = statics.loadMethod(sMethodName);

               final StackFrame stackFrame = new SnapshotableStackFrame(sMethodName, targetMethod.entry(), targetMethod.maxLocals(),targetMethod.maxStack());

               stack.methodInvocation(instruction.next(), methodInvokation.argSize(targetMethod), stackFrame);
            }
         });
      }
   }

   @Override
   public String toString() {
      return String.format("%s %s", methodInvokation.name(), sMethodName);
   }

   public static Instruction createInvokeVirtual(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new VirtualMethodInvokation());
   }

   public static Instruction createInvokeSpecial(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new SpecialMethodInvokation());
   }

   public static Instruction createInvokeSpecial(final SMethodName sMethodName) {
      return new MethodCallInstruction(sMethodName, new SpecialMethodInvokation());
   }

   public static Instruction createInvokeStatic(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new StaticMethodInvokation());
   }

   public static Instruction createInvokeStatic(final SMethodName sMethodName) {
      return new MethodCallInstruction(sMethodName, new StaticMethodInvokation());
   }

   public static Instruction createInvokeStatic(final String klass, final String method, final String desc) {
      return createInvokeStatic(new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }
}
