package com.lexicalscope.symb.partition.trace;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SMethodName;

public class Trace implements Iterable<Trace> {
   private final Trace previous;
   private final SMethodName method;

   public Trace() {
      this(null, null);
   }

   private Trace(final Trace trace, final SMethodName method) {
      this.previous = trace;
      this.method = method;
   }

   public Trace extend(final StackFrame currentFrame) {
      return new Trace(this, currentFrame.context());
   }

   public List<Trace> asList() {
      final LinkedList<Trace> result = new LinkedList<>();

      Trace element = this;
      while(element.previous != null) {
         result.add(0, element);
         element = element.previous;
      }
      return result;
   }

   @Override public Iterator<Trace> iterator() {
      return asList().iterator();
   }

   public SMethodName methodName() {
      return method;
   }

   @Override public String toString() {
      if(previous == null) {
         return "";
      } else if (previous.previous != null) {
         return String.format("%s%s", previous, method);
      } else {
         return String.format("%s", method);
      }
   }
}
