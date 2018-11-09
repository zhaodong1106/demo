package com.example.demo.service.impl;

import com.example.demo.dao.ContentDao;
import com.example.demo.dao.ContentNewDao;
import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentNew;
import com.example.demo.service.CommentNewService;
import com.example.demo.service.CommentService;
import com.example.demo.vo.CommentCountVO;
import com.example.demo.vo.CommentNewVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raytine on 2018/10/16.
 */
@Service
public class CommentNewServiceImpl implements CommentNewService {
    @Autowired
    private ContentNewDao contentNewDao;
    @Override
    public List<CommentNewVo> select(int informationId, Integer parentId) {
        List<CommentNewVo> comments1 = contentNewDao.selectByInformationId(informationId,null);
        for (CommentNewVo commentNew:comments1){
            List<CommentNewVo> commentNews = contentNewDao.selectByInformationId(informationId, commentNew.getId());
            if(commentNews.size()>0){
                commentNew.setCommentNews(commentNews);
            }
        }
        return comments1;
    }

    @Override
    public int insertComment(CommentNew comment) {
        return contentNewDao.insertComment(comment);
    }

    @Override
    public CommentNew selectById(int id) {
        return contentNewDao.selectById(id);
    }

    @Override
    public void updateStatus(int informationId) {
        contentNewDao.updateStatus(informationId);
    }

    @Override
    public List<CommentCountVO> selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem) {
        return contentNewDao.selectCommentNumByItem(shardingTotalCount, shardingItem);
    }
}
