package com.lexicalscope.svm.j.instruction.concrete.object;


public interface FieldConversionFactory {
   FieldConversion noConversion();

   FieldConversion intToInt();

   FieldConversion intToChar();
   FieldConversion charToInt();
}