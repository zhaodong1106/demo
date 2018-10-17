package com.example.demo.dao;

import com.example.demo.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by T011689 on 2018/10/11.
 */
public interface EmpDao {
    void updateStatus(List<Emp> lists);
    List<Emp> selectAllByItem(@Param("shardingTotalCount") int shardingTotalCount,@Param("shardingItem") int shardingItem);
}
