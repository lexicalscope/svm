package com.lexicalscope.symb.vm.instructions.ops;

public class Ops {
	public static LoadConstants loadConstants(Object ... values) {
		return new LoadConstants(values);
	}
}
