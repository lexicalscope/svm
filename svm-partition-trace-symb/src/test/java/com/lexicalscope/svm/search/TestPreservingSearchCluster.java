package com.lexicalscope.svm.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestPreservingSearchCluster {
   public final @Rule JUnitRuleMockery context = new JUnitRuleMockery();
   @Mock Randomiser randomiser;

   final Object candidate1 = new Object();
   final Object candidate2 = new Object();
   final Object candidate3 = new Object();

   private SearchCluster<Object> searchCluster;

   @Before public void initSearchCluster() {
      searchCluster = new PreservingSearchCluster<Object>(randomiser);
   }

   @Test public void beginsEmpty() throws Exception {
      assertThat("begins empty", searchCluster.isEmpty());
   }

   @Test public void addingACandiateCausesItToBeSelected() throws Exception {
      context.checking(new Expectations(){{
         oneOf(randomiser).random(1); will(returnValue(0));
      }});

      searchCluster.add(candidate1);
      assertThat(searchCluster.candidate(), equalTo(candidate1));
      assertThat("not empty after selection from 1", !searchCluster.isEmpty());
   }

   @Test public void canPickTheSameCandidateTwice() throws Exception {
      context.checking(new Expectations(){{
         exactly(2).of(randomiser).random(3); will(returnValue(1));
      }});

      searchCluster.add(candidate1);
      searchCluster.add(candidate2);
      searchCluster.add(candidate3);

      assertThat(searchCluster.candidate(), equalTo(candidate2));
      assertThat(searchCluster.candidate(), equalTo(candidate2));
      assertThat("not empty after selection from 3", !searchCluster.isEmpty());
   }
}
