package Domain;

import Domain.Value.StringValue;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Dictionary;
import java.util.Enumeration;

public class MyFileTable implements MyIFileTable<String, BufferedReader>{
    private Dictionary<String, BufferedReader> fileTable;
    public MyFileTable(){
        fileTable=new java.util.Hashtable<String,BufferedReader>();
    }
    @Override
    public void add(String filename, BufferedReader fileDescriptor) {
        this.fileTable.put(filename, fileDescriptor);
    }

    @Override
    public BufferedReader getFileDescriptor(String filename) {
        return this.fileTable.get(filename);
    }

    @Override
    public boolean isDefined(String filename) {
        return this.fileTable.get(filename) != null;
    }
    @Override
    public String toString(){
        String s="";
        if (fileTable.isEmpty())
            return "FileTable is empty";
        Enumeration<String> e = fileTable.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            s+=key.toString()+"\n";
        }
        return s;
    }
    @Override
    public void remove(String filename) {
        this.fileTable.remove(filename);
    }
}
