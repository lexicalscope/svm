package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.trace.Trace.CallReturn.CALL;
import static java.util.Arrays.copyOf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class Trace implements Iterable<Trace> {
   private final Trace previous;
   private final SMethodDescriptor method;
   private final CallReturn callReturn;
   private final Object[] args;
   private final Map<Object, Alias> map;
   private final int nextAlias;
   public enum CallReturn { CALL, RETURN }

   public Trace() {
      this(null, new HashMap<Object, Alias>(), 0, null, null, new Object[0]);
   }

   private Trace(
         final Trace trace,
         final Map<Object, Alias> map,
         final int nextAlias,
         final SMethodDescriptor method,
         final CallReturn callReturn,
         final Object[] args) {
      this.nextAlias = nextAlias;
      assert !Arrays.asList(args).contains(null);
      this.previous = trace;
      this.map = map;
      this.method = method;
      this.callReturn = callReturn;
      this.args = args;
   }

   public Trace extend(final SMethodDescriptor methodCalled, final CallReturn callReturn, final Object ... args) {
      final Object[] normalisedArgs = copyOf(args, args.length);
      int newNextAlias = nextAlias;
      Map<Object, Alias> newMap = map;

      if(callReturn.equals(CallReturn.CALL)) {
         for (final int i : methodCalled.objectArgIndexes()) {
            Alias alias;
            if(null == (alias = newMap.get(args[i + 1]))) {
               // TODO[tim] do COW here
               if(map == newMap) {
                  newMap = new HashMap<>(map);
               }
               alias = new Alias(newNextAlias++);
               newMap.put(args[i + 1], alias);
            }
            normalisedArgs[i + 1] = alias;
         }
      }
      if(callReturn.equals(CallReturn.CALL) || methodCalled.returnIsObject()) {
         Alias alias;
         if(null == (alias = newMap.get(args[0]))) {
            // TODO[tim] do COW here
            if(map == newMap) {
               newMap = new HashMap<>(map);
            }
            alias = new Alias(newNextAlias++);
            newMap.put(args[0], alias);
         }
         normalisedArgs[0] = alias;
      }

      return new Trace(this, newMap, newNextAlias, methodCalled, callReturn, normalisedArgs);
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
