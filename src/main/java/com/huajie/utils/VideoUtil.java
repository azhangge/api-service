package com.huajie.utils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoUtil {
    static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    public static int getVideoTimeLen(String fileAbsolutePath, String fileExtName) {
        int min = 0;
        if (!fileExtName.equalsIgnoreCase("mp4")) {
            return min;
        }

        try {
            Encoder encoder = new Encoder();
            MultimediaInfo info = encoder.getInfo(fileAbsolutePath);
            min = (int) (info.getDuration() / 1000);
        } catch (EncoderException e) {
            logger.error(e.toString());
            min = 0;
        }
        return min;
    }
}
