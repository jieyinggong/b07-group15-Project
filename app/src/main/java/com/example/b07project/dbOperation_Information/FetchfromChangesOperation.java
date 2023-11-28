package com.example.b07project.dbOperation_Information;

import com.example.b07project.main.Information;

public interface FetchfromChangesOperation {
    void fetchNewitem(String path, ResultCallback<Information> callback);
    public void fetchUpdates(String path, ResultCallback<Information> callback);
    public void removeListener();
}

