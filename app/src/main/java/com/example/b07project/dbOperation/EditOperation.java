package com.example.b07project.dbOperation;

public interface EditOperation<T> {
    void update(String id, T item);
    void delete(String id, T item);
}
