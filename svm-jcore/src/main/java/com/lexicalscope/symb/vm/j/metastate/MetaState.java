package com.lexicalscope.symb.vm.j.metastate;

import com.lexicalscope.symb.state.Snapshotable;

public interface MetaState extends Snapshotable<MetaState> {
   <T> T get(MetaKey<T> key);
   <T> void set(MetaKey<T> key, T value);
}