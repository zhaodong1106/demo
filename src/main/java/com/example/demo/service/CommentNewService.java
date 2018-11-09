package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentNew;
import com.example.demo.vo.CommentCountVO;
import com.example.demo.vo.CommentNewVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface CommentNewService {
    List<CommentNewVo> select(int informationId, Integer commentId);
    int  insertComment(CommentNew comment);
    CommentNew selectById(int id);
    void updateStatus(int informationId);
    List<CommentCountVO>selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem);
}
