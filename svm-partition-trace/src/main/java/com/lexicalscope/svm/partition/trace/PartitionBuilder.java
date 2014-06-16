package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.spec.MatchersSpec.*;
import static org.objectweb.asm.Type.getInternalName;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.partition.spec.CallContext;

public class PartitionBuilder {
   private final Set<String> klasses = new HashSet<>();

   public static PartitionBuilder partition() {
      return new PartitionBuilder();
   }

   public PartitionBuilder ofClass(final Class<?> klass) {
      klasses.add(getInternalName(klass));
      return this;
   }

   public Matcher<? super CallContext> newInstanceMatcher() {
      return receiver(klassIn(klasses));
   }
}
