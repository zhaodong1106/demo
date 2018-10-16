package com.example.demo.service;

import com.example.demo.entity.Comment;

import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface CommentService {
    List<Comment> select(int informationId);
}
