package com.lexicalscope.symb.vm;

public interface Snapshotable<T extends Snapshotable<T>> {
   T snapshot();
}
