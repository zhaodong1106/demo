package com.example.demo.web;

import com.example.demo.base.BaseResult;
import com.example.demo.dao.ContentNewDao;
import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.*;
import com.example.demo.dao.GoodsOrderDao;
import com.example.demo.enums.Status;
import com.example.demo.exception.DulplidateException;
import com.example.demo.exception.OrderError;
import com.example.demo.jedis.ApiResponse;
import com.example.demo.jedis.RedisService;
import com.example.demo.service.CommentNewService;
import com.example.demo.service.CommentService;
import com.example.demo.utils.FtpUtil;
import com.example.demo.utils.ImageUtils;
import com.example.demo.utils.MongoAutoidUtil;
import com.example.demo.utils.PictureService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.demo.jedis.ApiResponse.*;

/**
 * Created by Administrator on 2018-08-21.
 */
@Controller
@Slf4j
public class TestController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoAutoidUtil mongoAutoidUtil;

    @RequestMapping("/insetMongo")
    @ResponseBody
    public Object insetMongo(){
        List<User> all = mongoTemplate.findAll(User.class);
        return all;
//        User user=new User();
//        user.setId("5c53c16b641b51807c4d962d");
//        user.setName("xixiixdddsdasd");
//        Query query=Query.query(Criteria.where("id").is(user.getId()));
//        Update update=Update.update("name","zhaodong");
//        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
//        return updateResult;
    }
    @RequestMapping(value = "/api/testGoods",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试商品")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "pageNum",value = "页数",required = false,defaultValue = "0",dataType = "int"),
                        @ApiImplicitParam(paramType = "query",name = "pageSize",value = "每页大小",required = false,defaultValue = "15",dataType = "int")})
    public PageInfo<Goods> testGoods(HttpServletRequest request,@RequestParam(value = "pageNum",required = false,defaultValue = "0")int pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        String sessionId = (String) WebUtils.getSessionAttribute(request,"name");
        System.out.println("sessionId:"+sessionId);
        log.info("");
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> goodses = goodsDao.selectAll();
        return new PageInfo<Goods>(goodses);
    }
    @RequestMapping(value = "/api/selectByStudentId",method = RequestMethod.GET)
    @ResponseBody
    public Object selectByStudentId(@RequestParam(value="studentId",required = false,defaultValue = "133") int studentId){
        return goodsOrderDao.selectByStudentId(studentId);
    }
    @RequestMapping("/testHeapLeak")
    @ResponseBody
    public Object testHeapLeak(){
        int i=0;
        while (true){
            emps.add(new Emp(i++, UUID.randomUUID().toString()));
        }
    }
    private static final String[] urls=new String[]{"dasd","Dasd"};
    private List<Emp> emps=new ArrayList<>();

    @RequestMapping("/testBoolFilter")
    @ResponseBody
    public Object  testBoolFilter(){
        return null;
    }

    @Autowired
    private GoodsOrderDao goodsOrderDao;
    @RequestMapping(value = "/api/testComments",method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> testComments(@RequestParam(value = "informationId",required = false,defaultValue = "0") int informationId, Integer id, Comment comment){
        if(comment.getCreateDate()!=null) {
            System.out.println(comment.getCreateDate().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(comment.getCreateDate()));
        }
        return commentService.select(informationId,id);
    }
    @RequestMapping(value = "/api/testCommentsNew",method = RequestMethod.GET)
    @ResponseBody
    public Object testCommentsNew(@RequestParam(value = "informationId",required = false,defaultValue = "0") int informationId, Integer parentId, Comment comment){
        if(comment.getCreateDate()!=null) {
            System.out.println(comment.getCreateDate().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(comment.getCreateDate()));
        }
        return commentNewService.select2(informationId);
    }
    @RequestMapping(value = "/api/testCommentsMap",method = RequestMethod.GET)
    @ResponseBody
    public Object testCommentsMap(@RequestParam(value = "informationId",required = false,defaultValue = "0") int informationId, Integer parentId, Comment comment){
        if(comment.getCreateDate()!=null) {
            System.out.println(comment.getCreateDate().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(comment.getCreateDate()));
        }
        return contentNewDao.selectByInformationIdForMap(informationId,parentId);
    }
    @Autowired
    private CommentNewService commentNewService;
    @Autowired
    private ContentNewDao contentNewDao;
    @MessageMapping("/hello") //使用MessageMapping注解来标识所有发送到“/hello”这个destination的消息，都会被路由到这个方法进行处理.
    @SendTo("/topic/greetings") //使用SendTo注解来标识这个方法返回的结果，都会被发送到它指定的destination，“/topic/greetings”.
    //传入的参数HelloMessage为客户端发送过来的消息，是自动绑定的。
    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000); // 模拟处理延时
        return new Greeting("Hello, " +message.getName()+ "!"); //根据传入的信息，返回一个欢迎消息.
    }
