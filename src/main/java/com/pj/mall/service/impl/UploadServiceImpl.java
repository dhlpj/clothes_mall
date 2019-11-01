package com.pj.mall.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pj.mall.config.UploadProperties;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Jack
 * @create 2019-03-22 11:26
 */
@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService{
    @Autowired
    private UploadProperties uploadProperties;//使用@EnableConfigurationProperties所以可以注入，否则需要使用@Component
    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadImage(MultipartFile file) {
        //校验文件的类型
        String contentType = file.getContentType();
        if(!uploadProperties.getAllowTypes().contains(contentType)){
            throw new MallException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){//文件不是image类型
                throw new MallException(ExceptionEnum.INVALID_FILE_TYPE);
            }
        } catch (IOException e) {
            log.error("校验文件出现异常！",e);
            throw new MallException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
        //进行上传
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            return uploadProperties.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            log.error("上传文件出现异常！",e);
            throw new MallException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
