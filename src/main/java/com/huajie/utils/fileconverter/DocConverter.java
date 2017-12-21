package com.huajie.utils.fileconverter;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class DocConverter extends DocumentConverter {

    @Override
    public String convertFile(String fileAbsolutePath) throws IOException, ParserConfigurationException, TransformerException {
        OutputStream outStream = null;
        InputStream input = null;

        try {
            //读取fdfs上的文件
            input = getSourceInputStream(fileAbsolutePath);
            HWPFDocument wordDocument = new HWPFDocument(input);

            //解析word文档
            WordToHtmlConverter wordToHtmlConverter = createWordToHtmlConverter();
            wordToHtmlConverter.processDocument(wordDocument);

            //转换后的文件
            Document htmlDocument = wordToHtmlConverter.getDocument();
            DOMSource domSource = new DOMSource(htmlDocument);

            //文件输出
            File htmlFile = new File(storePath + File.separator + buildHtmlFileName(fileAbsolutePath));
            outStream = new FileOutputStream(htmlFile);
            StreamResult streamResult = new StreamResult(outStream);

            Transformer serializer = getTransformer();
            serializer.transform(domSource, streamResult);
        } finally {
            if (outStream != null) {
                outStream.close();
            }

            if (input != null) {
                input.close();
            }
        }

        return relativePath;
    }

    private Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        return serializer;
    }

    private WordToHtmlConverter createWordToHtmlConverter() throws IOException, ParserConfigurationException {
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] bytes, PictureType pictureType, String imageName, float v, float v1) {
                File file = new File(buildImgFilePath(imageName));
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(bytes);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //图片对html的相对路径，供html文件引用。
                return IMAGE_PATH_NAME + File.separator + imageName;
            }
        });

        return wordToHtmlConverter;
    }
}
