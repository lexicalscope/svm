package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.state.SMethodName;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.SnapshotableStackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.MethodResolver;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;

public class MethodCallInstruction {
   private static final class Resolution {
      public Resolution(final String receiverKlass, final SMethod sMethod) {
         this.receiverKlass = receiverKlass;
         this.methodName = sMethod;
      }

      public Resolution(final SMethod sMethod) {
         this(sMethod.name().klassName(), sMethod);
      }

      final String receiverKlass;
      final SMethod methodName;
   }
   private interface MethodInvokation {
      Object[] args(Statics statics, StackFrame stackFrame, SMethodDescriptor targetMethod);

      String name();

      Resolution resolveMethod(Object[] args, SMethodDescriptor sMethodName, Heap heap, Statics statics);
   }

   private static class VirtualMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethodDescriptor targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap, final Statics statics) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   private static class InterfaceMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethodDescriptor targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEINTERFACE";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap, final Statics statics) {
         return resolveVirtualMethod(args, sMethodName, heap);
      }
   }

   private static Resolution resolveVirtualMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap) {
      final MethodResolver receiverKlass = receiver(args, sMethodName, heap);
      return new Resolution(receiverKlass.virtualMethod(sMethodName));
   }

   private static MethodResolver receiver(final Object[] args, final SMethodName sMethodName, final Heap heap) {
      final Object receiver = heap.get(args[0], SClass.OBJECT_MARKER_OFFSET);
      assert receiver != null : sMethodName;
      assert receiver instanceof MethodResolver : "no " + sMethodName + " in " + receiver;
      final MethodResolver receiverKlass = (MethodResolver) receiver;
      return receiverKlass;
   }

   private static class SpecialMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethodDescriptor targetMethod) {
         return stackFrame.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap, final Statics statics) {
         return new Resolution(statics.load(sMethodName.klassName()).declaredMethod(sMethodName));
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
            final SMethodDescriptor targetMethod) {
         return new Object[]{statics.whereMyClassAt(klassName)};
      }

      @Override public String name() {
         return "INVOKECLASSCONSTRUCTOR";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap, final Statics statics) {
         final MethodResolver receiver = receiver(args, sMethodName, heap);
         return new Resolution(receiver.declaredMethod(sMethodName));
      }
   }

   private static class StaticMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final Statics statics, final StackFrame stackFrame, final SMethodDescriptor targetMethod) {
         return stackFrame.pop(targetMethod.argSize() - 1);
      }

      @Override public String name() {
         return "INVOKESTATIC";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final Heap heap, final Statics statics) {
         return new Resolution(statics.load(sMethodName.klassName()).declaredMethod(sMethodName));
      }
   }

   private static final class MethodCallOp implements Vop {
      private final MethodInvokation methodInvokation;
      private final SMethodDescriptor sMethodName;

      public MethodCallOp(final SMethodDescriptor sMethodName, final MethodInvokation methodInvokation) {
         this.sMethodName = sMethodName;
         this.methodInvokation = methodInvokation;
      }

      @Override
      public String toString() {
         return String.format("%s %s", methodInvokation.name(), sMethodName);
      }

      @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
         final Object[] args = methodInvokation.args(statics, stackFrame, sMethodName);

         final Resolution resolution = methodInvokation.resolveMethod(args, sMethodName, heap, statics);
         // TODO[tim]: virtual does not resolve overridden methods
         final SMethod targetMethod = resolution.methodName;
//         final SMethod targetMethod = statics.loadMethod(resolvedMethod);

         final StackFrame newStackFrame = new SnapshotableStackFrame(resolution.receiverKlass, targetMethod, targetMethod.entry(), targetMethod.maxLocals(), targetMethod.maxStack());
         stack.push(newStackFrame.setLocals(args));
      }
   }

   public static Instruction createInvokeVirtual(final SMethodDescriptor name) {
      return new LinearInstruction(new MethodCallOp(name, new VirtualMethodInvokation()));
   }

   public static Instruction createInvokeInterface(final SMethodDescriptor sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new InterfaceMethodInvokation()));
   }

   public static Instruction createInvokeSpecial(final SMethodDescriptor sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new SpecialMethodInvokation()));
   }

   public static Instruction createClassDefaultConstructor(final String klassName) {
      return new LinearInstruction(new MethodCallOp(JavaConstants.CLASS_DEFAULT_CONSTRUCTOR, new ClassDefaultConstructorMethodInvokation(klassName)));
   }

   public static Instruction createInvokeStatic(final SMethodDescriptor sMethodName) {
      return new LoadingInstruction(sMethodName.klassName(), new MethodCallOp(sMethodName, new StaticMethodInvokation()));
   }
}
