package com.lexicalscope.symb.vm;

import org.hamcrest.Description;

import com.lexicalscope.Transform;

public class FlowNodeToState<S> implements Transform<S, FlowNode<S>> {
   @Override public void describeTo(final Description description) {
      description.appendText("obtain state");
   }

   @Override public S transform(final FlowNode<S> item, final Description mismatchDescription) {
      mismatchDescription.appendText("obtain state");
      return item.state();
   }
}
