package com.lexicalscope.svm.partition.trace;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.klass.SFieldName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class InstructionQueryAdapter<T> implements InstructionQuery<T> {
   @Override public T nativ3() {
      return null;
   }

   @Override public T synthetic() {
      return null;
   }

   @Override public T methodentry(final SMethodDescriptor methodName) {
      return null;
   }

   @Override public T methodexit() {
      return null;
   }

   @Override public T arraycopy() {
      return null;
   }

   @Override public T arraylength() {
      return null;
   }

   @Override public T arrayload() {
      return null;
   }

   @Override public T arraystore() {
      return null;
   }

   @Override public T newarray() {
      return null;
   }

   @Override public T branch() {
      return null;
   }

   @Override public T f2i() {
      return null;
   }

   @Override public T iinc() {
      return null;
   }

   @Override public T i2f() {
      return null;
   }

   @Override public T i2l() {
      return null;
   }

   @Override public T checkcast() {
      return null;
   }

   @Override public T getstatic() {
      return null;
   }

   @Override public T instance0f() {
      return null;
   }

   @Override public T l2i() {
      return null;
   }

   @Override public T lcmp() {
      return null;
   }

   @Override public T lushr() {
      return null;
   }

   @Override public T invokevirtual(final SMethodDescriptor methodName) {
      return null;
   }

   @Override public T invokestatic(final SMethodDescriptor methodName) {
      return null;
   }

   @Override public T invokespecial(final SMethodDescriptor methodName) {
      return null;
   }

   @Override public T invokeinterface(final SMethodDescriptor methodName) {
      return null;
   }

   @Override public T aconst_null() {
      return null;
   }

   @Override public T getfield(final SFieldName name) {
      return null;
   }

   @Override public T putfield(final SFieldName name) {
      return null;
   }

   @Override public T binaryop() {
      return null;
   }

   @Override public T nularyop() {
      return null;
   }

   @Override public T unaryop() {
      return null;
   }

   @Override public T objectpoolload() {
      return null;
   }

   @Override public T stringpoolload() {
      return null;
   }

   @Override public T load(final int var) {
      return null;
   }

   @Override public T store(final int var) {
      return null;
   }

   @Override public T dup_x1() {
      return null;
   }

   @Override public T pop() {
      return null;
   }

   @Override public T r3turn(final int returnCount) {
      return null;
   }

   @Override public T dup() {
      return null;
   }

   @Override public T ifacmpeq() {
      return null;
   }

   @Override public T ifle() {
      return null;
   }

   @Override public T ifacmpne() {
      return null;
   }

   @Override public T ifeq() {
      return null;
   }

   @Override public T iflt() {
      return null;
   }

   @Override public T ifne() {
      return null;
   }

   @Override public T ifnonnull() {
      return null;
   }

   @Override public T ifnull() {
      return null;
   }

   @Override public T icmp() {
      return null;
   }

   @Override public T ifgt() {
      return null;
   }

   @Override public T got0() {
      return null;
   }

   @Override public T ifge() {
      return null;
   }

   @Override public T putstatic() {
      return null;
   }

   @Override public T loadarg(final Object value) {
      return null;
   }

   @Override public T newobject(final String klassDesc) {
      return null;
   }
}
