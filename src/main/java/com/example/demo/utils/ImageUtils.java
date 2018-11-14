package com.example.demo.utils;

import com.example.demo.base.BaseResult;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.uuid.Generators;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Raytine on 2018/11/13.
 */
@Component
public class ImageUtils {
    private static final DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger log= LoggerFactory.getLogger(ImageUtils.class);
    @Value("${web.img-path}")
    private String imageFilePath;
    public  BaseResult uploadFileAndCreateThumbnail(MultipartFile imageFile, HttpServletRequest request) {
        if(imageFile == null ){
            return new BaseResult(false, "imageFile不能为空");
        }
        String mimeType = request.getServletContext().getMimeType(imageFile.getOriginalFilename());
        log.info("mimeType:{}",mimeType);
        if (imageFile.getSize() >= 10*1024*1024)
        {
            return new BaseResult(false, "文件不能大于10M");
        }
        String uuid = Generators.timeBasedGenerator().generate().toString();
//        UUID uuid1 = UUID.fromString(uuid);
//        long timeFromUUID = getTimeFromUUID(uuid1);
//        Date date=new Date(timeFromUUID);
//        System.out.println(date);
//        String uuid = Generators.timeBasedGenerator().generate().toString();
        String fileDirectory = LocalDate.now().format(dtf);

        //拼接后台文件名称
        String pathName = fileDirectory + File.separator + uuid+ "."
                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
//        String pathUrl = fileDirectory + "/" + uuid + "."
//                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        //构建保存文件路劲
        //2016-5-6 yangkang 修改上传路径为服务器上
//        String realPath = request.getServletContext().getRealPath("uploadPath");
        //获取服务器绝对路径 linux 服务器地址  获取当前使用的配置文件配置
        //String urlString=PropertiesUtil.getInstance().getSysPro("uploadPath");
        //拼接文件路劲
        String filePathName = imageFilePath + pathName;
        log.info("图片上传路径："+filePathName);
        //判断文件保存是否存在
        File file = new File(filePathName);
        if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            //创建文件
            file.getParentFile().mkdirs();
        }
        //折叠代码块
        /*InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = imageFile.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            //写出文件
            //2016-05-12 yangkang 改为增加缓存
//            IOUtils.copy(inputStream, fileOutputStream);
            byte[] buffer = new byte[2048];
            IOUtils.copyLarge(inputStream, fileOutputStream, buffer);
            buffer = null;

        } catch (IOException e) {
            filePathName = null;
            return new BaseResult(false, "操作失败", e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                filePathName = null;
                return new BaseResult(false, "操作失败", e.getMessage());
            }
        }*/
        try {
            imageFile.transferTo(file);
        } catch (IOException e) {
//            e.printStackTrace();
            return new BaseResult(false,"上传原图错误");
        }

        //        String fileId = FastDFSClient.uploadFile(file, filePathName);

        /**
         * 缩略图begin
         */

        //拼接后台文件名称
        String thumbnailPathName = fileDirectory + File.separator + uuid + "small."
                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
//        String thumbnailPathUrl = fileDirectory + "/" + uuid + "small."
//                + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        //added by yangkang 2016-3-30 去掉后缀中包含的.png字符串
        if(thumbnailPathName.contains(".png")){
            thumbnailPathName = thumbnailPathName.replace(".png", ".jpg");
        }
        long size = imageFile.getSize();
        double scale = 1.0d ;
        if(size >= 200*1024){
            if(size > 0){
                scale = (200*1024f) / size  ;
            }
        }


        //拼接文件路劲
        String thumbnailFilePathName = imageFilePath + thumbnailPathName;
        try {
            //added by chenshun 2016-3-22 注释掉之前长宽的方式，改用大小
//            Thumbnails.of(filePathName).size(width, height).toFile(thumbnailFilePathName);
            if(size < 200*1024){
                Thumbnails.of(filePathName).scale(1).outputFormat("jpg").toFile(thumbnailFilePathName);
            }else{
                Thumbnails.of(filePathName).size(960,960).outputQuality(scale).outputFormat("jpg").toFile(thumbnailFilePathName);
            }

        } catch (Exception e1) {
            return new BaseResult(false, "操作失败", e1.getMessage());
        }
        /**
         * 缩略图end
         */

        Map<String, Object> map = new HashMap();
        //原图地址
        map.put("originalUrl", pathName);
        //缩略图地址
        map.put("thumbnailUrl", thumbnailPathName);
        return new BaseResult(true,map, "操作成功");
    }
    private  final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;
    public  long getTimeFromUUID(UUID uuid) {
        return (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
    }
    public static void main(String[] args){
        String uuid="f0b6af23-e7d3-11e8-be46-d1467f366da8";
        UUID uuid1 = UUID.fromString(uuid);
        long timeFromUUID = new ImageUtils().getTimeFromUUID(uuid1);
        Instant instant = Instant.ofEpochSecond(timeFromUUID);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDateTime.format(dtf));
    }
}
