package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackVop;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SMethod;
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

   private final MethodInsnNode methodInsnNode;
   private final MethodInvokation methodInvokation;

   public MethodCallInstruction(final MethodInsnNode methodInsnNode, final MethodInvokation methodInvokation) {
      this.methodInsnNode = methodInsnNode;
      this.methodInvokation = methodInvokation;
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      if(!methodInvokation.load(state, methodInsnNode.owner)){
         state.op(new StackVop() {
            @Override public void eval(final Stack stack, final Statics statics) {
               // TODO[tim]: virtual does not resolve overridden methods
               final SMethod targetMethod = statics.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
               stack.pushFrame(instruction.next(), targetMethod, methodInvokation.argSize(targetMethod));
            }
         });
      }
   }

   @Override
   public String toString() {
      return String.format("%s %s.%s%s", methodInvokation.name(), methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
   }

   public static Instruction createInvokeVirtual(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new VirtualMethodInvokation());
   }

   public static Instruction createInvokeSpecial(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new SpecialMethodInvokation());
   }

   public static Instruction createInvokeStatic(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new StaticMethodInvokation());
   }

   public static Instruction createInvokeStatic(final String klass, final String method, final String desc) {
      return createInvokeStatic(new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }
}
