package com.example.a24a10357_liorzalta_task1.Model;

import java.util.ArrayList;
import java.util.Collections;

public class RecordsList {
    private ArrayList<Record> records = new ArrayList<Record>();

    public RecordsList(){
    }

    public RecordsList setRecords(ArrayList<Record> records) {
        this.records = records;
        Collections.sort(records);
        return this;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public ArrayList<Record> getTopRecords(int length){
        ArrayList<Record> topRecords = new ArrayList<Record>();
        for(int i = 0; i < length && i < records.size(); i++){
            topRecords.add(this.records.get(i));
        }
        return topRecords;
    }

    public boolean add(Record record){
        boolean res = this.records.add(record);
        Collections.sort(this.records);
        return res;
    }
}
