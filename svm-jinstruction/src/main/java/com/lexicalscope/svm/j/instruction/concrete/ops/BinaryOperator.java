package com.lexicalscope.svm.j.instruction.concrete.ops;

public interface BinaryOperator {
	Object eval(Object left, Object right);
}
