package com.compiledideas.crewsecback.storage;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class Utils {
    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http or https
        String serverName = request.getServerName(); // hostname or IP
        int serverPort = request.getServerPort(); // port number
        String contextPath = request.getContextPath(); // application context

        if (serverPort == 80 || serverPort == 443) {
            return scheme + "://" + serverName + contextPath;
        } else {
            return scheme + "://" + serverName + ":" + serverPort + contextPath;
        }
    }
}
