package com.lexicalscope.svm.vm.j;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.lexicalscope.svm.heap.Heap;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SFieldName;

public class HeapMatchers {
   public static final class HeapLocation {
      private final Object heapAddress;
      private final SClass sClass;
      private final String fieldName;

      public HeapLocation(final Object heapAddress, final SClass sClass, final String fieldName) {
         this.heapAddress = heapAddress;
         this.sClass = sClass;
         this.fieldName = fieldName;
      }

      @Override public String toString() {
         return String.format("(%s)%s.%s", heapAddress, sClass.name(), fieldName);
      }
   }

   public static HeapLocation heapLocation(final Object heapAddress, final SClass sClass, final String fieldName) {
      return new HeapLocation(heapAddress, sClass, fieldName);
   }

   public static Matcher<? super Heap> contains(final HeapLocation heapLocation, final Object expected) {
      return new TypeSafeDiagnosingMatcher<Heap>(Heap.class) {
         @Override public void describeTo(final Description description) {
            description.appendText("heap with location ").appendValue(heapLocation).appendText("=").appendValue(expected).appendText("  type=").appendValue(expected != null ? expected.getClass() : null);
         }

         @Override protected boolean matchesSafely(final Heap item, final Description mismatchDescription) {
            final Object actual = item.get(heapLocation.heapAddress, heapLocation.sClass.fieldIndex(new SFieldName(heapLocation.sClass.name(), heapLocation.fieldName)));
            mismatchDescription.appendText("actual value=").appendValue(actual).appendText("  type=").appendValue(actual != null ? actual.getClass() : null);
            return Matchers.equalTo(expected).matches(actual);
         }
      };
   }
}
