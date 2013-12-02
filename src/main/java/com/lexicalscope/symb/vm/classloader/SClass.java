package com.lexicalscope.symb.vm.classloader;

import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.instructions.Instructions;

public class SClass {
   private final TreeMap<SFieldName, Integer> fieldMap;
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
		   final FieldNode fieldNode = (FieldNode) fields.get(i);
         fieldMap.put(new SFieldName(this.name(), fieldNode.name), i + classStartOffset);
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

   public Integer fieldIndex(final SFieldName name) {
      return fieldMap.get(name);
   }

   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
   }

   public String name() {
      return classNode.name;
   }

   @Override
   public String toString() {
      return String.format("%s <%s>", name(), fieldMap);
   }
}
