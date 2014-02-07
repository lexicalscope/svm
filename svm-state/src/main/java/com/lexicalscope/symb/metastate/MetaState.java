package com.lexicalscope.symb.metastate;

import com.lexicalscope.symb.state.Snapshotable;

public interface MetaState extends Snapshotable<MetaState> {
   <T> T get(MetaKey<T> key);
   <T> void set(MetaKey<T> key, T value);
   boolean contains(MetaKey<?> key);
   void remove(MetaKey<?> key);
}