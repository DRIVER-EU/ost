package pl.com.itti.app.driver.util;

import org.apache.commons.codec.binary.Base32;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public final class FileUtils {

    /**
     * Generates random filename that does not exist in the specified dir.
     *
     * @param dir directory in which the uniqueness of the file name will be checked
     * @return a unique filename with a length of 8 characters
     */
    public static String generateRandomFileName(File dir) {
        String fileName;

        do {
            SecureRandom random = new SecureRandom();
            byte[] buffer = new byte[5];
            random.nextBytes(buffer);
            fileName = new Base32().encodeAsString(buffer);
        } while (new File(dir, fileName).exists());

        return fileName;
    }

    /**
     * Saves the uploaded file on the server in specific directory.
     * Creates the directory first.
     *
     * @param dir      target directory on the server
     * @param file     uploaded file
     * @param fileName unique filename (within {@code dir})
     * @return saved file
     * @throws IOException
     */
    public static File save(File dir, MultipartFile file, String fileName)
            throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory");
        }
        File copy = new File(dir, fileName);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(copy));
        FileCopyUtils.copy(file.getInputStream(), outputStream);
        outputStream.close();
        return copy;
    }

    /**
     * Remove the file from the server.
     *
     * @param dir      target directory on the server
     * @param fileName unique filename (within {@code dir})
     * @return void
     * @throws IOException
     */
    public static void remove(File dir, String fileName)
            throws IOException {
        File fileToDelete = new File(dir, fileName);
        fileToDelete.delete();
    }

    private FileUtils() {
        throw new AssertionError();
    }
}
