package com.example.demo.web;

import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Comment;
import com.example.demo.dao.GoodsOrderDao;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Greeting;
import com.example.demo.entity.HelloMessage;
import com.example.demo.exception.DulplidateException;
import com.example.demo.exception.OrderError;
import com.example.demo.jedis.ApiResponse;
import com.example.demo.jedis.RedisService;
import com.example.demo.service.CommentService;
import com.example.demo.utils.FtpUtil;
import com.example.demo.utils.PictureService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-08-21.
 */
@Controller
public class TestController {
    @RequestMapping("/testGoods")
    @ResponseBody
    public PageInfo<Goods> testGoods(@RequestParam(value = "pageNum",required = false,defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> goodses = goodsDao.selectAll();
        return new PageInfo<Goods>(goodses);
    }
    @RequestMapping("/testComments")
    @ResponseBody
    public List<Comment> testComments(@RequestParam(value = "informationId",required = false,defaultValue = "0") int informationId, Integer id, Comment comment){
        if(comment.getCreateDate()!=null) {
            System.out.println(comment.getCreateDate().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(comment.getCreateDate()));
        }
        return commentService.select(informationId,id);
    }
    @MessageMapping("/hello") //使用MessageMapping注解来标识所有发送到“/hello”这个destination的消息，都会被路由到这个方法进行处理.
    @SendTo("/topic/greetings") //使用SendTo注解来标识这个方法返回的结果，都会被发送到它指定的destination，“/topic/greetings”.
    //传入的参数HelloMessage为客户端发送过来的消息，是自动绑定的。
    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000); // 模拟处理延时
        return new Greeting("Hello, " +message.getName()+ "!"); //根据传入的信息，返回一个欢迎消息.
    }

    @RequestMapping("/insertComment")
    @ResponseBody
    public Object insertComment(@RequestParam(value = "parentId",required = false,defaultValue = "0") int parentId,String contentText){
        if(contentText==null||"".equals(contentText.trim())){
            return ApiResponse.ofSuccess("发送失败");
        }
        Comment comment=new Comment();
        comment.setContent(contentText);
        comment.setInformationId(3);
        comment.setUserId(335);
        if(parentId==0){
            comment.setParentId(0);
            comment.setFloor(1);
            commentService.insertComment(comment);
        }else {
            Comment comment1 = commentService.selectById(parentId);
            if(comment1==null){
                comment.setParentId(0);
                comment.setFloor(1);
                commentService.insertComment(comment);
            }else {
                comment.setParentId(parentId);
                comment.setFloor(comment1.getFloor() + 1);
                commentService.insertComment(comment);
            }
        }
        return ApiResponse.ofSuccess("success");

    }
    @Autowired
    private CommentService commentService;
    @RequestMapping("/queryGoods")
    public Goods queryGoods(Long id){
        throw new RuntimeException("orderError,please Try it again");
    }
    @RequestMapping("/insertGoodsOrder")
    @ResponseBody
    public Object insertGoodsOrder(){
        for(int i=21;i<500;i++) {
            goodsOrderDao.insert(Long.valueOf(i),Long.valueOf(i+1),i+2);
        }
        return "success";
    }
    @Autowired
    private GoodsOrderDao goodsOrderDao;
    @RequestMapping("/")
    public ModelAndView index(Long id){
        return new ModelAndView("index").addObject("goods",new Goods());
    }
    @RequestMapping("/testCookie")
    @ResponseBody
    public Object testCookie(@CookieValue(value = "userName",required = false) String userNameForCookie, @CookieValue(value = "userPassword",required = false) String userPasswordForCookie, HttpServletResponse response){

        if("zhaodong".equals(userNameForCookie)&&"11064957".equals(userPasswordForCookie)){
            return ApiResponse.ofSuccess(200);
        }
        Cookie cookieValue=new Cookie("userName","zhaodong");
        cookieValue.setPath("/");
        cookieValue.setMaxAge(3600);
        Cookie cookieValue2=new Cookie("userPassword","11064957");
        cookieValue2.setPath("/");
        cookieValue2.setMaxAge(3600);
        response.addCookie(cookieValue);
        response.addCookie(cookieValue2);
        return ApiResponse.ofSuccess(1);
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
        if(pageNo==null){
            pageNo=15;
        }
        PageHelper.startPage(1,pageNo);
        List<Goods> goodses = goodsDao.selectAll();
        int index=1;
        return new ModelAndView("goods/goodsList").addObject("str",123456).addObject("name","zhaodong").addObject("goods",new Goods()).addObject("goodsList",goodses).addObject("index",index);
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
