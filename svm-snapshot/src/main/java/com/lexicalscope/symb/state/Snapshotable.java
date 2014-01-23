package com.lexicalscope.symb.state;

public interface Snapshotable<T extends Snapshotable<T>> {
   T snapshot();
}
