package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class MethodCallInstruction implements Instruction {
   public interface MethodInvokation {
      int argSize(SMethod targetMethod);

      String name();

      boolean load(State state, String klassName);

      SMethodName resolveMethod(Object[] args, SMethodName sMethodName, Heap heap);
   }

   public static class VirtualMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }

      @Override public boolean load(final State state, final String klassName) { return false; }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   public static class InterfaceMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKEINTERFACE";
      }

      @Override public boolean load(final State state, final String klassName) { return false; }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   private static SMethodName resolveVirtualMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
      final Object receiver = heap.get(args[0], SClass.OBJECT_CLASS_OFFSET);
      assert receiver != null : sMethodName;
      assert receiver instanceof SClass : receiver;
      return ((SClass) receiver).resolve(sMethodName);
   }

   public static class SpecialMethodInvokation implements MethodInvokation {
      @Override public int argSize(final SMethod targetMethod) {
         return targetMethod.argSize();
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }

      @Override public boolean load(final State state, final String klassName) { return false; }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return sMethodName;
      }
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

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return sMethodName;
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
         state.op(new Vop() {
            @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
               final SMethod invokedMethod = statics.loadMethod(sMethodName);
               final Object[] args = stackFrame.pop(methodInvokation.argSize(invokedMethod));

               // TODO[tim]: virtual does not resolve overridden methods
               final SMethodName resolvedMethod = methodInvokation.resolveMethod(args, sMethodName, heap);
               final SMethod targetMethod = statics.loadMethod(resolvedMethod);

               final StackFrame newStackFrame = new SnapshotableStackFrame(targetMethod, targetMethod.entry(), targetMethod.maxLocals(), targetMethod.maxStack());

               stackFrame.advance(instruction.next());
               stack.push(newStackFrame.setLocals(args));
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

   public static Instruction createInvokeInterface(final MethodInsnNode methodInsnNode) {
      return new MethodCallInstruction(methodInsnNode, new InterfaceMethodInvokation());
   }

   public static Instruction createInvokeInterface(final SMethodName sMethodName) {
      return new MethodCallInstruction(sMethodName, new InterfaceMethodInvokation());
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
