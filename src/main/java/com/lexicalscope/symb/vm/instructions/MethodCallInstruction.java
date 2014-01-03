package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public class MethodCallInstruction {
   private interface MethodInvokation {
      Object[] args(Statics statics, StackFrame stackFrame, SMethod targetMethod);

      String name();

      SMethodName resolveMethod(Object[] args, SMethodName sMethodName, Heap heap);
   }

   private static class VirtualMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethod targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   private static class InterfaceMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethod targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEINTERFACE";
      }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   private static SMethodName resolveVirtualMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
      final Object receiver = heap.get(args[0], SClass.OBJECT_MARKER_OFFSET);
      assert receiver != null : sMethodName;
      assert receiver instanceof SClass : "no " + sMethodName + " in " + receiver;
      return ((SClass) receiver).resolve(sMethodName);
   }

   private static class SpecialMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethod targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return sMethodName;
      }
   }

   private static class ClassDefaultConstructorMethodInvokation implements MethodInvokation {
      private final String klassName;

      public ClassDefaultConstructorMethodInvokation(final String klassName) {
         this.klassName = klassName;
      }

      @Override public Object[] args(
            final Statics statics,
            final StackFrame stackFrame,
            final SMethod targetMethod) {
         return new Object[]{statics.whereMyClassAt(klassName)};
      }

      @Override public String name() {
         return "INVOKECLASSCONSTRUCTOR";
      }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return sMethodName;
      }
   }

   private static class StaticMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethod targetMethod) {
         return stackFrame.pop(targetMethod.argSize() - 1);
      }

      @Override public String name() {
         return "INVOKESTATIC";
      }

      @Override public SMethodName resolveMethod(final Object[] args, final SMethodName sMethodName, final Heap heap) {
         return sMethodName;
      }
   }

   private static final class MethodCallOp implements Vop {
      private final MethodInvokation methodInvokation;
      private final SMethodName sMethodName;

      public MethodCallOp(final SMethodName sMethodName, final MethodInvokation methodInvokation) {
         this.sMethodName = sMethodName;
         this.methodInvokation = methodInvokation;
      }

      @Override
      public String toString() {
         return String.format("%s %s", methodInvokation.name(), sMethodName);
      }

      @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
         final SMethod invokedMethod = statics.loadMethod(sMethodName);
         final Object[] args = methodInvokation.args(statics, stackFrame, invokedMethod);

         // TODO[tim]: virtual does not resolve overridden methods
         final SMethodName resolvedMethod = methodInvokation.resolveMethod(args, sMethodName, heap);
         final SMethod targetMethod = statics.loadMethod(resolvedMethod);

         final StackFrame newStackFrame = new SnapshotableStackFrame(targetMethod, targetMethod.entry(), targetMethod.maxLocals(), targetMethod.maxStack());
         stack.push(newStackFrame.setLocals(args));
      }
   }

   public static Instruction createInvokeVirtual(final SMethodName name) {
      return new LinearInstruction(new MethodCallOp(name, new VirtualMethodInvokation()));
   }

   public static Instruction createInvokeInterface(final SMethodName sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new InterfaceMethodInvokation()));
   }

   public static Instruction createInvokeSpecial(final SMethodName sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new SpecialMethodInvokation()));
   }

   public static Instruction createClassDefaultConstructor(final String klassName) {
      return new LinearInstruction(new MethodCallOp(JavaConstants.CLASS_DEFAULT_CONSTRUCTOR, new ClassDefaultConstructorMethodInvokation(klassName)));
   }

   public static Instruction createInvokeStatic(final SMethodName sMethodName) {
      return new LoadingInstruction(sMethodName.klassName(), new MethodCallOp(sMethodName, new StaticMethodInvokation()));
   }
}
