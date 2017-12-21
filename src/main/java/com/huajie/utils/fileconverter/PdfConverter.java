package com.huajie.utils.fileconverter;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import javax.xml.transform.TransformerException;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;

public class PdfConverter extends DocumentConverter {
    @Override
    public String convertFile(String fileAbsolutePath) throws InterruptedException, IOException, TransformerException, PDFException, PDFSecurityException {
        Document document = null;
        OutputStream out = null;

        // 读入PPT文件
        try {
            URL url = new URL(fileAbsolutePath);
            document = new Document();
            document.setUrl(url);

            float scale = 1f;// 缩放比例
            float rotation = 0f;// 旋转角度
            StringBuffer imgHtml = new StringBuffer("");
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                        Page.BOUNDARY_CROPBOX, rotation, scale);
                RenderedImage rendImage = image;
                // 保存输出
                String filename = buildImgFileName(i);
                out = new FileOutputStream(buildImgFilePath(filename));
                // 这里png作用是：格式是jpg但有png清晰度
                ImageIO.write(rendImage, "png", out);
                image.flush();
                out.close();

                // 图片在html加载路径
                String imageRelativePath = IMAGE_PATH_NAME + File.separator + filename;
                imgHtml.append(buildImgHtmlTag(imageRelativePath));
            }

            //写html文件
            File htmlFile = new File(storePath + File.separator + buildHtmlFileName(fileAbsolutePath));
            writeHtml(htmlFile, imgHtml);
        } finally {
            if (out != null) {
                out.close();
            }

            if (document != null) {
                document.dispose();
            }

        }
        return relativePath;
    }
}
