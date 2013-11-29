package com.lexicalscope.symb.z3;

import org.junit.Test;

import com.microsoft.z3.Z3Exception;

public class TestFeasibilityChecker {
   @Test
   public void testZ3IsWorking() throws Z3Exception {
      new FeasbilityChecker().checkZ3IsWorking();
   }
}
