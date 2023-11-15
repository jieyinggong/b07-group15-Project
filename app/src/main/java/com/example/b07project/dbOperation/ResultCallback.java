package com.example.b07project.dbOperation;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
