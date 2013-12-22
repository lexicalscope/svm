package com.lexicalscope.symb.vm;

import java.util.Iterator;
import java.util.List;

public class StackTrace implements Iterable<SStackTraceElement> {
   private List<SStackTraceElement> trace;

   public StackTrace(List<SStackTraceElement> trace) {
      this.trace = trace;
   }

   @Override public Iterator<SStackTraceElement> iterator() {
      return trace.iterator();
   }
}
