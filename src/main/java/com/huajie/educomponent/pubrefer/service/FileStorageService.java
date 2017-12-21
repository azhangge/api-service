package com.huajie.educomponent.pubrefer.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.constants.FileFormatType;
import com.huajie.educomponent.pubrefer.dao.FileStorageJpaRepo;
import com.huajie.educomponent.pubrefer.entity.FileStorageEntity;
import com.huajie.utils.fileconverter.*;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.huajie.utils.VideoUtil;

import static com.huajie.configs.EnvConstants.FILE_PATH;


/**
 * Created by fangxing on 17-7-14.
 */
@Service
public class FileStorageService {
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FileStorageJpaRepo fileStorageJpaRepo;

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @param fileSize
     * @return
     * @throws ParserConfigurationException 
     * @throws TransformerException 
     * @throws IOException 
     * @throws InterruptedException 
     * @throws PDFSecurityException 
     * @throws PDFException 
     */
    public FileStorageBo uploadFile(InputStream inputStream, String fileName, long fileSize) throws IOException, TransformerException, ParserConfigurationException, PDFException, PDFSecurityException, InterruptedException {
        String fileExtName = fileName.substring(fileName.lastIndexOf('.') + 1);
        
        StorePath storePath = storageClient.uploadFile(inputStream, fileSize, fileExtName, null);

        FileStorageEntity entity = new FileStorageEntity();
        entity.setFileName(fileName);
        entity.setFileSize(fileSize);

        entity.setStorageGroup(storePath.getGroup());
        entity.setStoragePath(storePath.getPath());
        String[] suffix = fileName.split("\\.");
        if (suffix.length == 1) {
            entity.setSuffix(suffix[0]);
        }else {
            entity.setSuffix(suffix[suffix.length - 1]);
        }

        String totalPath = FILE_PATH + "/" + storePath.getFullPath();
        if (isVideo(entity.getSuffix())) {
            int videoTimeLen = VideoUtil.getVideoTimeLen(totalPath, entity.getSuffix());
            entity.setVideoTimeLen(videoTimeLen);
        }

        //设置转换文件路径
        if (isDocument(entity.getSuffix())) {
            String htmlPath = convertDocumentToHtml(entity.getSuffix(), totalPath);
            entity.setTransformFilePath(htmlPath);
        }

        FileStorageEntity fileStorageEntity = fileStorageJpaRepo.save(entity);

        return convertToFileStorageBo(fileStorageEntity);
    }

    private boolean isDocument(String suffix) {
        String suffixFormatted = suffix.toLowerCase();
        if (suffixFormatted.equals(FileFormatType.DOC.getValue())
                || suffixFormatted.equals(FileFormatType.DOCX.getValue())
                || suffixFormatted.equals(FileFormatType.PPT.getValue())
                || suffixFormatted.equals(FileFormatType.PPTX.getValue())
                || suffixFormatted.equals(FileFormatType.PDF.getValue())) {
            return true;
        }

        return false;
    }


    private boolean isVideo(String suffix) {
        String suffixFormatted = suffix.toLowerCase();
        if (suffixFormatted.equals(FileFormatType.MP4.getValue())) {
            return true;
        }

        return false;
    }

    private String convertDocumentToHtml(String fileSuffix, String fileAbsolutePath)
            throws InterruptedException, PDFException, TransformerException, IOException, PDFSecurityException, ParserConfigurationException {
        DocumentConverter converter = DocumentConverterFactory.createConverter(fileSuffix);
        if (converter == null) {
            return null;
        }
        converter.convert(fileAbsolutePath);
        return converter.getHtmlPath();
    }


    /**
     * 获取文件的本地信息
     * @param fileId
     * @return
     */
    public FileStorageBo getStore(String fileId){
        FileStorageEntity result = fileStorageJpaRepo.findOne(fileId);
        if (result == null){
            return null;
        }
        return convertToFileStorageBo(result);

    }

    public void saveStore(FileStorageEntity fileStorageEntity){
        fileStorageJpaRepo.save(fileStorageEntity);
    }

    /**
     * 获取视频时长
     * @param fileId
     * @return
     */
    public Integer getVideoLen(String fileId){
        FileStorageEntity fileStorageEntity = fileStorageJpaRepo.findById(fileId);
        if (fileStorageEntity == null){
            return 0;
        }
        if (!fileStorageEntity.getSuffix().equalsIgnoreCase("mp4")){
            return 0;
        }
        return fileStorageEntity.getVideoTimeLen();
    }

    public void updateFileRefer(String fileId, String refId){
        FileStorageEntity entity = fileStorageJpaRepo.findById(fileId);
        entity.setOwnerId(refId);
        fileStorageJpaRepo.save(entity);
    }

    public void deleteFile(String fileId) {
        FileStorageEntity entityList = fileStorageJpaRepo.findById(fileId);
        delete(entityList);
    }


    //此业务应异步调用
    public void deleteIdleFile(){
        List<FileStorageEntity> entityList = fileStorageJpaRepo.findIdleFiles();
        delete(entityList);
    }

    private void delete(List<FileStorageEntity> entities) {
        for (FileStorageEntity entity : entities) {
            delete(entity);
        }
    }

    private void delete(FileStorageEntity entity) {
        entity.setDeleted(true);
        fileStorageJpaRepo.save(entity);

        String storageFilePath = entity.getStorageGroup()+ "/" + entity.getStoragePath();
        storageClient.deleteFile(storageFilePath);
    }

    public FileStorageBo convertToFileStorageBo(FileStorageEntity fileStorageEntity){
        if (fileStorageEntity == null){
            return null;
        }
        FileStorageBo fileStorageBo = new FileStorageBo();
        BeanUtils.copyProperties(fileStorageEntity, fileStorageBo);
        fileStorageBo.setFileId(fileStorageEntity.getId());
        return fileStorageBo;
    }

    /**
     * 获取视频时长,单位秒
     * @param storePath
     */
    private int getVideoTimeLen(StorePath storePath, String fileExtName){
        int min = 0;
        if (!fileExtName.equalsIgnoreCase("mp4")){
            return min;
        }
        String totalPath = FILE_PATH + "/" + storePath.getFullPath();
        try {
            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(totalPath);
            min = (int) (info.getDuration() / 1000);
        } catch (EncoderException e) {
            logger.error(e.toString());
            min = 0;
        }
        return min;
    }
}
