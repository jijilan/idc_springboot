package com.idc.modules.controller.system;


import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.QRCodeUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.common.exception.MyException;
import com.idc.common.utils.QiniuUtil;
import com.idc.common.utils.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther: jijl
 * @Date: Create in 2020/8/4
 * @Description: 文件上传
 **/
@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseController {

    /**
     * @Description 文件上传
     * @Date 2020/7/11 20:33
     * @Author jijl
     */
    @PostMapping("/agent/uploadAnyType")
    public ResultView fileUploadAnyType(@RequestParam("file") MultipartFile[] file) throws MyException {
        if(EmptyUtil.isEmpty(file)){
            return ResultView.error("上传文件为空!");
        }
        String resFileStr=UploadFileUtil.fileUploadAnyType(file, webResource.getFileUploadPath(),webResource.getFileReadUrl());
        if(EmptyUtil.isNotEmpty(resFileStr)){
            return ResultView.ok(resFileStr);
        }else{
            return ResultView.error("上传文件失败!");
        }

    }

    /**
     * @Description 文件上传
     * @Date 2020/7/11 20:33
     * @Author jijl
     */
    @PostMapping("/admin/upload")
    public ResultView adminFileUpload(@RequestParam("file") MultipartFile[] file,
                                      @RequestParam(value = "fileName", required = false, defaultValue = "images") String fileName) throws MyException {
        return ResultView.ok(UploadFileUtil.flowUpload(file, webResource.getStaticResourcePath(), fileName));
    }

    /**
     * 生成设备二维码
     */
    @GetMapping("/front/downloadQr")
    public void downloadQr(String equipmentNumber, HttpServletResponse response) {
        QRCodeUtil createQrcode = new QRCodeUtil();
        //生成二维码
        try {
            createQrcode.getQrcode(webResource.getQrCodePath() + equipmentNumber, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三方七牛上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/front/fileUploadQiniu")
    public ResultView fileUploadQiniu(@RequestParam(value = "file") MultipartFile multipartFile) {
        try {
            byte[] bytes = multipartFile.getBytes();
            return ResultView.ok(QiniuUtil.upLoadImage(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultView.error(ResultEnum.CODE_2);
    }
}
