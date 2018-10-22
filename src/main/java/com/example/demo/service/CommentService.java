package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.vo.CommentCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface CommentService {
    List<Comment> select(int informationId,Integer commentId);
    int  insertComment(Comment comment);
    Comment selectById(int id);
    void updateStatus(int informationId);
    List<CommentCountVO>selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem);
}
