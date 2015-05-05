package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class SymbolFactory {
    /** Create an integer symbols while symbolically executing the program. */
    public static native int newIntSymbol();

    /** Create a boolean symbols while symbolically executing the program. */
    public static native boolean newBooleanSymbol();
}
