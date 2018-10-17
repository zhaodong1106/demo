package com.example.demo.entity;

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
import java.util.Date;

/**
 * Created by Administrator on 2018-08-21.
 */
public class Goods {
    private String goodsId;
    @NotBlank
    private String goodsName;
    @Min(value = 10)
    @Max(value=99999999)
    private BigDecimal goodsPrice;
    private int goodsNum;
    private int status;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
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

    public static void main(String[] args){
        System.out.println(new BigDecimal("0.001").multiply(new BigDecimal("2")));
        int i=0;
        System.out.println("i++:"+(i++));
        int j=0;
        System.out.println("++j:"+(++j));
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:dd:ss");
        String format = LocalDateTime.now().plusDays(1).format(dtf);
        System.out.println(format);
    }
}
