package com.example.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.example.demo.dao.EmpDao;
import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by T011689 on 2018/9/20.
 */
public class MyElasticJobForEmp implements DataflowJob<Emp> {
    @Autowired
    private EmpDao empDao;
    @Override
    public List<Emp> fetchData(ShardingContext shardingContext) {
        List<Emp> empList=null;
        switch(shardingContext.getShardingItem()){
            case 0:
                empList= empDao.selectAllByItem(shardingContext.getShardingTotalCount(),0);
                break;
            case 1:
                empList = empDao.selectAllByItem(shardingContext.getShardingTotalCount(),1);
                break;

        }
        return empList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Emp> list) {
        for(Emp goods:list){
            System.out.println(goods.getEname());
        }
        empDao.updateStatus(list);

    }
}
