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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
