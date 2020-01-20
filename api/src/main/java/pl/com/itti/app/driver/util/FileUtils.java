package pl.com.itti.app.driver.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.web.AttachmentController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Optional;

public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

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


    /**
     * Retrieves extension from multipart file.
     *
     * @param file a resource
     * @return extension of file
     */
    public static String getFileExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

    /**
     * Sets parameters for response with file.
     *
     * @param response servlet response
     * @param request user request
     * @param path location of file
     */
    public static void setFileResponse(HttpServletResponse response, HttpServletRequest request, String path) {
        ServletContext context = request.getServletContext();
        String contentType = Optional.ofNullable(context.getMimeType(path))
                .filter(type -> !type.isEmpty())
                .orElse("application/octet-stream");
        response.setContentType(contentType);

        File file = new File(path);
        response.setContentLength((int) file.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        response.setHeader(headerKey, headerValue);

        try (InputStream inputStream = new FileInputStream(file)) {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new InternalServerException("IOError writing file to output stream");
        }
    }

    /**
     * @see FileUtils#getAbsolutePath(File)
     *
     * @param path path to file
     */
    public static String getAbsolutePath(String path) {
        return getAbsolutePath(new File(path));
    }

    /**
     * Obtains absolute path to file with respect to file separator
     * used in operating system.
     *
     * @param file file in file system
     * @return absolute path to file
     */
    public static String getAbsolutePath(File file) {
        String absolutePath = file.getAbsolutePath();
        return Paths.get(absolutePath).toString();
    }
}
