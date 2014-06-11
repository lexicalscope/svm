package com.lexicalscope.svm.partition.trace.ops;

import static com.lexicalscope.svm.j.instruction.concrete.object.PartitionTagMetaKey.PARTITION_TAG;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.JStateAdaptor;
import com.lexicalscope.svm.vm.j.Vop;

public class NewInstanceSamePartitionOp implements Vop {
   private final Vop newOp;

   public NewInstanceSamePartitionOp(final Vop newOp) {
      this.newOp = newOp;
   }

   @Override public void eval(final JState ctx) {
      newOp.eval(new JStateAdaptor(ctx){
         @Override public ObjectRef newObject(final Allocatable klass) {
            return ctx.newObject(klass, ctx.getFrameMeta(PARTITION_TAG));
         }

         @Override public ObjectRef newObject(final Allocatable klass, final Object tag) {
            assert false : "new operation must not provide a custom tag";
            return super.newObject(klass, tag);
         }
      });
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return newOp.query(instructionQuery);
   }

   @Override public String toString() {
      return "UNDEFINED PARTITION OBJECT";
   }
}