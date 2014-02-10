package com.lexicalscope.svm.state;

public interface Snapshotable<T extends Snapshotable<T>> {
   T snapshot();
}
