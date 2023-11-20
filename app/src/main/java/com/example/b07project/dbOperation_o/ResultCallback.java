package com.example.b07project.dbOperation_o;

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
