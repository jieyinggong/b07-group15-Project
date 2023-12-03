package com.example.b07project.dbOperation_Special;
import com.example.b07project.main.Information;
import com.example.b07project.dbOperation_Information.ResultCallback;

import java.util.List;

public interface ReadSpecialOperation {
        void readSpecial(String id,Class<?> Claz, String path, ResultCallback<Information> callback);
        void listAllSpecial(String path ,Class<?> Claz , ResultCallback<List<Information>> callback);

}
