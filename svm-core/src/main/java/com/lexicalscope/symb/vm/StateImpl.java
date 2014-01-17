package com.lexicalscope.symb.vm;

import static com.google.common.base.Objects.equal;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;

public class StateImpl implements State {
   private final Statics statics;
   private final Stack stack;
   private final Heap heap;
   private final Snapshotable<?> meta;
   private final Vm<State> vm;

   public StateImpl(
         final Vm<State> vm,
         final Statics statics,
         final Stack stack,
         final Heap heap,
         final Snapshotable<?> meta) {
      this.vm = vm;
      this.statics = statics;
      this.stack = stack;
      this.heap = heap;
      this.meta = meta;
   }

   @Override
   public void eval() {
      instruction().eval(new Context(vm, statics, heap, stack(), meta));
   }

   @Override
   public InstructionNode instruction() {
      return (InstructionNode) stackFrame().instruction();
   }

   @Override
   public StackFrame stackFrame() {
      return stack().topFrame();
   }

   @Override
   public Stack stack() {
      return stack;
   }

   @Override
   public State[] fork(){
      return new StateImpl[]{this.snapshot(), this.snapshot()};
   }

   @Override
   public Object getMeta() {
      return meta;
   }

   @Override public StateImpl snapshot() {
      return new StateImpl(vm, statics.snapshot(), stack().snapshot(), heap.snapshot(), meta == null ? null : meta.snapshot());
   }

   @Override public SStackTrace trace() {
      return stack().trace();
   }

   @Override
   public String toString() {
      return String.format("stack:<%s>, heap:<%s>, meta:<%s>", stack(), heap, meta);
   }

   @Override public boolean equals(final Object obj) {
      if(obj != null && obj.getClass().equals(this.getClass())) {
         final StateImpl that = (StateImpl) obj;
         return equal(that.stack(), this.stack()) && equal(that.heap, this.heap) && equal(that.heap, this.heap);
      }
      return false;
   }

   @Override
   public int hashCode() {
      return stack().hashCode() ^ heap.hashCode() ^ (meta == null ? 0 : meta.hashCode());
   }

   @Override public State state() {
      return this;
   }

   @Override public Object peekOperand() {
      return stackFrame().peek();
   }
}
