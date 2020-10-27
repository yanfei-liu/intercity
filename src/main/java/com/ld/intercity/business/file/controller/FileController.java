package com.ld.intercity.business.file.controller;

import com.ld.intercity.utils.ResponseResult2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: LD
 * @date:
 * @description:
 */
@Api(tags = "上传文件")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${upLoad.path}")
    private String path;

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult2 upload(@RequestBody MultipartFile multipartFile) throws Exception {
        return uploadFile(multipartFile, path);
    }

    private static ResponseResult2 uploadFile(MultipartFile multipartFile, String filePath) {
        if (multipartFile == null || multipartFile.getSize() <= 0) {
            return new ResponseResult2(0, "文件不能为空");
        }
        if (filePath == null || filePath.isEmpty()) {
            return new ResponseResult2(0, "文件上传地址不能为空");
        }
        String uuid = UUID.randomUUID().toString();
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            String name = multipartFile.getOriginalFilename();
//            boolean contains = name.contains("\\.");
            boolean contains = name != null && name.contains(".");
            if (!contains) {
                return new ResponseResult2(0, "非法文件名称");
            }
            int i = name.lastIndexOf(".");
            String substring = name.substring(i - 1);
            String fileName = uuid + substring;
            out = new FileOutputStream(filePath + fileName);
            out.write(multipartFile.getBytes());
            return new ResponseResult2(1, fileName);
        } catch (IOException e) {
            return new ResponseResult2(0, "上传文件错误");
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
