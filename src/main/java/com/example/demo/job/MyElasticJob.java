package com.example.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by T011689 on 2018/9/20.
 */
public class MyElasticJob implements DataflowJob<Goods> {
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public List<Goods> fetchData(ShardingContext shardingContext) {
        List<Goods> goodsList=null;
        Map<String,Object> map=new HashMap<>();
        map.put("shardingTotalCount",shardingContext.getShardingTotalCount());
        switch(shardingContext.getShardingItem()){
            case 0:
                map.put("shardingItem",0);
                goodsList= goodsDao.selectAllByItem(map);
                break;
            case 1:
                map.put("shardingItem",1);
                goodsList = goodsDao.selectAllByItem(map);
                break;

        }
        return goodsList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Goods> list) {
        for(Goods goods:list){
            System.out.println(goods.getGoodsName());
        }
        goodsDao.updateStatus(list);

    }
}
