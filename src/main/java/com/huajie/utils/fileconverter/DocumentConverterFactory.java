package com.huajie.utils.fileconverter;

import com.huajie.educomponent.pubrefer.constants.FileFormatType;

public class DocumentConverterFactory {
    public static DocumentConverter createConverter(String fileSuffix) {
        if (fileSuffix.equals(FileFormatType.DOC.getValue())) {
            return new DocConverter();
        }

        if (fileSuffix.equals(FileFormatType.DOCX.getValue())) {
            return new DocxConverter();
        }

        if (fileSuffix.equals(FileFormatType.PPT.getValue())) {
            return new PptConverter();
        }

        if (fileSuffix.equals(FileFormatType.PPTX.getValue())) {
            return new PptxConverter();
        }

        if (fileSuffix.equals(FileFormatType.PDF.getValue())) {
            return new PdfConverter();
        }

        return null;
    }
}
