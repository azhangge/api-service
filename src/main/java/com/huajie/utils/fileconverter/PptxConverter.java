package com.huajie.utils.fileconverter;

import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import javax.xml.transform.TransformerException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class PptxConverter extends DocumentConverter {
    private static final String FONT = "宋体";


    @Override
    public String convertFile(String fileAbsolutePath) throws IOException, TransformerException {

        InputStream input = null;
        XMLSlideShow ppt = null;

        // 读入PPT文件
        try {
            input = getSourceInputStream(fileAbsolutePath);
            ppt = new XMLSlideShow(input);

            Dimension pageSize = ppt.getPageSize();
            StringBuffer imgHtml = new StringBuffer("");
            //对PPT文件中的每一张幻灯片进行转换和操作
            List<XSLFSlide> pptPageXSLFSLiseList = ppt.getSlides();
            for (int pageIndex = 0; pageIndex < pptPageXSLFSLiseList.size(); pageIndex++) {
                XSLFSlide slide = pptPageXSLFSLiseList.get(pageIndex);
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape tsh = (XSLFTextShape) shape;
                        for (XSLFTextParagraph p : tsh) {
                            for (XSLFTextRun r : p) {
                                r.setFontFamily(FONT);
                            }
                        }
                    }
                }

                BufferedImage img = new BufferedImage(pageSize.width, pageSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                // 设置转换后的背景色为白色
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));

                slide.draw(graphics);

                // 保存输出
                String filename = buildImgFileName(pageIndex);
                OutputStream out = new FileOutputStream(buildImgFilePath(filename));
                ImageIO.write(img, IMG_TYPE, out);
                graphics.dispose();
                img.flush();
                out.close();
                // 图片在html加载路径
                String imageRelativePath = IMAGE_PATH_NAME + File.separator + filename;
                imgHtml.append(buildImgHtmlTag(imageRelativePath));
            }

            //写html文件
            File htmlFile = new File(storePath + File.separator + buildHtmlFileName(fileAbsolutePath));
            writeHtml(htmlFile, imgHtml);

        } finally {
            if (ppt != null)
                ppt.close();
            if (input != null)
                input.close();

        }
        return relativePath;
    }

}

