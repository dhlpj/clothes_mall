package com.pj.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jack
 * @create 2019-03-22 11:27
 */
public interface UploadService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file);
}
