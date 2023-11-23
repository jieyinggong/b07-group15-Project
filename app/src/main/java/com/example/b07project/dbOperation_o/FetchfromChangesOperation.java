package com.example.b07project.dbOperation_o;

import com.example.b07project.Information;

public interface FetchfromChangesOperation {
    void fetchNewitem(String path, ResultCallback<Information> callback);
    public void fetchNewGeneralitem(String path, Class<?> claz,ResultCallback<Information> callback);
    public void fetchUpdates(String path, ResultCallback<Information> callback);
    public void removeListener();
}

