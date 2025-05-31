package util;

import java.io.File;

public class FileUtils {
    private static final String FILES_DIRECTORY = "files";

    public static String getFilePath(String fileName) {
        return FILES_DIRECTORY + File.separator + fileName;
    }

    public static void createDirectoryIfNotExists() {
        File directory = new File(FILES_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
