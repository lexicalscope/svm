package com.lexicalscope.svm.vm;

import org.hamcrest.Description;

import com.lexicalscope.Transform;

@Deprecated
public class FlowNodeToState<S extends FlowNode> implements Transform<S, S> {
   @Override public void describeTo(final Description description) {
      description.appendText("obtain state");
   }

   @Override public S transform(final S item, final Description mismatchDescription) {
      mismatchDescription.appendText("obtain state");
      return item;
   }
}
