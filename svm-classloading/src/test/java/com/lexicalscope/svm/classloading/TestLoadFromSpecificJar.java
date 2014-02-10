package com.lexicalscope.svm.classloading;

import static com.lexicalscope.matchers.url.MatchersUrl.*;
import static com.lexicalscope.svm.classloading.ClasspathClassRepository.classpathClassRepostory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;

import org.junit.Test;

import com.lexicalscope.svm.examples.ExamplesOneMarker;
import com.lexicalscope.svm.examples.ExamplesTwoMarker;

public class TestLoadFromSpecificJar {
   @Test public void loadsFromExamplesOne() {
      assertThat(
            classpathClassRepostory(ExamplesOneMarker.class)
               .loadFromRepository("com/lexicalscope/svm/examples/ExamplesMarker"),
            both(urlHasProtocolFile()).and(urlPathContains("svm-examples-one")));
   }

   @Test public void loadsFromExamplesTwo() {
      assertThat(
            classpathClassRepostory(ExamplesTwoMarker.class)
               .loadFromRepository("com/lexicalscope/svm/examples/ExamplesMarker"),
            both(urlHasProtocolFile()).and(urlPathContains("svm-examples-two")));
   }
}
