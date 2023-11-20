package com.example.b07project.dbOperation_o;

import com.example.b07project.Information;

public interface FetchfromChangesOperation {
    void fetchNewitem(String node, ResultCallback<Information> callback);
    public void fetchUpdates(String node, ResultCallback<Information> callback);
}

