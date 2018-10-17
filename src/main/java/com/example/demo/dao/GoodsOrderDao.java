package com.example.demo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by T011689 on 2018/10/9.
 */
public interface GoodsOrderDao {
    public void insert(@Param("studentId") Long studentId,@Param("goodsId") Long goodsId,@Param("goodsNum") int goodsNum);
}
