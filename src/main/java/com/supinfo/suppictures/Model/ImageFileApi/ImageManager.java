package com.supinfo.suppictures.Model.ImageFileApi;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * a class with static methods to verify a {@link Part} and save it
 */
public class ImageManager {
    private static List<String> allowedExtensions = Arrays.asList("png","jpg","bmp","jpeg","gif");
    private static long maxSize = 10000000;
    private static String UPLOAD_PATH = System.getenv("UPLOAD_PATH");
    public static final String IMAGE_UPLOADS_ROOT = "/supuploads/";

    /**
     * @return generates a random string based on time and a random alphanum string
     */
    private static String generateFileName(){
        String filename = "";
        long millis = System.currentTimeMillis();
        String datetime = new Date().toGMTString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        String rndchars = RandomStringUtils.randomAlphanumeric(16);
        filename = rndchars + "_" + datetime + "_" + millis;
        return filename;
    }

    /**
     * Verefies a part based on whether it satisfies file size limits and extensions allowed
     * @param part the part to be verified
     * @return returns a status based on the validity of the file
     */
    public static VerifyStatus verify(Part part){
        String ext = FilenameUtils.getExtension(part.getSubmittedFileName());
        if(part.getSize()> maxSize){
            return VerifyStatus.ERROR_TOO_BIG;
        }
        if(!allowedExtensions.contains(ext)){
            return VerifyStatus.ERROR_INVALID_EXTENSION;
        }
        return VerifyStatus.VALID;
    }

    /**
     * Saves a part and adds the tile to the end of the randomly generated name
     * @param part Part with image to be saved
     * @param title title supplied by user
     * @return returns the absolute path of the image saved
     * @throws IOException
     */
    public static String savePartAs(Part part,String title)throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String fileName = generateFileName()+"_"+title+"."+FilenameUtils.getExtension(part.getSubmittedFileName());
        File outputFile = new File(UPLOAD_PATH +  File.separator + fileName);
        inputStream = part.getInputStream();
        outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return ImageManager.IMAGE_UPLOADS_ROOT + fileName;
    }

    public static List<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public static long getMaxSize() {
        return maxSize;
    }

    public static String getUploadPath() {
        return UPLOAD_PATH;
    }
}
