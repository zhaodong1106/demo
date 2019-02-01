package com.example.demo.utils;

import org.springframework.data.annotation.Id;

/**
 * Created by T011689 on 2019/2/1.
 */
public class MongoSequence {
    @Id
    private String id;
    private int seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
