package com.lexicalscope.svm.j.instruction.concrete;


public interface FieldConversionFactory {
   PutFieldConversion noConversion();

   PutFieldConversion intToInt();

   PutFieldConversion intToChar();
   PutFieldConversion charToInt();
}