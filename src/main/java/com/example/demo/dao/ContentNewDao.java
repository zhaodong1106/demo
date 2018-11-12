package com.example.demo.dao;

import com.example.demo.entity.CommentNew;
import com.example.demo.vo.comment.CommentCountVO;
import com.example.demo.vo.comment.CommentNewVo;
import com.example.demo.vo.comment.CommentNewVo2;
import com.example.demo.vo.comment.CommentNewVoPrimary;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Raytine on 2018/10/16.
 */
public interface ContentNewDao {
    CommentNew selectById(Integer id);
    List<CommentNewVoPrimary> selectByInformationIdPrimary(@Param("informationId") int informationId);
    List<CommentNewVo2> selectByInformationId2(@Param("informationId") int informationId, @Param("parentId") Integer parentId);
    List<CommentNewVo> selectByInformationId(@Param("informationId") int informationId, @Param("parentId") Integer parentId);
    @MapKey("id")
    Map<String,CommentNewVo> selectByInformationIdForMap(@Param("informationId") int informationId, @Param("parentId") Integer parentId);
    @MapKey("id")
    Map<String,CommentNewVo> selectByIdForMap(List<Integer> ids);
    int  insertComment(CommentNew comment);
    List<CommentCountVO>selectCommentNumByItem(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem);
    void updateStatus(@Param("informationId") int informationId);
}
