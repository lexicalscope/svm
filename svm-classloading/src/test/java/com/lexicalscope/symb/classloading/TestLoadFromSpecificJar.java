package com.lexicalscope.symb.classloading;

import static com.lexicalscope.matchers.url.MatchersUrl.*;
import static com.lexicalscope.symb.classloading.ClasspathClassRepository.classpathClassRepostory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;

import org.junit.Test;

import com.lexicalscope.symb.examples.ExamplesOneMarker;
import com.lexicalscope.symb.examples.ExamplesTwoMarker;

public class TestLoadFromSpecificJar {
   @Test public void loadsFromExamplesOne() {
      assertThat(
            classpathClassRepostory(ExamplesOneMarker.class)
               .loadFromRepository("com/lexicalscope/symb/examples/ExamplesMarker"),
            both(urlHasProtocolFile()).and(urlPathContains("svm-examples-one")));
   }

   @Test public void loadsFromExamplesTwo() {
      assertThat(
            classpathClassRepostory(ExamplesTwoMarker.class)
               .loadFromRepository("com/lexicalscope/symb/examples/ExamplesMarker"),
            both(urlHasProtocolFile()).and(urlPathContains("svm-examples-two")));
   }
}
