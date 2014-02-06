package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.Trace.CallReturn.CALL;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SMethodName;

public class Trace implements Iterable<Trace> {
   private final Trace previous;
   private final SMethodName method;
   private final CallReturn callReturn;
   public enum CallReturn { CALL, RETURN }

   public Trace() {
      this(null, null, null);
   }

   private Trace(final Trace trace, final SMethodName method, final CallReturn callReturn) {
      this.previous = trace;
      this.method = method;
      this.callReturn = callReturn;
   }

   public Trace extend(final StackFrame currentFrame, final CallReturn callReturn) {
      return extend(currentFrame.context(), callReturn);
   }

   public Trace extend(final SMethodName methodCalled, final CallReturn callReturn) {
      return new Trace(this, methodCalled, callReturn);
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

   public boolean isCall() {
      return callReturn.equals(CALL);
   }

   @Override public String toString() {
      if(previous == null) {
         return "";
      } else if (previous.previous != null) {
         return String.format("%s, %s", previous, describe());
      } else {
         return describe();
      }
   }

   private String describe() {
      return String.format("[%s]%s", callReturn, method);
   }
}
