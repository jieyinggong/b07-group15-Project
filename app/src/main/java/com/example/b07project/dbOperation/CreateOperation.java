package com.example.b07project.dbOperation;

public interface CreateOperation<T> {
    void create(String path, T item, DefaultCallback callback);
}

