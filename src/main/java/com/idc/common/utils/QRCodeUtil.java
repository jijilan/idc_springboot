package com.idc.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.zip.ZipOutputStream;

/**
 * @Author: jijl
 * @Description: 二维码生成
 * @Date: 2020/8/3 16:51
 **/
@Slf4j
public class QRCodeUtil {
    public static final String PATH_IMG = "qrcodes/";
    public static final String UPLOADS = "uploads/";


    /**
     * 生成二维码方法
     *
     * @param url  访问链接
     * @param resp response对象
     * @throws Exception 抛出异常
     */
    public void getQrcode(String url, HttpServletResponse resp) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = resp.getOutputStream();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bm = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(bm, "png", stream);
        } catch (WriterException e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 生成二维码方法(保存到服务器)
     *
     * @param qrcodePath
     * @param url
     * @param context
     * @param type       0 门禁 1 扭蛋机
     * @throws Exception
     */
    public static String saveQrcode(String qrcodePath, String url, String context, int type, String equipmentNo) throws Exception {
        String imgPath = UPLOADS + PATH_IMG + type + "/";
        //扭蛋机需要根据文件夹分类
        if (type == 1) {
            imgPath += "/" + equipmentNo;
        }
        qrcodePath = qrcodePath + imgPath;
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        //内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //二维码内容
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, 600, 600, hints);
        //生成二维码
        UploadFileUtil.checkDirectories(qrcodePath);
        Path path = new File(qrcodePath + File.separator + context + ".png").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
        //context——>二维码显示的内容
        pressText(context, qrcodePath + File.separator + context + ".png", 1, Color.BLACK, 36, 600, 600);
        return imgPath + context + ".png";
    }

    /**
     * @param pressText 文本
     * @param newImg    二维码路径
     * @param fontStyle 字体风格
     * @param color     字体验收
     * @param fontSize  大小
     * @param width     宽度
     * @param height    高度
     * @为图片添加文字
     */
    public static void pressText(String pressText, String newImg, int fontStyle, Color color, int fontSize, int width, int height) {
        //计算文字开始的位置
        //x开始的位置：（图片宽度-字体大小*字的个数）/2

        int startX = (width - (fontSize * pressText.length())) / 3 + 64;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height - (height - width) / 2 - 10;

        try {
            File file = new File(newImg);
            Image src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);
            BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, imageW, imageH, null);
            g.setColor(color);
            g.setFont(new Font(null, fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
            g.dispose();
            FileOutputStream out = new FileOutputStream(newImg);
            ImageIO.write(image, "JPEG", out);
            out.close();
            System.out.println("image press success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 批量生成二维码并打包成zip
     *
     * @param portraitPath 文件生成路径
     * @param resp         输出流
     * @throws Exception
     */
    public static void getQrcodeAllZip(String portraitPath, HttpServletResponse resp, int type) throws Exception {
        String imgPath = UPLOADS + PATH_IMG;
        portraitPath = portraitPath + imgPath + type + "/";
        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition", "attachment; filename=" + "qrcodes.zip");
        ZipOutputStream out = new ZipOutputStream(resp.getOutputStream());
        try {
            log.info("-------------------------------压缩目录：{}", portraitPath);
            ZipUtils.doCompress(portraitPath, out);
            resp.flushBuffer();
        } catch (Exception e) {
            out.close();
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * @return boolean
     * @Author jijl
     * @Description: 服务器二维码
     * @Date 15:48 2019/2/21
     * @Param [portraitPath]
     **/
    public static boolean deleteFile(String portraitPath, String equipmentNo) {
        String sPath = portraitPath + UPLOADS + PATH_IMG + "/" + equipmentNo + ".png";
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

}
