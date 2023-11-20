package com.example.b07project.dbOperation_o;

import com.example.b07project.Information;

public interface CreateOperation {
    void create(String path, Information item, DefaultCallback callback);
}

