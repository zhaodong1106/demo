package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private Integer status;
    private Integer CommentCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    private LocalDateTime updateTime;

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        BloomFilter<Integer> bloomFilter=BloomFilter.create(Funnels.integerFunnel(),100000);
        for(int i=0;i<100;i++) {
            bloomFilter.put(i);
        }
        if(bloomFilter.mightContain(33333)){
            System.out.println(33333);
        }
        String newStr = substring("1", 1, 4);//长度不够前面加0 等于 0ABC 截取 1，4  所得 ABC
        System.out.println(newStr);

    }
    private static  String substringLast(String str, int subLen){
        int leng = 0;
        if(StringUtils.isNotBlank(str)){
            leng = str.length();
        }
        if(leng < subLen){
            return append(str, leng, subLen);
        }else{
            return str.substring(leng - subLen);
        }
    }
    /**
     * 截取 字符串，不够以0 补充
     *
     * @param str
     *            字符串
     * @param start
     *            截取开始位置(包含)
     * @param end
     *            截取结束位置(不包含)
     * @return
     */
    private static String substring(String str, int start, int end) {
        int len = 0;
        if (StringUtils.isNotBlank(str)) {
            len = str.length();
        }

        if (len < end) {// 长度不够，补充 0
            return append(str, len, end).substring(start, end);
        } else {
            return str.substring(start, end);
        }

    }

    private static String append(String str, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

}
