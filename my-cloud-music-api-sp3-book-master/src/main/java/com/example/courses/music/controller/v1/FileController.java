package com.example.courses.music.controller.v1;

import com.example.courses.music.exception.ArgumentException;
import com.example.courses.music.exception.NotFoundException;
import com.example.courses.music.model.Resource;
import com.example.courses.music.service.FileService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 文件上传控制器
 */
@Controller
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 根据文件名返回文件
     *
     * 也可以使用addResourceHandlers方法，把文件上传目录映射出去
     * @param filename
     * @return
     */
    @GetMapping("/files/{filename}")
    @ResponseBody
    public Object show(@PathVariable String filename) {

        File result = fileService.loadAsFile(filename);

        if (!result.exists()) {
            throw new NotFoundException();
        }

        //获取文件类型
        String contentType = null;
        try {
            Path path = Paths.get(result.getAbsolutePath());
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            contentType="application/octet-stream";
        }

        return ResponseEntity.ok()
                //下载
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + result.getName() + "\"")

                //设置文件类型，浏览器能预览的文件就会直接预览
                .contentType(MediaType.parseMediaType(contentType))
                .body(new FileSystemResource(result));
    }

    /**
     * 表单方式上传文件
     * @param file 文件对象，表单字段名称为data，类似为file
     * @param flavor 渠道，例如：客户端会传递prod，dev，local等值，服务端方便保存到不同地方，这样后面好清理测试资源
     * @return
     */
    @PostMapping("/v1/files")
    @ResponseBody
    public Object create(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "local") String flavor) {
        if (file.isEmpty()) {
            throw new ArgumentException();
        }

//        System.out.println("文件类型："+data.getContentType());
//        System.out.println("原文件名："+data.getOriginalFilename());
//        System.out.println("是否为空："+data.isEmpty());
//        System.out.println("文件大小："+data.getSize());

        Resource result=fileService.create(file, flavor);

        return R.wrap(result);
    }

    /**
     * 表单方式上传多个文件
     * @param file 文件对象列表
     * @return
     */
    @PostMapping("/v1/files/multi")
    @ResponseBody
    public Object creates(MultipartFile[] file,@RequestParam(defaultValue = "local") String flavor) {
        if(file.length == 0){
            throw new ArgumentException();
        }

        ArrayList<Resource> results = new ArrayList<>();
        for (MultipartFile it : file) {
            if(it.isEmpty()){
                throw new ArgumentException();
            }

            results.add(fileService.create(it, flavor));
        }

        return R.wrap(results);
    }
}
