package com.example.demo.web;

import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Goods;
import com.example.demo.exception.DulplidateException;
import com.example.demo.jedis.RedisService;
import com.example.demo.service.CommentService;
import com.example.demo.utils.FtpUtil;
import com.example.demo.utils.PictureService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-08-21.
 */
@Controller
public class TestController {
    @RequestMapping("/testGoods")
    @ResponseBody
    public List<Goods> testGoods(){
        throw  new DulplidateException("重复了");
    }
    @RequestMapping("/testComments")
    @ResponseBody
    public List<Comment> testComments(){
        return commentService.select(2);
    }
    @Autowired
    private CommentService commentService;
    @RequestMapping("/queryGoods")
    @ResponseBody
    public Goods queryGoods(Long id){
        return goodsDao.queryGoods(id);
    }
    @RequestMapping("/testCookie")
    @ResponseBody
    public Object testCookie(@CookieValue(value = "userName",required = false) String userNameForCookie, @CookieValue(value = "userPassword",required = false) String userPasswordForCookie, HttpServletResponse response){

        if("zhaodong".equals(userNameForCookie)&&"11064957".equals(userPasswordForCookie)){
            return 200;
        }
        Cookie cookieValue=new Cookie("userName","zhaodong");
        cookieValue.setPath("/");
        cookieValue.setMaxAge(3600);
        Cookie cookieValue2=new Cookie("userPassword","11064957");
        cookieValue2.setPath("/");
        cookieValue2.setMaxAge(3600);
        response.addCookie(cookieValue);
        response.addCookie(cookieValue2);
        return 100;
    }
    @RequestMapping("/getIdGenerator")
    @ResponseBody
    public Object getIdGenerator(String date,String count){
        return redisService.evalId(date,count);
    }
    @Autowired
    private RedisService redisService;
    @RequestMapping("/goodsList")
    public ModelAndView goodsList(Integer pageNo){
        List<Goods> goodses=new ArrayList<>();
        int index=0;
        if(pageNo!=null &&pageNo>0) {
            index = pageNo * 15;
        }
        return new ModelAndView("goods/goodsList").addObject("name","zhaodong").addObject("goods",new Goods()).addObject("goodsList",goodsDao.selectAll()).addObject("index",index);
    }
    @RequestMapping("/add")
    public ModelAndView add(@Valid Goods goods,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("goods/goodsList");
        }
        try {
            int count = goodsDao.insertSelective(goods);
        }catch (DataAccessException e){
            e.printStackTrace();
            System.out.println("dsdasdad");
        }
        return new ModelAndView(new RedirectView("/goodsList"));
    }
    @RequestMapping("/add1")
    public ModelAndView add1(){
        for(int i=0;i<1000;i++) {
            Goods goods = new Goods();
            goods.setGoodsName("zhaoshaokangsdsdsd"+i);
            goods.setGoodsNum(i);
            goods.setGoodsPrice(new BigDecimal(i+10));
            int count = goodsDao.insertSelective(goods);
        }
        return new ModelAndView(new RedirectView("/goodsList"));
    }
    @RequestMapping("/testPoi")
    @ResponseBody
    public Object testPoi(){
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\test.docx"));
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            for(XWPFParagraph xwpfParagraph:xwpfDocument.getParagraphs()){
                System.out.println(xwpfParagraph.getParagraphText());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
    @RequestMapping("/testMultipartFile")
    @ResponseBody
    public Object testMultipartFile(MultipartFile file){
        Map<String, String> stringMap = pictureService.uploadPicture(file);
        return stringMap;
    }

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PictureService pictureService;
}
