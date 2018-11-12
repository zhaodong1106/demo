package com.example.demo.service.impl;

import com.example.demo.dao.ContentDao;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.vo.comment.CommentCountVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ContentDao contentDao;
    @Override
    public List<Comment> select(int informationId, int commentId) {
        List<Comment> comments = contentDao.selectByInformationId(informationId,commentId);
        for(Comment comment:comments){
            int floor = comment.getFloor();
            if(floor!=1){
                List<Comment> commentList=new ArrayList<>(floor-1);
                int id=comment.getParentId();
                while(floor>1){
                    Comment comment1 = contentDao.selectById(id);
                    id=comment1.getParentId();
                    commentList.add(0,comment1);
                    floor--;
                }
//                Collections.reverse(commentList);
                comment.setParentContents(commentList);
            }
        }
        return comments;
    }

    @Override
    public int insertComment(Comment comment) {
        return contentDao.insertComment(comment);
    }

    @Override
    public Comment selectById(int id) {
        return contentDao.selectById(id);
    }

    @Override
    public void updateStatus(int informationId) {
        contentDao.updateStatus(informationId);
    }

    @Override
    public List<CommentCountVO> selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem) {
        return contentDao.selectCommentNumByItem(shardingTotalCount, shardingItem);
    }
}
