package com.huajie.utils.fileconverter;

import org.apache.poi.hslf.usermodel.*;

import javax.imageio.ImageIO;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class PptConverter extends DocumentConverter {
    @Override
    public String convertFile(String fileAbsolutePath) throws IOException, TransformerException {
        OutputStream out = null;
        InputStream input = null;
        HSLFSlideShow ppt = null;

        // 读入PPT文件
        try {
            input = getSourceInputStream(fileAbsolutePath);
            ppt = new HSLFSlideShow(new HSLFSlideShowImpl(input));

            Dimension pgsize = ppt.getPageSize();
            StringBuffer imgHtml = new StringBuffer("");
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                //防止中文乱码
                for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape tsh = (HSLFTextShape) shape;
                        for (HSLFTextParagraph p : tsh) {
                            for (HSLFTextRun r : p) {
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                // 设置转换后的背景色为白色
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                // 绘图
                ppt.getSlides().get(i).draw(graphics);

                String filename = buildImgFileName(i);
                out = new FileOutputStream(buildImgFilePath(filename));
                ImageIO.write(img, IMG_TYPE, out);
                img.flush();
                out.close();

                //图片在html加载路径
                String imageRelativePath = IMAGE_PATH_NAME + File.separator + filename;
                imgHtml.append(buildImgHtmlTag(imageRelativePath));
            }
            File htmlFile = new File(storePath + File.separator + buildHtmlFileName(fileAbsolutePath));
            writeHtml(htmlFile, imgHtml);
        } finally {
            if (out != null)
                out.close();
            if (ppt != null)
                ppt.close();
            if (input != null)
                input.close();

        }
        return relativePath;
    }
}
