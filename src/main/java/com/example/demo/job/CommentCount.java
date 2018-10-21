//package com.example.demo.job;
//
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import com.example.demo.dao.GoodsDao;
//import com.example.demo.entity.Comment;
//import com.example.demo.entity.Goods;
//import com.example.demo.service.CommentService;
//import com.example.demo.vo.CommentCountVO;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by T011689 on 2018/10/17.
// */
//public class CommentCount implements DataflowJob<CommentCountVO> {
//    @Autowired
//    private GoodsDao goodsDao;
//    @Autowired
//    private CommentService commentService;
//    @Override
//    public List<CommentCountVO> fetchData(ShardingContext shardingContext) {
//        List<CommentCountVO> list=null;
//        Map<String,Object> map=new HashMap<>();
//        map.put("shardingTotalCount",shardingContext.getShardingTotalCount());
//        switch(shardingContext.getShardingItem()){
//            case 0:
//                map.put("shardingItem",0);
//                list= goodsDao.selectAllByItem(map);
//                break;
//            case 1:
//                map.put("shardingItem",1);
//                list = goodsDao.selectAllByItem(map);
//                break;
//
//        }
//        return goodsList;
//    }
//
//    @Override
//    public void processData(ShardingContext shardingContext, List<CommentCountVO> list) {
//        for(CommentCountVO commentCountVO:list){
//            commentService.
//        }
//        goodsDao.updateStatus(list);
//    }
//}
