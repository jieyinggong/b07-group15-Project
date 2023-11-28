package com.example.b07project.dbOperation_Information;

import com.example.b07project.main.Information;

public interface CreateOperation {
    void create(String path, Information item, DefaultCallback callback);
}

