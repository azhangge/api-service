package com.huajie.utils.fileconverter;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.net.URL;

public class DocxConverter extends DocumentConverter {

    @Override
    public String convertFile(String fileAbsolutePath) throws IOException {
        OutputStream out = null;
        InputStream input = null;
        XWPFDocument document = null;

        try {
            //加载word文档生成 XWPFDocument对象
            input = getSourceInputStream(fileAbsolutePath);
            document = new XWPFDocument(input);

            //将 XWPFDocument转换成XHTML
            File htmlFile = new File(storePath + File.separator + buildHtmlFileName(fileAbsolutePath));
            out = new FileOutputStream(htmlFile);

            XHTMLConverter.getInstance().convert(document, out, buildXhtmlOptions());
        } finally {
            if (out != null)
                out.close();
            if (document != null)
                document.close();
            if (input != null)
                input.close();
        }

        return relativePath;
    }

    private XHTMLOptions buildXhtmlOptions() {
        //解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        File imgFolder = new File(imagePath);
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new BasicURIResolver("image"));
        options.setExtractor(new FileImageExtractor(imgFolder));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);
        return options;
    }
}
