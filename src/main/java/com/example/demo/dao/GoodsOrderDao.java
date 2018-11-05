package com.example.demo.dao;

import com.example.demo.entity.GoodsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by T011689 on 2018/10/9.
 */
public interface GoodsOrderDao {
    public void insert(@Param("studentId") Long studentId,@Param("goodsId") Long goodsId,@Param("goodsNum") int goodsNum);
    List<GoodsOrder> selectByStudentId(@Param("studentId") Integer studentId);
}
