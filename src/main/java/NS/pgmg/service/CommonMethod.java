package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public final class CommonMethod {

    public static String imgPath;
    public static String pdfPath;

    @Value("${img.path}")
    public void setImgPath(String imgPath) {
        CommonMethod.imgPath = imgPath;
    }

    @Value("${pdf.path}")
    public void setPdfPath(String pdfPath) {
        CommonMethod.pdfPath = pdfPath;
    }


    public static String tokenCheck(String token) {

        if (token == null) {
            throw new RuntimeException("토큰이 없습니다.");
        }

        return JwtConfig.getEmail(token);
    }

    public static void emailCheck(String tokenEmail, String requestEmail) {

        if (!tokenEmail.equals(requestEmail)) {
            throw new RuntimeException("토큰 정보와 요청하신 이메일의 정보가 일치하지 않습니다.");
        }

    }

    public static String createRandomUuid() {
        return UUID.randomUUID().toString();
    }

    public static String updatePDF(MultipartFile file, String path) {

        String fileName;

        if (path != null) {
            fileName = path.replace("/portfolio/", "");
            deleteFile(fileName, pdfPath);
        }

        if (file != null && !file.isEmpty()) {
            fileName = createRandomUuid() + ".pdf";

            try {
                file.transferTo(new File(pdfPath + fileName));
            } catch (IOException e) {
                throw new RuntimeException("pdf 저장 오류", e);
            }

            return "/portfolio/" + fileName;
        }

        return null;
    }

    public static String updateImgFile(MultipartFile file, String path) {

        String fileName;

        if (path != null) {
            fileName = path.replace("/img/", "");
            deleteFile(fileName, imgPath);
        }

        if (file != null && !file.isEmpty()) {
            fileName = createRandomUuid() + ".png";

            try {
                file.transferTo(new File(imgPath + fileName));
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 오류", e);
            }

            return "/img/" + fileName;
        }
        return null;
    }

    public static List<String> updateImgFiles(List<MultipartFile> files, List<String> path) {

        List<String> paths = new ArrayList<>();

        if (path != null) {
            for (String p : path) {
                String fileName = p.replace("/img/", "");
                deleteFile(fileName, imgPath);
            }
        }

        if (files != null) {
            for (MultipartFile f : files) {
                String fileName = createRandomUuid() + ".png";
                try {
                    f.transferTo(new File(imgPath + fileName));
                    paths.add("/img/" + fileName);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 오류", e);
                }
            }
            return paths;
        }

        return null;
    }

    public static void deleteFile(String fileName, String filePath) {
        File tempFile = new File(filePath + fileName);

        if (tempFile.exists()) {
            boolean delete = tempFile.delete();
        }
    }
}
