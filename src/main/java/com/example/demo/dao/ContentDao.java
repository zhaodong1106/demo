package com.example.demo.dao;

import com.example.demo.entity.Comment;
import com.example.demo.vo.comment.CommentCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface ContentDao {
    Comment selectById(int id);
    List<Comment> selectByInformationId(@Param("informationId") int informationId, @Param("id") Integer id);
    int  insertComment(Comment comment);
    List<CommentCountVO>selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount,@Param("shardingItem") int shardingItem);
    void updateStatus(@Param("informationId") int informationId);
}
