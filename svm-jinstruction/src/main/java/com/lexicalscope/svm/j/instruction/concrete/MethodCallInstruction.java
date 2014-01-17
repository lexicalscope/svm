package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.symb.stack.SnapshotableStackFrame;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.state.SMethodName;
import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.MethodResolver;
import com.lexicalscope.symb.vm.SClass;
import com.lexicalscope.symb.vm.SMethod;
import com.lexicalscope.symb.vm.SMethodDescriptor;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class MethodCallInstruction {
   private static final class Resolution {
      public Resolution(final String receiverKlass, final SMethod sMethod) {
         this.receiverKlass = receiverKlass;
         this.method = sMethod;
      }

      public Resolution(final SMethod sMethod) {
         this(sMethod.name().klassName(), sMethod);
      }

      final String receiverKlass;
      final SMethod method;
   }
   private interface MethodInvokation {
      Object[] args(State ctx, SMethodDescriptor targetMethod);

      String name();

      Resolution resolveMethod(Object[] args, SMethodDescriptor sMethodName, State ctx);
   }

   private static class VirtualMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final State ctx, final SMethodDescriptor targetMethod) {
         return ctx.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEVIRTUAL";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
         return resolveVirtualMethod(args, sMethodName, ctx);
      }
   }

   private static class InterfaceMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final State ctx, final SMethodDescriptor targetMethod) {
         return ctx.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKEINTERFACE";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
         return resolveVirtualMethod(args, sMethodName, ctx);
      }
   }

   private static Resolution resolveVirtualMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
      final MethodResolver receiverKlass = receiver(args, sMethodName, ctx);
      return new Resolution(receiverKlass.virtualMethod(sMethodName));
   }

   private static MethodResolver receiver(final Object[] args, final SMethodName sMethodName, final State ctx) {
      final Object receiver = ctx.get(args[0], SClass.OBJECT_MARKER_OFFSET);
      assert receiver != null : sMethodName;
      assert receiver instanceof MethodResolver : "no " + sMethodName + " in " + receiver;
      final MethodResolver receiverKlass = (MethodResolver) receiver;
      return receiverKlass;
   }

   private static class SpecialMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final State ctx, final SMethodDescriptor targetMethod) {
         return ctx.pop(targetMethod.argSize());
      }

      @Override public String name() {
         return "INVOKESPECIAL";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
         return new Resolution(ctx.load(sMethodName.klassName()).declaredMethod(sMethodName));
      }
   }

   private static class ClassDefaultConstructorMethodInvokation implements MethodInvokation {
      private final String klassName;

      public ClassDefaultConstructorMethodInvokation(final String klassName) {
         this.klassName = klassName;
      }

      @Override public Object[] args(
            final State ctx,
            final SMethodDescriptor targetMethod) {
         return new Object[]{ctx.whereMyClassAt(klassName)};
      }

      @Override public String name() {
         return "INVOKECLASSCONSTRUCTOR";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
         final MethodResolver receiver = receiver(args, sMethodName, ctx);
         return new Resolution(receiver.declaredMethod(sMethodName));
      }
   }

   private static class StaticMethodInvokation implements MethodInvokation {
      @Override public Object[] args(final State ctx, final SMethodDescriptor targetMethod) {
         return ctx.pop(targetMethod.argSize() - 1);
      }

      @Override public String name() {
         return "INVOKESTATIC";
      }

      @Override public Resolution resolveMethod(final Object[] args, final SMethodDescriptor sMethodName, final State ctx) {
         return new Resolution(ctx.load(sMethodName.klassName()).declaredMethod(sMethodName));
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

      @Override public void eval(final State ctx) {
         final Object[] args = methodInvokation.args(ctx, sMethodName);

         final Resolution resolution = methodInvokation.resolveMethod(args, sMethodName, ctx);
         // TODO[tim]: virtual does not resolve overridden methods
         final SMethod targetMethod = resolution.method;
         final StackFrame newStackFrame = new SnapshotableStackFrame(targetMethod, targetMethod.entry(), targetMethod.maxLocals(), targetMethod.maxStack());
         ctx.pushFrame(newStackFrame.setLocals(args));
      }
   }

   public static Vop createInvokeVirtual(final SMethodDescriptor name) {
      return new LinearInstruction(new MethodCallOp(name, new VirtualMethodInvokation()));
   }

   public static Vop createInvokeInterface(final SMethodDescriptor sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new InterfaceMethodInvokation()));
   }

   public static Vop createInvokeSpecial(final SMethodDescriptor sMethodName) {
      return new LinearInstruction(new MethodCallOp(sMethodName, new SpecialMethodInvokation()));
   }

   public static Vop createClassDefaultConstructor(final String klassName) {
      return new LinearInstruction(new MethodCallOp(JavaConstants.CLASS_DEFAULT_CONSTRUCTOR, new ClassDefaultConstructorMethodInvokation(klassName)));
   }

   public static Vop createInvokeStatic(final SMethodDescriptor sMethodName) {
      return new LoadingInstruction(sMethodName.klassName(), new MethodCallOp(sMethodName, new StaticMethodInvokation()));
   }
}
