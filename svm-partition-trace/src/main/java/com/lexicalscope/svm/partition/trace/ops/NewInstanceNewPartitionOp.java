package com.lexicalscope.svm.partition.trace.ops;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.JStateAdaptor;
import com.lexicalscope.svm.vm.j.Vop;

public final class NewInstanceNewPartitionOp implements Vop {
   private final Vop newOp;
   private final Object partitionTag;

   public NewInstanceNewPartitionOp(
         final Vop newInstruction,
         final Object partitionTag) {
      this.newOp = newInstruction;
      this.partitionTag = partitionTag;
   }

   @Override public void eval(final JState ctx) {
      newOp.eval(new JStateAdaptor(ctx){
         @Override public ObjectRef newObject(final Allocatable klass) {
            return ctx.newObject(klass, partitionTag);
         }
      });
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return newOp.query(instructionQuery);
   }

   @Override public String toString() {
      return partitionTag + " PARTITION OBJECT";
   }
}