package com.example.demo.dao;

import com.example.demo.entity.Comment;

import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface ContentDao {
    Comment selectById(int id);
    List<Comment> selectByInformationId(int informationId);
    int  insertComment(Comment comment);
}
