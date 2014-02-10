package com.lexicalscope.svm.stack.trace;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;

import java.util.Iterator;
import java.util.List;

public class SStackTrace implements Iterable<SStackTraceElement> {
   private List<SStackTraceElement> trace;

   public SStackTrace(List<SStackTraceElement> trace) {
      this.trace = trace;
   }

   @Override public Iterator<SStackTraceElement> iterator() {
      return trace.iterator();
   }
   
   @Override public String toString() {
      return format("%s", on(", ").join(trace));
   }
}
