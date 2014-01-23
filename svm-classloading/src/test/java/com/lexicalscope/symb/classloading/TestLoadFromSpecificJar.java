package com.lexicalscope.symb.classloading;

import static com.lexicalscope.matchers.url.MatchersUrl.*;
import static com.lexicalscope.symb.classloading.ClasspathClassRepository.classpathClassRepostory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;

import org.junit.Test;

import com.lexicalscope.symb.examples.ExamplesOneMarker;

public class TestLoadFromSpecificJar {
   @Test public void loadsFromExamplesOne() {
      assertThat(
            classpathClassRepostory(ExamplesOneMarker.class)
               .loadFromRepository("com/lexicalscope/symb/examples/ExamplesMarker"),
            both(urlHasProtocolFile()).and(urlPathContains("svm-examples-one")));
   }
}
