package com.example.b07project.dbOperation_o;

import com.example.b07project.Information;

public interface EditOperation {
    void update(String id, String node, Information item, DefaultCallback callback);
    void delete(String id, String node, DefaultCallback callback);
}
