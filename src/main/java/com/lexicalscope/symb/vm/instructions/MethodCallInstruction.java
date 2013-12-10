package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackVop;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;

public class MethodCallInstruction implements Instruction {
   public interface MethodInvokation {
      int argSize(SMethod targetMethod);

      String name();
   }

   public static class VirtualMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }
   }

   public static class SpecialMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }
   }

   public static class StaticMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize() - 1;
      }

      @Override public String name() {
         return "INVOKESTATIC";
      }
   }

   private final SClassLoader classLoader;
   private final MethodInsnNode methodInsnNode;
   private final MethodInvokation methodInvokation;

   public MethodCallInstruction(final SClassLoader classLoader, final MethodInsnNode methodInsnNode, final MethodInvokation methodInvokation) {
      this.classLoader = classLoader;
      this.methodInsnNode = methodInsnNode;
      this.methodInvokation = methodInvokation;
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      // TODO[tim]: virtual does not resolve overridden methods
      final SMethod targetMethod = classLoader.loadMethod(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);

      state.op(new StackVop() {
         @Override public void eval(final Stack stack) {
            stack.pushFrame(instruction.next(), targetMethod, methodInvokation.argSize(targetMethod));
         }
      });
   }

   @Override
   public String toString() {
      return String.format("%s %s%s", methodInvokation.name(), methodInsnNode.name, methodInsnNode.desc);
   }

   public static Instruction createInvokeVirtual(final SClassLoader classLoader, final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(classLoader, methodInsnNode, new VirtualMethodInvokation());
   }

   public static Instruction createInvokeSpecial(final SClassLoader classLoader, final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(classLoader, methodInsnNode, new SpecialMethodInvokation());
   }

   public static Instruction createInvokeStatic(final SClassLoader classLoader, final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(classLoader, methodInsnNode, new StaticMethodInvokation());
   }

   public static Instruction createInvokeStatic(final SClassLoader classLoader, final String klass, final String method, final String desc) {
      return createInvokeStatic(classLoader, new MethodInsnNode(Opcodes.INVOKESTATIC, klass, method, desc));
   }
}
