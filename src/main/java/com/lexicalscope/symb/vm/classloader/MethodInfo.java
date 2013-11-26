package com.lexicalscope.symb.vm.classloader;

public class MethodInfo {
	private final String klass;
	private final String name;
	private final String desc;

	public MethodInfo(String klass, String name, String desc) {
		this.klass = klass;
		this.name = name;
		this.desc = desc;
	}

	public String klass() {
		return klass;
	}

	public String name() {
		return name;
	}

	public String desc() {
		return desc;
	}
}
