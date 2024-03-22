package NS.pgmg.service;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.ModelAssistanceBoard;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import NS.pgmg.dto.board.ModelAssistanceFindResponseDto;
import NS.pgmg.dto.board.ModelAssistanceUpdateDto;
import NS.pgmg.repository.board.ModelAssistanceBoardRepository;
import NS.pgmg.repository.user.UserRepository;
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

import static NS.pgmg.service.CommonMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final ModelAssistanceBoardRepository modelAssistanceBoardRepository;
    private final UserRepository userRepository;

    @Value("${board.path}")
    private String filePath;

    @Transactional
    public ResponseEntity<Map<String, String>> createMA(String token, ModelAssistanceCreateDto request,
                                                        MultipartFile title, List<MultipartFile> details) {

        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            String titleValue = saveTitleFile(title);
            List<String> detailsValue = saveDetailFiles(details);

            BaseBoard base = BaseBoard.setBaseBoard(request, requestEmail, titleValue, detailsValue);
            ModelAssistanceBoard modelAssistanceBoard = ModelAssistanceBoard.builder()
                    .base(base)
                    .modelAssistanceCategory(request.getModelAssistanceCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .build();
            modelAssistanceBoardRepository.save(modelAssistanceBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> findMA(String token, BoardRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            User findUser = userRepository.findByEmail(requestEmail);
            ModelAssistanceBoard findBoard = modelAssistanceBoardRepository.
                    findByBidAndBase_Email(request.getId(), requestEmail);

            BaseBoard findBase = findBoard.getBase();

            return ResponseEntity.ok().body(ModelAssistanceFindResponseDto.builder()
                    .name(findUser.getName())
                    .createdAt(findBase.getCreatedAt())
                    .title(findBase.getTitle())
                    .price(findBoard.getPrice())
                    .modelAssistanceCategory(findBoard.getModelAssistanceCategory())
                    .firstDate(findBase.getFirstDate())
                    .lastDate(findBase.getLastDate())
                    .place(findBoard.getPlace())
                    .gender(findUser.getGender())
                    .height(findUser.getHeight())
                    .weight(findUser.getWeight())
                    .top(findUser.getTop())
                    .bottom(findUser.getBottom())
                    .shoes(findUser.getShoes())
                    .nationality(findUser.getNationality())
                    .city(findUser.getCity())
                    .titlePath(findBase.getTitlePath())
                    .detailPath(findBase.getDetailPaths())
                    .build()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public List<ModelAssistanceBoard> findAllMA() {
        return modelAssistanceBoardRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateMA(String token, ModelAssistanceUpdateDto request,
                                                     MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

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

            return ResponseEntity.ok().body(Map.of("message", "게시물이 수정됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }

    @Transactional
    public ResponseEntity<Map<String, String>> deleteMA(String token, BoardRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            modelAssistanceBoardRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }

    private String saveTitleFile(MultipartFile file) {

        if (file == null) {
            return null;
        }

        String fileName = "MA-" + createRandomUuid() + ".png";
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Title 이미지 저장 오류", e);
        }

        return "/img/" + fileName;
    }

    private List<String> saveDetailFiles(List<MultipartFile> files) {

        if (files == null) {
            return null;
        }

        List<String> imgPaths = new ArrayList<>();

        for (MultipartFile f : files) {
            String fileName = "MA-" + createRandomUuid() + ".png";

            try {
                f.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                throw new RuntimeException("Details 이미지 저장 오류", e);
            }
            imgPaths.add("/img/" + fileName);
        }

        return imgPaths;
    }

    private String updateTitleFile(MultipartFile file, String path) {
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
            throw new RuntimeException("Title 이미지 저장 오류", e);
        }

        return "/img/" + fileName;
    }

    private List<String> updateDetailFiles(List<MultipartFile> files, List<String> path) {

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
                throw new RuntimeException("Details 이미지 저장 오류", e);
            }
        }

        return paths;
    }
}
