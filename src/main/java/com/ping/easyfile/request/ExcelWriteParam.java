package com.ping.easyfile.request;

import com.ping.easyfile.excelmeta.ExcelSheet;
import com.ping.easyfile.util.FileUtil;

import java.util.List;
import java.util.Objects;

/**
 * 导出Excel 对象
 *
 * @author liu_wp
 * @date Created in 2019/2/28 18:01
 * @see
 */
public class ExcelWriteParam {
    /**
     * excel 模板文件
     */
    private String excelTemplateFile;
    /**
     * excel 导出路径
     */
    private String excelOutFilePath;
    /**
     * excel 文件名称
     */
    private String excelFileName;
    /**
     * excel 导出全路径（路径+文件名称）
     */
    private String excelOutFileFullPath;

    /**
     * excel sheets
     */
    private List<ExcelSheet> excelSheets;

    private WaterMark waterMark;

    /**
     *
     */
    private boolean inMemory;


    public ExcelWriteParam() {
    }


    ExcelWriteParam(String excelTemplateFile, String excelOutFilePath, String excelFileName, String excelOutFileFullPath, List<ExcelSheet> excelSheets, WaterMark waterMark, boolean inMemory) {
        this.excelTemplateFile = excelTemplateFile;
        this.excelOutFilePath = excelOutFilePath;
        this.excelFileName = excelFileName;
        this.excelOutFileFullPath = excelOutFileFullPath;
        this.excelSheets = excelSheets;
        this.waterMark = waterMark;
        this.inMemory = waterMark != null || inMemory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String excelTemplateFile;

        private String excelOutFilePath;

        private String excelFileName;

        private String excelOutFileFullPath;


        private List<ExcelSheet> excelSheets;

        private WaterMark waterMark;

        private boolean inMemory;

        Builder() {
        }

        public Builder waterMark(WaterMark waterMark) {
            this.waterMark = waterMark;
            return this;
        }

        public Builder inMemory(Boolean inMemory) {
            this.inMemory = inMemory;
            return this;
        }

        public Builder excelTemplateFile(String excelTemplateFile) {
            this.excelTemplateFile = excelTemplateFile;
            return this;
        }

        public Builder excelOutFilePath(String excelOutFilePath) {
            this.excelOutFilePath = excelOutFilePath;
            return this;
        }

        public Builder excelFileName(String excelFileName) {
            this.excelFileName = excelFileName;
            return this;
        }

        public Builder excelSheets(List<ExcelSheet> excelSheets) {
            this.excelSheets = excelSheets;
            return this;
        }

        public Builder excelOutFileFullPath(String excelOutFileFullPath) {
            this.excelOutFileFullPath = excelOutFileFullPath;
            return this;
        }

        public ExcelWriteParam build() {
            return new ExcelWriteParam(excelTemplateFile, excelOutFilePath, excelFileName, excelOutFileFullPath, excelSheets, waterMark, inMemory);
        }

        @Override
        public String toString() {
            return "builder{" +
                    "excelTemplateFile='" + excelTemplateFile + '\'' +
                    ", excelOutFilePath='" + excelOutFilePath + '\'' +
                    ", excelFileName='" + excelFileName + '\'' +
                    ", excelOutFileFullPath='" + excelOutFileFullPath + '\'' +
                    ", excelSheets=" + excelSheets +
                    '}';
        }
    }

    public List<ExcelSheet> getExcelSheets() {
        return excelSheets;
    }

    public void setExcelSheets(List<ExcelSheet> excelSheets) {
        this.excelSheets = excelSheets;
    }

    public String getExcelTemplateFile() {
        return excelTemplateFile;
    }

    public void setExcelTemplateFile(String excelTemplateFile) {
        this.excelTemplateFile = excelTemplateFile;
    }

    public String getExcelOutFilePath() {
        return excelOutFilePath;
    }

    public void setExcelOutFilePath(String excelOutFilePath) {
        this.excelOutFilePath = excelOutFilePath;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public WaterMark getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(WaterMark waterMark) {
        this.waterMark = waterMark;
    }

    public String getExcelOutFileFullPath() {
        if (this.excelOutFileFullPath != null && this.excelOutFileFullPath.length() > 0) {
            return this.excelOutFileFullPath;
        }
        return FileUtil.fileReplacePath(this.excelOutFilePath + "/" + this.excelFileName);
    }

    public void setExcelOutFileFullPath(String excelOutFileFullPath) {
        this.excelOutFileFullPath = excelOutFileFullPath;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExcelWriteParam)) {
            return false;
        }

        ExcelWriteParam that = (ExcelWriteParam) o;

        if (!Objects.equals(excelTemplateFile, that.excelTemplateFile)) {
            return false;
        }
        if (!Objects.equals(excelOutFilePath, that.excelOutFilePath)) {
            return false;
        }
        if (!Objects.equals(excelFileName, that.excelFileName)) {
            return false;
        }
        if (!Objects.equals(excelOutFileFullPath, that.excelOutFileFullPath)) {
            return false;
        }
        return Objects.equals(excelSheets, that.excelSheets);
    }

    @Override
    public int hashCode() {
        int result = excelTemplateFile != null ? excelTemplateFile.hashCode() : 0;
        result = 31 * result + (excelOutFilePath != null ? excelOutFilePath.hashCode() : 0);
        result = 31 * result + (excelFileName != null ? excelFileName.hashCode() : 0);
        result = 31 * result + (excelOutFileFullPath != null ? excelOutFileFullPath.hashCode() : 0);
        result = 31 * result + (excelSheets != null ? excelSheets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExcelWriteParam{" +
                "excelTemplateFile='" + excelTemplateFile + '\'' +
                ", excelOutFilePath='" + excelOutFilePath + '\'' +
                ", excelFileName='" + excelFileName + '\'' +
                ", excelOutFileFullPath='" + excelOutFileFullPath + '\'' +
                ", excelSheets=" + excelSheets +
                '}';
    }
}
