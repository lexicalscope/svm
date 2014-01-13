package com.lexicalscope.symb.vm.symbolic;

public class WithSymbolicObject {
   public static int symbolicObject(final SimpleExpression expression) {
      switch (expression.type()) {
         case 0:
            return expression.left() + expression.right();
         case 1:
            return expression.left() - expression.right();
         case 2:
            return expression.left() * expression.right();
         case 3:
            return expression.left() / expression.right();
         default:
            return 0;
      }
   }
}
