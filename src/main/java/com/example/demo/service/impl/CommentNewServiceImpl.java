package com.example.demo.service.impl;

import com.example.demo.dao.ContentDao;
import com.example.demo.dao.ContentNewDao;
import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentNew;
import com.example.demo.service.CommentNewService;
import com.example.demo.service.CommentService;
import com.example.demo.vo.CommentCountVO;
import com.example.demo.vo.CommentNewVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Raytine on 2018/10/16.
 */
@Service
public class CommentNewServiceImpl implements CommentNewService {
    @Autowired
    private ContentNewDao contentNewDao;
    @Override
    public  Map<String,Object> select(int informationId, Integer parentId) {
        Map<String,Object> params=new HashedMap();
        List<CommentNewVo> comments1 = contentNewDao.selectByInformationId(informationId,null);
        List<Integer> replyIds=new ArrayList<>();
        for (CommentNewVo commentNew:comments1){
            List<CommentNewVo> commentNews = contentNewDao.selectByInformationId(informationId, commentNew.getId());
            if(commentNews.size()>0){
                commentNew.setCommentNews(commentNews);
            }
            for(CommentNewVo commentNewVo:commentNews){
                if(commentNewVo.getReplyId()!=0){
                    replyIds.add(commentNewVo.getReplyId());
                }
            }
        }
        params.put("datas",comments1);
        if(replyIds.size()>0){
            Map<String, CommentNewVo> commentNewVoMap = contentNewDao.selectByIdForMap(replyIds);
            params.put("commentNewVoMap",commentNewVoMap);
        }
        return params;
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
