package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.ModelAssistanceBoard;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import NS.pgmg.dto.board.ModelAssistanceUpdateDto;
import NS.pgmg.exception.TokenNullException;
import NS.pgmg.repository.board.ModelAssistanceBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final ModelAssistanceBoardRepository modelAssistanceBoardRepository;

    @Value("${board.path}")
    private String filePath;

    @Transactional
    public ResponseEntity<Map<String, String>> createMA(String token, ModelAssistanceCreateDto request,
                                                        MultipartFile title, List<MultipartFile> details) {

        try {
            String email = tokenCheck(token);
            String titleValue = saveTitleFile(title);
            List<String> detailsValue = saveDetailFiles(details);

            BaseBoard base = BaseBoard.setBaseBoard(request, email, titleValue, detailsValue);
            ModelAssistanceBoard modelAssistanceBoard = ModelAssistanceBoard.builder()
                    .base(base)
                    .modelAssistanceCategory(request.getModelAssistanceCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .build();
            modelAssistanceBoardRepository.save(modelAssistanceBoard);
            log.info("모델 해줄게 게시글 작성, email = {}", email);

            return ResponseEntity.ok().body(Map.of("message", "게시물 생성 완료"));
        } catch (TokenNullException | IOException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public List<ModelAssistanceBoard> findAllMA() {
        return modelAssistanceBoardRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateMA(String token, ModelAssistanceUpdateDto request,
                                                     MultipartFile title, List<MultipartFile> details) {
        String requestEmail = tokenCheck(token);

        if (!requestEmail.equals(request.getEmail())) {
            return ResponseEntity.ok().body(Map.of("message", "잘못된 접근입니다."));
        }

        try {
            ModelAssistanceBoard findBoard = modelAssistanceBoardRepository
                    .findByBidAndBase_Email(request.getId(), request.getEmail());

            BaseBoard base = findBoard.getBase();

            String titlePath = updateTitleFile(title, base.getTitlePath());
            List<String> detailsPaths = updateDetailFiles(details, base.getDetailPaths());

            BaseBoard updateBase = BaseBoard.builder()
                    .email(request.getEmail())
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .createdAt(base.getCreatedAt())
                    .firstDate(request.getFirstDate())
                    .lastDate(request.getLastDate())
                    .titlePath(titlePath)
                    .detailPaths(detailsPaths)
                    .build();

            findBoard.setUpdateBoard(updateBase, request.getModelAssistanceCategory(), request.getPlace(), request.getPrice());

            modelAssistanceBoardRepository.save(findBoard);

        } catch (IOException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }

        return ResponseEntity.ok().body(Map.of("message", "게시물 수정 완료"));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> deleteMA(String token, BoardRequestDto request) {
        String requestEmail = tokenCheck(token);

        if (!requestEmail.equals(request.getEmail())) {
            return ResponseEntity.ok().body(Map.of("message", "잘못된 접근입니다."));
        }

        modelAssistanceBoardRepository.deleteByBid(request.getId());
        return ResponseEntity.ok().body(Map.of("message", "게시물 삭제 완료"));
    }

    private String tokenCheck(String token) {

        if (token == null) {
            throw new TokenNullException("토큰이 없습니다.");
        }

        return JwtConfig.getEmail(token);
    }

    private String createRandomUuid() {
        return UUID.randomUUID().toString();
    }

    private String saveTitleFile(MultipartFile file) throws IOException {

        if (file == null) {
            return null;
        }

        String fileName = "MA-" + createRandomUuid() + ".png";
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            throw new IOException("Title 이미지 저장 오류");
        }

        return "/img/" + fileName;
    }

    private List<String> saveDetailFiles(List<MultipartFile> files) throws IOException {

        if (files == null) {
            return null;
        }

        List<String> imgPaths = new ArrayList<>();

        for (MultipartFile f : files) {
            String fileName = "MA-" + createRandomUuid() + ".png";

            try {
                f.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                throw new IOException("Details 이미지 저장 오류");
            }
            imgPaths.add("/img/" + fileName);
        }

        return imgPaths;
    }

    private String updateTitleFile(MultipartFile file, String path) throws IOException {
        String fileName;

        if (file == null) {
            return path;
        }

        if (path != null) {
            fileName = path.replace("/img/", "");
            File tempFile = new File(filePath + fileName);

            if (tempFile.exists()) {
                boolean delete = tempFile.delete();
            }
        }

        fileName = "MA-" + createRandomUuid() + ".png";

        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            throw new IOException("Title 이미지 저장 오류");
        }

        return "/img/" + fileName;
    }

    private List<String> updateDetailFiles(List<MultipartFile> files, List<String> path) throws IOException {

        if (files == null) {
            return path;
        }

        List<String> paths = new ArrayList<>();

        if (path != null) {
            for (String p : path) {
                String fileName = p.replace("/img/", "");
                File tempFile = new File(filePath + fileName);

                if (tempFile.exists()) {
                    boolean delete = tempFile.delete();
                }
            }
        }

        for (MultipartFile f : files) {
            String fileName = "MA-" + createRandomUuid() + ".png";
            try {
                f.transferTo(new File(filePath + fileName));
                paths.add("/img/" + fileName);
            } catch (IOException e) {
                throw new IOException("Details 이미지 저장 오류");
            }
        }

        return paths;
    }
}
