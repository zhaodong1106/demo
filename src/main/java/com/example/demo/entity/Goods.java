package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.datetime.DateFormatter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018-08-21.
 */
public class Goods {
    private Long goodsId;
    @NotBlank
    private String goodsName;
    @Min(value = 10)
    @Max(value=99999999)
    private BigDecimal goodsPrice;
    private int goodsNum;
    private int status;
    private Integer CommentCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(Integer commentCount) {
        CommentCount = commentCount;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNum=" + goodsNum +
                ", status=" + status +
                ", CommentCount=" + CommentCount +
                ", updateTime=" + updateTime +
                '}';
    }

    public Goods() {
    }

    public Goods(Long goodsId, @NotBlank String goodsName, @Min(value = 10) @Max(value = 99999999) BigDecimal goodsPrice, int goodsNum, int status, Integer commentCount) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
        this.status = status;
        CommentCount = commentCount;
    }

    public static void main(String[] args){
        System.out.println(new BigDecimal("0.001").multiply(new BigDecimal("2")));
        int i=0;
        System.out.println("i++:"+(i++));
        int j=0;
        System.out.println("++j:"+(++j));
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:dd:ss");
        String format = LocalDateTime.now().plusDays(1).format(dtf);
        System.out.println(format);
        List<Goods> goodses=new ArrayList<>();
        goodses.add(new Goods(111l,"zhao",new BigDecimal("22.22"),222,1, 22));
        goodses.add(new Goods(222l,"dong",new BigDecimal("22.22"),222,1, 22));
        goodses.add(new Goods(333l,"xi",new BigDecimal("22.22"),222,1, 22));
        goodses.add(new Goods(444l,"nan",new BigDecimal("22.22"),222,1, 22));
        for(Goods goods:goodses){
            if("xi".equals(goods.getGoodsName())){
                System.out.println("333");
            }
        }
        Map<Long, Goods> goodsMap = goodses.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity()));
        goodsMap.entrySet().forEach(entry -> System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue()));
        String[] strings=new String[]{"a","b","a","c","e","c"};
        boolean b = Arrays.stream(strings).anyMatch("c"::equals);
        if (b){
            System.out.println("相当");
        }
        List<String>  stringList=Arrays.asList("a","b","a","c","e","c");
        stringList.stream().forEach(System.out::println);
    }

}
