package com.huajie.utils.fileconverter;

import com.huajie.utils.CodeGenerator;
import com.huajie.utils.PathUtils;
import com.huajie.utils.RemoteSyncUtils;
import org.apache.commons.io.FileUtils;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class DocumentConverter {
    static final String IMAGE_PATH_NAME = "image";
    static final String IMG_TYPE = "jpeg";
    String storePath;
    String relativePath;
    String imagePath;
    String pathUuid;

    abstract String convertFile(String fileAbSolutePath) throws IOException, ParserConfigurationException, TransformerException, InterruptedException, PDFException, PDFSecurityException;

    public String getHtmlPath() {
        return relativePath;
    }

    public void convert(String fileAbsolutePath) throws IOException, ParserConfigurationException, TransformerException, InterruptedException, PDFException, PDFSecurityException {
        buildStorePath();
        createStoreDir();
        buildRelativePath(fileAbsolutePath);
        convertFile(fileAbsolutePath);
        syncHtmlFile();
    }

    private void syncHtmlFile() {
        RemoteSyncUtils.syncFile(PathUtils.getHtmlBaseDir()+File.separator,PathUtils.getDstPath());
    }

    InputStream getSourceInputStream(String fileAbsolutePath) throws IOException {
        URL url = new URL(fileAbsolutePath);
        return new BufferedInputStream(url.openStream());
    }

    String extractFileName(String fileAbsolutePath) {
        File originFile = new File(fileAbsolutePath);
        String fileName = originFile.getName().substring(0, originFile.getName().indexOf("."));
        return fileName;
    }

    String buildHtmlFileName(String fileAbsolutePath) {
        return extractFileName(fileAbsolutePath) + ".html";
    }

    void buildStorePath() {
        pathUuid = CodeGenerator.getUuid();
        storePath = PathUtils.getHtmlBaseDir() + File.separator + pathUuid;

        imagePath = storePath + File.separator + IMAGE_PATH_NAME;
    }

    void buildRelativePath(String fileAbsolutePath) {
        relativePath = pathUuid + File.separator + buildHtmlFileName(fileAbsolutePath);
    }

    String buildImgFilePath(String imageFileName) {
        return imagePath + File.separator + imageFileName;
    }

    String buildImgFileName(int pageNo) {
        return "" + pageNo + "." + IMG_TYPE;
    }

    void createStoreDir() {
        createDir(storePath);
        createDir(imagePath);
    }

    void createDir(String dirPath) {
        File folder = new File(dirPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    String buildImgHtmlTag(String imgPath) {
        return "<img src=\'" + imgPath + "\' style=\'vertical-align:text-bottom;\'><br><br><br>";
    }

    void writeHtml(File htmlFile, StringBuffer htmlBody)
            throws TransformerFactoryConfigurationError, TransformerException,
            IOException {
        StringBuffer htmlStr = new StringBuffer(
                "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>")
                .append(htmlBody).append("</body></html>");
        FileUtils.writeStringToFile(htmlFile, htmlStr.toString(), "utf-8");
    }


}
