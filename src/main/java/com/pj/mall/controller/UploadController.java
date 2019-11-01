package com.pj.mall.controller;

import com.pj.mall.common.PageResult;
import com.pj.mall.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-03-22 11:03
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/image")
    public PageResult<String> uploadImage(@RequestParam("file") MultipartFile file){
        String url = uploadService.uploadImage(file);
        return new PageResult<String>(url);
    }

    @PostMapping("/image/editor")
    public Map<String,Object> editorUploadImage(@RequestParam("file") MultipartFile file){
        Map<String,Object> map = new HashMap<>();
        try{
            String url = uploadService.uploadImage(file);
            map.put("code",0);
            map.put("msg","OK");
            Map<String,String> map2 = new HashMap<>();
            map2.put("src",url);
            map.put("data",map2);
        }catch (Exception exception){
            log.error("上传图片失败",exception);
            map.put("code",1);
            map.put("msg","上传图片失败！");
            map.put("data",null);
        }
        return map;
    }
}
