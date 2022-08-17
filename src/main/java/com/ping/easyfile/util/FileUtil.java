package com.ping.easyfile.util;

import java.io.*;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 16:58
 */
public class FileUtil {
    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    private static final String FILETEMP = "fileTemp";

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    /**
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static synchronized OutputStream synGetResourcesFileOutputStream(String filePath, String fileName) throws IOException {
        File file = new File(fileReplacePath(filePath));
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(fileReplacePath(filePath + "/" + fileName));
        return new FileOutputStream(file);
    }
    /**
     * @param filePath
     * @return
     */
    public static String fileReplacePath(String filePath) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().indexOf("windows") != -1) {
            filePath = filePath.replace("/", "\\");
        } else {
            filePath = filePath.replace("\\", "/");
        }
        return filePath;
    }

    public static void close(InputStream inputStream, OutputStream outputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {

        }
    }

    public static String createTempDirectory() {

        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                    "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm fileTemp!");
        }
        File directory = new File(tmpDir, FILETEMP);
        if (!directory.exists()) {
            syncCreateTempFilesDirectory(directory);
        }
        return directory.getAbsolutePath();
    }

    /**
     * @param directory
     */
    private static synchronized void syncCreateTempFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
