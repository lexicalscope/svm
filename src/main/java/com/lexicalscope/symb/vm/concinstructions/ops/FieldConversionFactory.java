package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.PutFieldConversion;

public interface FieldConversionFactory {
   PutFieldConversion noConversion();

   PutFieldConversion intToInt();

   PutFieldConversion intToChar();
}