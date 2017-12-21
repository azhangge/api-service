package com.huajie.educomponent.pubrefer.constants;

public enum FileFormatType {

    DOC("doc"),
    DOCX("docx"),
    PPT("ppt"),
    PPTX("pptx"),
    PDF("pdf"),
    MP4("mp4");

    private String value;

    FileFormatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
