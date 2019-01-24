package com.example.demo.dao;

import com.example.demo.entity.Goods;
import com.example.demo.vo.comment.CommentCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-08-21.
 */
public interface GoodsDao {
    List<Goods> selectAll();
    void updateStatus(List<Goods> goodses);
    List<Goods> selectAllByItem(Map map);
    int insertSelective(Goods goods);
    Goods queryGoods(Long id);
    void updateCommentCount(List<CommentCountVO> list);
    Integer updateGoods(@Param(value = "goodsName") String goodsName,@Param(value = "goodsId") Integer goodsId);
}
