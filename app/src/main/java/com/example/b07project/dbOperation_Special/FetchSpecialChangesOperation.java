package com.example.b07project.dbOperation_Special;

import com.example.b07project.main.Information;
import com.example.b07project.dbOperation_Information.ResultCallback;

public interface FetchSpecialChangesOperation {
    public void fetchNewSpecialitem(String path, Class<?> claz,ResultCallback<Information> callback);
    public void fetchUpdates(String path, Class<?> claz,ResultCallback<Information> callback);
    public void removeListener();
}

