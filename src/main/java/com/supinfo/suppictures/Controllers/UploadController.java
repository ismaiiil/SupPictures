package com.supinfo.suppictures.Controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.*;

@ManagedBean
@SessionScoped
public class UploadController {
    private static final long serialVersionUID = 123456789L;
    private String message;
    private Part file1;
    public Part getFile1() {
        return file1;
    }
    public void setFile1(Part file1) {
        this.file1 = file1;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }
    public String uploadFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        boolean file1Success = false;
        if (file1.getSize() > 0) {
            String fileName = getFileNameFromPart(file1);
            File outputFile = new File(path + File.separator + "WEB-INF" + File.separator + fileName);
            inputStream = file1.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            file1Success = true;
        }
        if (file1Success) {
            System.out.println("File uploaded to : " + path);
            setMessage("File successfully uploaded to " + path);
        } else {

            setMessage("Error, select atleast one file!");
        }
        return null;
    }


}
