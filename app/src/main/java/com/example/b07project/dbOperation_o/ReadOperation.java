package com.example.b07project.dbOperation_o;
import com.example.b07project.Information;

import java.util.List;
public interface ReadOperation {
        void read(String id,String path, ResultCallback<Information> callback);
        void listAll(String path, ResultCallback<List<Information>> callback);

}
