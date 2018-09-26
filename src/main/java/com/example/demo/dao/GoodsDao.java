package com.example.demo.dao;

import com.example.demo.entity.Goods;

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
}