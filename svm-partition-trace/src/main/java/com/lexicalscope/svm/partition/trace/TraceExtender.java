package com.lexicalscope.svm.partition.trace;

import static java.util.Arrays.copyOf;

import java.util.HashMap;
import java.util.Map;

public class TraceExtender {
   private final Object[] normalisedArgs;
   private int newNextAlias;
   private Map<Object, Alias> newMap;
   private final Map<Object, Alias> map;

   public TraceExtender(final Object[] args, final Map<Object, Alias> map, final int nextAlias) {
      this.normalisedArgs = copyOf(args, args.length);
      this.newNextAlias = nextAlias;
      this.map = map;
      this.newMap = map;
   }

   public void aliasesForZerothArguments() {
      normalisedArgs[0] = aliasForArg(normalisedArgs[0]);
   }

   public void aliasesForCallArguments(final int[] objectArgIndexes) {
      for (final int i : objectArgIndexes) {
         normalisedArgs[i + 1] = aliasForArg(normalisedArgs[i + 1]);
      }
   }

   private Alias aliasForArg(final Object arg) {
      Alias alias;
      if(null == (alias = newMap.get(arg))) {
         alias = newAlias(arg);
      }
      return alias;
   }

   private Alias newAlias(final Object arg) {
      // TODO[tim] do proper COW here
      if(map == newMap) {
         newMap = new HashMap<>(map);
      }
      final Alias alias = new Alias(newNextAlias++);
      newMap.put(arg, alias);
      return alias;
   }

   public Map<Object, Alias> newMap() {
      return newMap;
   }

   public int newNextAlias() {
      return newNextAlias;
   }

   public Object[] normalisedArgs() {
      return normalisedArgs;
   }
}
