package com.lexicalscope.symb.vm;

public class StaticInfeasibleBranchMethod {
   public static int infeasible(final int x) {
      final int y = x + 1;
      if(y - x < 0) return 10;
      return -10;
    }
}
