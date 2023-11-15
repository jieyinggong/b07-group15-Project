package com.example.b07project.dbOperation;

public interface FetchfromChangesOperation<T> {
    void FetchChanges(Class<T> tClass, ResultCallback<T> callback);
}

