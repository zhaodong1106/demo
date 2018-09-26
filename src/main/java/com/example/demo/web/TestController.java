package com.example.demo.web;

import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Goods;
import com.example.demo.utils.FtpUtil;
import com.example.demo.utils.PictureService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.*;
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
        return goodsDao.selectAll();
    }
    @RequestMapping("/queryGoods")
    @ResponseBody
    public Goods queryGoods(Long id){
        return goodsDao.queryGoods(id);
    }
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
        int count = goodsDao.insertSelective(goods);
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
