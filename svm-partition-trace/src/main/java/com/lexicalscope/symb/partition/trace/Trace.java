package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.symb.partition.trace.Trace.CallReturn.CALL;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;
import com.lexicalscope.symb.stack.trace.SMethodName;

public class Trace implements Iterable<Trace> {
   private final Trace previous;
   private final SMethodName method;
   private final CallReturn callReturn;
   private final Object[] args;
   public enum CallReturn { CALL, RETURN }

   public Trace() {
      this(null, null, null, null);
   }

   private Trace(final Trace trace, final SMethodName method, final CallReturn callReturn, final Object[] args) {
      this.previous = trace;
      this.method = method;
      this.callReturn = callReturn;
      this.args = args;
   }

   public Trace extend(final SMethodName methodCalled, final CallReturn callReturn, final Object ... args) {
      return new Trace(this, methodCalled, callReturn, args);
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
//         return String.format("%s, %s", previous, describe());
         return describe();
      } else {
         return describe();
      }
   }

   public Object[] args() {
      return args;
   }

   private String describe() {
      return String.format("[%s]%s - (%s)", callReturn, method, Joiner.on(", ").join(args));
   }
}
