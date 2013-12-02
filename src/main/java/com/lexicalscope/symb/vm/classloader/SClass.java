package com.lexicalscope.symb.vm.classloader;

import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.instructions.Instructions;

public class SClass {
   private final TreeMap<Integer, FieldNode> fieldMap;
	private final ClassNode classNode;
	private final Instructions instructions;
   private final int classStartOffset;
   private final int subclassOffset;

	public SClass(final Instructions instructions, final ClassNode classNode, final SClass superclass) {
		this.instructions = instructions;
		this.classNode = classNode;

		this.classStartOffset = superclass == null ? 0 :superclass.subclassOffset;
		this.fieldMap = new TreeMap<>();
		if(superclass != null) fieldMap.putAll(superclass.fieldMap);

		final List<?> fields = classNode.fields;
		for (int i = 0; i < fields.size(); i++) {
		   fieldMap.put(i + classStartOffset, (FieldNode) fields.get(i));
      }
		subclassOffset = fieldMap.size();
	}

	@SuppressWarnings("unchecked")
	private List<MethodNode> methods() {
		return classNode.methods;
	}

	public SMethod staticMethod(final String name, final String desc) {
		for (final MethodNode method : methods()) {
			if (method.name.equals(name) && method.desc.equals(desc)) {
				return new SMethod(instructions, method);
			}
		}
		throw new SMethodNotFoundException("main");
	}

   public Integer fieldCount() {
      return fieldMap.size();
   }
}
