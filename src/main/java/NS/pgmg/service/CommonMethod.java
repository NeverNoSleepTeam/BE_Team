package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommonMethod {

    @Value("${board.path}")
    public static String boardPath;

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
    
//    public static String saveFile(MultipartFile file, String filePath) {
//
//        if (file == null) {
//            return null;
//        }
//
//        String fileName = createRandomUuid() + ".png";
//        try {
//            file.transferTo(new File(filePath + fileName));
//        } catch (IOException e) {
//            throw new RuntimeException("이미지 저장 오류", e);
//        }
//
//        return "/img/" + fileName;
//    }
//
//    public static List<String> saveFiles(List<MultipartFile> files, String filePath) {
//
//        if (files == null) {
//            return null;
//        }
//
//        List<String> imgPaths = new ArrayList<>();
//
//        for (MultipartFile f : files) {
//            String fileName = createRandomUuid() + ".png";
//
//            try {
//                f.transferTo(new File(filePath + fileName));
//            } catch (IOException e) {
//                throw new RuntimeException("이미지 저장 오류", e);
//            }
//            imgPaths.add("/img/" + fileName);
//        }
//
//        return imgPaths;
//    }

    public static String updateFile(MultipartFile file, String path, String filePath) {

        String fileName;

        if (path != null) {
            fileName = path.replace("/img/", "");
            deleteFile(fileName, filePath);
        }

        if (file != null) {
            fileName = createRandomUuid() + ".png";

            try {
                file.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 오류", e);
            }

            return "/img/" + fileName;
        }
        return null;
    }

    public static List<String> updateFiles(List<MultipartFile> files, List<String> path, String filePath) {

        List<String> paths = new ArrayList<>();

        if (path != null) {
            for (String p : path) {
                String fileName = p.replace("/img/", "");
                deleteFile(fileName, filePath);
            }
        }

        if (files != null) {
            for (MultipartFile f : files) {
                String fileName = createRandomUuid() + ".png";
                try {
                    f.transferTo(new File(filePath + fileName));
                    paths.add("/img/" + fileName);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 오류", e);
                }
            }
            return paths;
        }

        return null;
    }

    protected static void deleteFile(String fileName, String filePath) {
        File tempFile = new File(filePath + fileName);

        if (tempFile.exists()) {
            boolean delete = tempFile.delete();
        }
    }
}
