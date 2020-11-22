package com.idc.common.utils;

import com.idc.common.enums.ResultEnum;
import com.idc.common.exception.MyException;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 文件上传
 */
@Slf4j
public class UploadFileUtil {


    public static final String PATH_IMG = "uploads/";

    public static String base64Upload(String attach, String path) throws MyException {
        log.info("path================{}", path);
        if (attach == null) {
            throw new MyException(ResultEnum.CODE_5);
        }
        //检查目录是否存在
        checkDirectories(path);

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(attach);
            //调整异常数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String UUID = java.util.UUID.randomUUID().toString();
            //生成jpg图片
            OutputStream out = new FileOutputStream(path + "/" + UUID + ".jpg");
            out.write(b);
            out.flush();
            out.close();
            return path + "/" + UUID + ".jpg";
        } catch (Exception e) {
            throw new MyException(ResultEnum.CODE_6);
        }
    }

    public static String flowUpload(MultipartFile[] attach, String portraitPath, String path) throws MyException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        if (attach != null && attach.length > 0) {
            // 创建文件对象，此文件对象用于接收用户上传的文件流
            portraitPath = portraitPath + PATH_IMG + path + "/" + date + "/";
            String uploadPath = "";
            checkDirectories(portraitPath);
            for (int i = 0; i < attach.length; i++) {
                if (!attach[i].isEmpty()) {
                    // 获取原文件名
                    String oldFileName = attach[i].getOriginalFilename();
                    log.info("获取原文件名：{}", oldFileName);
                    // 获取原文件名的后缀
                    String prefix = FilenameUtils.getExtension(oldFileName);
                    int filesize = 50000000;
                    // 上传大小不得超过 500k
                    log.info("第" + (i + 1) + "张图片的大小:" + attach[i].getSize());
                    if (attach[i].getSize() > filesize) {
                        log.info("上传大小不能超过5M");
                        throw new MyException(ResultEnum.CODE_7);
                    } else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                            || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")
                            || prefix.equalsIgnoreCase("webp")) {
                        // 新的照片名称，毫秒数加随机数，确保不能重复
                        String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000, 5000000)
                                + ".jpg";
                        // 保存
                        FileOutputStream out = null;
                        try {
                            // 把MultipartFile中的文件流数据的数据输出至目标文件中
                            out = new FileOutputStream(portraitPath + fileName);
                            out.write(attach[i].getBytes());
                            out.flush();
                            out.close();
                            if (i == 0) {
                                uploadPath += PATH_IMG + path + "/" + date + "/" + fileName;
                                log.info(portraitPath + "图片路径");
                            } else {
                                uploadPath += "," + PATH_IMG + path + "/" + date + "/" + fileName;
                            }
                        } catch (Exception e) {
                            log.info("上传失败！");
                            e.printStackTrace();
                            throw new MyException(ResultEnum.CODE_6);
                        }
                    } else {
                        log.info("上传图片格式不正确");
                        throw new MyException(ResultEnum.CODE_8);
                    }
                } else {
                    continue;
                }
            }
            return uploadPath;
        }
        return null;
    }

    /**
     * 上传任意类型文件接口
     * @param attach 文件
     * @param portraitPath 上传服务器路径
     * @param readUrl 访问地址
     * @return
     * @throws MyException
     */
    public static String fileUploadAnyType(MultipartFile[] attach, String portraitPath,String readUrl) throws MyException {
        String Now_Date = DateUtils.getCurrentDate("yyyy/MM/dd");
        String uploadPath = "";
        String fileName="";
        // 拼接文件保存路径
        String pathName=portraitPath+"/"+Now_Date+"/";
        // 若文件夹不存在则创建文件夹
        checkDirectories(pathName);
        // 文件列表是否为空
        if (attach != null && attach.length > 0) {
            for (int i = 0; i < attach.length; i++) {
                if (!attach[i].isEmpty()) {
                    // 获取原文件名
                    String oldFileName = attach[i].getOriginalFilename();
                    // 获取原文件名的后缀
                    String prefix = FilenameUtils.getExtension(oldFileName);
                    // 拼接生成的文件名称
                    fileName=IdentityUtil.uuid()+prefix;
                    int filesize = 5*1024*1024;
                    if (attach[i].getSize() > filesize) {
                        log.info("上传大小不能超过5M");
                        throw new MyException(ResultEnum.CODE_7);
                    }else{
                        // 保存
                        FileOutputStream out = null;
                        try {
                            // 把MultipartFile中的文件流数据的数据输出至目标文件中
                            out = new FileOutputStream(pathName+fileName);
                            out.write(attach[i].getBytes());
                            out.flush();
                            out.close();
                            if (i == 0) {
                                uploadPath += readUrl+Now_Date+"/"+fileName;
                            } else {
                                uploadPath += ";" + readUrl+Now_Date+"/"+fileName;
                            }
                            log.info("图片路径:"+uploadPath);
                        } catch (Exception e) {
                            log.info("上传失败！");
                            e.printStackTrace();
                            throw new MyException(ResultEnum.CODE_6);
                        }
                    }
                }
            }
        }
        return uploadPath;
    }

    public static void checkDirectories(String path) {
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }
}
