package com.example.demo.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by T011689 on 2018/9/17.
 */
@Service
public class PictureService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Value("${fastDfs.host}")
    private String hostIp;
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    public Map<String,String> uploadPicture(MultipartFile file){
        Map<String,String> map=new HashedMap();
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String filePath=storePath.getFullPath();
            String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getFullPath());
            map.put("data",filePath);
            map.put("thumbImagePath",thumbImagePath);
            map.put("state","0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
