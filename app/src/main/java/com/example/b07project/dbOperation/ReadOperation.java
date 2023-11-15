package com.example.b07project.dbOperation;
import java.util.List;
public interface ReadOperation<T> {
        void read(String id, Class<T> tClass, ResultCallback<T> callback);
        void listAll(Class<T> tClass, ResultCallback<List<T>> callback);

}
