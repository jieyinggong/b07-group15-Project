package com.example.b07project.dbOperation_Information;

import com.example.b07project.main.Information;

public interface EditOperation {
    void update(String id, String path, Information item, DefaultCallback callback);
    void delete(String id, String path, DefaultCallback callback);
}
