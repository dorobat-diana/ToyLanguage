package Domain;

import java.io.BufferedReader;
import java.io.Reader;

public interface MyIFileTable<String,BufferedReader> {

    void add(String filename, BufferedReader fileDescriptor);

    BufferedReader getFileDescriptor(String filename);
    boolean isDefined(String filename);
    void remove(String filename);
}
