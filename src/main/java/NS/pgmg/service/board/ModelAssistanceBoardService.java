package NS.pgmg.service.board;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.ModelAssistanceBoard;
import NS.pgmg.dto.board.ModelAssistanceCreateDto;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelAssistanceBoardService {

    private final ModelAssistanceBoardRepository modelAssistanceBoardRepository;

    @Value("${board.path}")
    private String filePath;

    @Transactional
    public ResponseEntity<Map<String, String>> create(String token, ModelAssistanceCreateDto request,
                                                      MultipartFile title, List<MultipartFile> details) {

        Long id = (long) modelAssistanceBoardRepository.findAll().size() + 1L;

        try {
            String email = tokenCheck(token);
            String titleValue = modelAssistanceTitleFileSaved(id, email, title);
            List<String> detailsValue = modelAssistanceDetailFilesSaved(id, email, details);

            BaseBoard base = BaseBoard.setBaseBoard(request, email, titleValue, detailsValue);
            ModelAssistanceBoard modelAssistanceBoard = ModelAssistanceBoard.builder()
                    .base(base)
                    .modelAssistanceCategory(request.getModelAssistanceCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .build();
            modelAssistanceBoardRepository.save(modelAssistanceBoard);
            log.info("모델 해줄게 게시글 작성, id = {}, email = {}", id, email);

            return ResponseEntity.ok().body(Map.of("message", "게시물 생성 완료"));
        } catch (TokenNullException | IOException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }
    }

    private String tokenCheck(String token) {

        if (token == null) {
            throw new TokenNullException("토큰이 없습니다.");
        }

        return JwtConfig.getEmail(token);
    }

    private String modelAssistanceTitleFileSaved(Long id, String email, MultipartFile file) throws IOException {

        if (file == null) {
            return "파일이 없습니다.";
        }

        String savedPath = filePath + "MA-" + id + "-" + email + "-title.png";
        try {
            file.transferTo(new File(savedPath));
            log.info("파일 저장 savedPath = {}", savedPath);
        } catch (IOException e) {
            log.warn("message = Title 이미지 저장 오류");
            throw new IOException("Title 이미지 저장 오류");
        }
        log.info("Title 이미지 저장 = /img/MA-{}-{}-title.png", id, email);

        return "/img/MA-" + id + "-" + email + "-title.png";
    }

    private List<String> modelAssistanceDetailFilesSaved(Long id, String email, List<MultipartFile> files) throws IOException {

        if (files == null) {
            return Collections.singletonList("파일이 없습니다.");
        }

        List<String> imgPaths = new ArrayList<>();
        for (int i = 1; i <= files.size(); i++) {
            String savedPath = filePath + "MA-" + id + "-" + email + "-detail-" + i + ".png";

            try {
                files.get(i-1).transferTo(new File(savedPath));
            } catch (IOException e) {
                log.warn("message = Details 이미지 저장 오류");
                throw new IOException("Details 이미지 저장 오류");
            }

            imgPaths.add("/img/MA-" + id + "-" + email + "-detail-" + i + ".png");
            log.info("Details 이미지 저장 = /img/MA-{}-{}-detail-{}.png", id, email, i);
        }

        return imgPaths;
    }
}