//    @Scheduled(fixedRate=3000)
//    public void sendJvmInfo(){
//        //可用进程数
//        int availa = Runtime.getRuntime().availableProcessors();
//        //空闲内存
//        long free = Runtime.getRuntime().freeMemory();
//        //最大内存数
//        long max = Runtime.getRuntime().maxMemory();
//
//        String content = String.format("可用进程数:%s 空闲内存:%s 最大内存数:%s",availa,free,max);
//        System.out.println("信息: "+content);
//        Greeting greeting=new Greeting(content);
//        template.convertAndSend("/monitor/jvm/info",greeting);
//    }
    @Autowired
    private SimpMessagingTemplate template;
    @RequestMapping(value = "/api/insertComment",method = RequestMethod.GET)
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
    @RequestMapping(value = "/api/getSession",method = RequestMethod.GET)
    @ResponseBody
    public Object queryGoods(HttpServletRequest request){
        WebUtils.setSessionAttribute(request,"name","zhao");
        return "sucess";
    }
    @RequestMapping(value = "/api/insertGoodsOrder",method = RequestMethod.GET)
    @ResponseBody
    public Object insertGoodsOrder(){
        for(int i=21;i<500;i++) {
            goodsOrderDao.insert(Long.valueOf(i),Long.valueOf(i+1),i+2);
        }
        return "success";
    }
    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request,HttpServletResponse response,Long id){
//        CookieGenerator cookieGenerator=new CookieGenerator();
//        cookieGenerator.setCookieName("name");
//        cookieGenerator.setCookieMaxAge(3600);
//        cookieGenerator.addCookie(response,"zhaodong");
        Cookie nameCookie = WebUtils.getCookie(request, "name");
        Cookie passwordCookie = WebUtils.getCookie(request, "password");
        if(nameCookie!=null&&passwordCookie!=null){
            String name=nameCookie.getValue();
            String password=passwordCookie.getValue();
            WebUtils.setSessionAttribute(request,"name",name);
            WebUtils.setSessionAttribute(request,"password",password);

        }
//        WebUtils.setSessionAttribute(request,"name","zhaodong");
        return new ModelAndView("index").addObject("goods",new Goods());
    }
    @RequestMapping(value = "/api/testResourceUtils",method = RequestMethod.GET)
    @ResponseBody
    public Object testResourceUtils(){
        try {
//            File file = ResourceUtils.getFile("classpath:luaScripts/xianliu.lua");
            File file = ResourceUtils.getFile("file:C:\\Users\\T011689\\logout.log");
            String copyToString = FileCopyUtils.copyToString(new FileReader(file));
            return copyToString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
//    @ResponseBody
//    @CrossOrigin
//    public String sseEmitter() throws JsonProcessingException {
//        Map map=new HashMap();
//        map.put("count",new Random().nextInt(100));
//        String s = objectMapper.writeValueAsString(map);
//        System.out.println("End Async processing.");
//        return s + "\n\n";
//    }
    @Autowired
    private ObjectMapper objectMapper;
    @RequestMapping(value = "/api/testCookie",method = RequestMethod.GET)
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
    @RequestMapping(value = "/api/getIdGenerator",method = RequestMethod.GET)
    @ResponseBody
    public Object getIdGenerator(String date,String count){
        return redisService.evalId(date,count);
    }
    @Autowired
    private RedisService redisService;
    @RequestMapping("/goodsList")
    public ModelAndView goodsList(HttpServletRequest request,@RequestParam(value = "pageNo",required = false,defaultValue = "1") int pageNo
    ,@RequestParam(value="pageSize",required = false,defaultValue = "2") int pageSize
    ){
        log.debug("进入goodsLsit");
        String sessionId = WebUtils.getSessionId(request);
        System.out.println("sessionId:"+sessionId);
        PageHelper.startPage(pageNo,pageSize);
        List<Goods> goodses = goodsDao.selectAll();
        int index=1;
        return new ModelAndView("goods/goodsList").addObject("str",123456).addObject("name","zhaodong").addObject("goodsList",goodses).addObject("index",index);
    }
    @RequestMapping("/updateOracle")
    @ResponseBody
    public Object updateOracle(String goodsName,Integer goodsId){
        Integer count = goodsDao.updateGoods(goodsName, goodsId);
        return  count;
    }
    @RequestMapping("/addOracle")
    @ResponseBody
    public Object addOracle(){
        int i=1;
        Goods goods=new Goods();
        goods.setGoodsName("dadad"+i);
        goods.setGoodsPrice(new BigDecimal("13213.22"));
        goods.setGoodsNum(222);
        goods.setUpdateTime(LocalDateTime.now());
        goodsDao.insertSelective(goods);
        return 1;
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
    @RequestMapping("/testZset")
    @ResponseBody
    public Object testZset(){
            return null;
    }
    @RequestMapping(value = "/api/testPoi",method = RequestMethod.GET)
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
    @RequestMapping("/testJstack")
    @ResponseBody
    public Object testJstack(){
        int i=3444;
        while(i>1){
            i=2222;
        }
        return "success";
    }


    @RequestMapping(value = "/api/testMultipartFile",method = RequestMethod.POST)
    @ResponseBody
    public Object testMultipartFile(MultipartFile file,HttpServletRequest request){

        BaseResult baseResult = imageUtils.uploadFileAndCreateThumbnail(file,request);
//        Map<String, String> stringMap = pictureService.uploadPicture(file);
        return baseResult;
    }
    @RequestMapping("/testNotLogin")
    @ResponseBody
    public ApiResponse testNotLogin(){
        return ApiResponse.ofStatus(Status.NOT_LOGIN);
    }
    @RequestMapping(value = "/testApiresponse",method = RequestMethod.GET)
    @ResponseBody
    public Object testApiresponse(){
        ApiResponse apiResponse = ofSuccess("dsadas");
//        Map<String, String> stringMap = pictureService.uploadPicture(file);
        return apiResponse;
    }
    @RequestMapping(value = "/api/testSend",method = RequestMethod.GET)
    @ResponseBody
    public Object testSend() throws InterruptedException {
        long start=System.currentTimeMillis();
        rabbitTemplate.convertAndSend("topicExchange", "key.1","dasdasdddddddddddddddddddddddddddddddddddddddddd");
        long end=System.currentTimeMillis();
        System.out.println("程序运行时间： " + (end - start)/1000 + "s");
        ApiResponse apiResponse = ofSuccess("dsadas");
//        Map<String, String> stringMap = pictureService.uploadPicture(file);
        return apiResponse;
    }
    @RequestMapping(value = "/api/testSendDTL",method = RequestMethod.GET)
    @ResponseBody
    public Object testSendDTL() throws InterruptedException {
        long start=System.currentTimeMillis();
        rabbitTemplate.convertAndSend("orderDelayExchange", "orderDelayExchange.key","dasdasdddddddddddddddddddddddddddddddddddddddddd");
        long end=System.currentTimeMillis();
        System.out.println("程序运行时间： " + (end - start)/1000 + "s");
        ApiResponse apiResponse = ofSuccess("dsadas");
//        Map<String, String> stringMap = pictureService.uploadPicture(file);
        return apiResponse;
    }
    private static final DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    @RequestMapping("/api/testSendDTL2")
//    @ResponseBody
//    public Object testSendDTL2() throws InterruptedException {
////        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("发送时间：" + LocalDateTime.now().format(dtf));
//        String msg="dasddddddddddddd";
//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.QUEUE_NAME, msg, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setHeader("x-delay", 18000);
//                return message;
//            }
//        });
//        ApiResponse apiResponse = ApiResponse.ofSuccess("dsadas");
////        Map<String, String> stringMap = pictureService.uploadPicture(file);
//        if(params.containsKey("sendCount")) {
//            params.put("sendCount", params.get("sendCount") + 1);
//        }else {
//            params.put("sendCount",1);
//        }
//        System.out.println("sendCount:"+params.get("sendCount"));
//        return apiResponse;
//    }
    @RequestMapping("/testStatic")
    @ResponseBody
    public Object testStatic(){
        count++;
        System.out.println(count);
        return count;
    }
    private   Integer count=1;
    private static final ConcurrentHashMap<String,Integer>  params=new ConcurrentHashMap<>();


    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PictureService pictureService;
}
