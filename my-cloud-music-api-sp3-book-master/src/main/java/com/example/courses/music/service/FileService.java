package com.example.courses.music.service;

import com.example.courses.music.config.Config;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.model.Resource;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.FileUtil;
import com.example.courses.music.util.IDUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件服务
 */
@Service
public class FileService {
    /**
     * 保存文件
     * @param data
     * @param flavor
     * @return
     */
    public Resource create(MultipartFile data, String flavor) {
        try {
            //随机文件名称
            //当然还可以计算文件内容md5，避免重复上传
            String name=IDUtil.getUUID();

            //后缀
            String suffix=FileUtil.suffix(data.getOriginalFilename());
            String targetName = String.format("%s%s%s", name,flavor, suffix);
            File targetFile = new File(Config.DIR_UPLOAD,targetName );

            //保存文件
            FileUtils.copyInputStreamToFile(data.getInputStream(), targetFile);

            //最后返回的路径，为uploads/targetName
            //客户端只需要拼接前面的地址就可以访问了
            return new Resource(String.format("uploads/%s",targetName));
        }
        catch (IOException e) {
            throw new CommonException(Constant.ERROR_UPLOAD, Constant.ERROR_UPLOAD_MESSAGE);
        }
    }

    public File loadAsFile(String data) {
        File file = new File(Config.DIR_UPLOAD,data);
        return file;
    }
}
