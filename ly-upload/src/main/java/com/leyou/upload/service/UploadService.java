package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UploadService
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/4 22:49
 * @Version 1.0
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties uploadProperties;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    public String uploadFile(MultipartFile multipartFile) {
        // 检验文件类型
        String contentType = multipartFile.getContentType();
        if (!uploadProperties.getAllowFileType().contains(contentType) || multipartFile.getOriginalFilename() == null) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        try {
            // 检验文件内容
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            // 准备文件存储地址
            if (image == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
//            // 保存文件到本地
//            File destFile = new File("H:\\IdeaProjects\\leyou\\ly-upload\\src\\main\\resources\\image", multipartFile.getOriginalFilename());
//            multipartFile.transferTo(destFile);
            // 保存文件到FDFS
            String extension = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), extension, null);
            // 返回文件地址
            return uploadProperties.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("[文件上传] 上传文件失败");
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

    }
}
