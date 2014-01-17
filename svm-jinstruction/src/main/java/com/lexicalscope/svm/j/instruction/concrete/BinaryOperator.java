package com.lexicalscope.svm.j.instruction.concrete;

public interface BinaryOperator {
	Object eval(Object left, Object right);
}
