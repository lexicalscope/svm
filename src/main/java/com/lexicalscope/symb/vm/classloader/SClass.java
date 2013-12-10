package com.lexicalscope.symb.vm.classloader;

import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SClass {
   private final TreeMap<SFieldName, Integer> fieldMap;
   private final TreeMap<SFieldName, Integer> staticFieldMap;
	private final ClassNode classNode;
	private final Instructions instructions;
   private final int classStartOffset;
   private final int subclassOffset;
   private final SClass superclass;
   private final SClassLoader classLoader;

	public SClass(
	      final SClassLoader classLoader,
	      final Instructions instructions,
	      final ClassNode classNode,
	      final SClass superclass) {
		this.classLoader = classLoader;
      this.instructions = instructions;
		this.classNode = classNode;
      this.superclass = superclass;

		this.classStartOffset = superclass == null ? 0 :superclass.subclassOffset;
		this.staticFieldMap = new TreeMap<>();
		this.fieldMap = new TreeMap<>();
		if(superclass != null) fieldMap.putAll(superclass.fieldMap);

		final List<?> fields = classNode.fields;
		int staticOffset = 0;
		int dynamicOffset = 0;
		for (int i = 0; i < fields.size(); i++) {
		   final FieldNode fieldNode = (FieldNode) fields.get(i);
         final SFieldName fieldName = new SFieldName(this.name(), fieldNode.name);
         if((fieldNode.access & Opcodes.ACC_STATIC) !=0) {
            staticFieldMap.put(fieldName, staticOffset);
            staticOffset++;
         } else {
            fieldMap.put(fieldName, dynamicOffset + classStartOffset);
            dynamicOffset++;
         }
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
				return new SMethod(classLoader, instructions, method);
			}
		}
		throw new SMethodNotFoundException(name, desc);
	}

   public int fieldCount() {
      return fieldMap.size();
   }

   public int fieldIndex(final SFieldName name) {
      return fieldMap.get(name);
   }

   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
   }

   public int staticFieldCount() {
      return staticFieldMap.size();
   }

   public int staticFieldIndex(final SFieldName name) {
      return staticFieldMap.get(name);
   }

   public boolean hasStaticField(final SFieldName name) {
      return staticFieldMap.containsKey(name);
   }

   public String name() {
      return classNode.name;
   }

   public Object superclass() {
      return superclass;
   }

   public boolean initialised(final State state) {
      return true;
   }

   @Override
   public String toString() {
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fieldMap);
   }
}
