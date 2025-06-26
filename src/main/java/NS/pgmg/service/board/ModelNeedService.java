package NS.pgmg.service.board;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.enums.BigCategory;
import NS.pgmg.domain.board.ModelBoard;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.*;
import NS.pgmg.repository.board.ModelNeedRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static NS.pgmg.service.CommonMethod.*;

@Service
@RequiredArgsConstructor
public class ModelNeedService {
    private final ModelNeedRepository modelNeedRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> create(String token, CreateModelDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            String titleValue = updateImgFile(title, null);
            List<String> detailsValue = updateImgFiles(details, null);

            BaseBoard baseBoard = BaseBoard.setModelBase(request, requestEmail, titleValue, detailsValue);
            ModelBoard modelBoard = ModelBoard.builder()
                    .baseBoard(baseBoard)
                    .category(request.getCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .bigCategory(BigCategory.해줘)
                    .build();
            modelNeedRepository.save(modelBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> find(BoardRequestDto request) {
        try {
            User findUser = userRepository.findByEmail(request.getEmail());
            ModelBoard findBoard = modelNeedRepository.
                    findByBidAndBaseBoard_Email(request.getId(), request.getEmail());

            BaseBoard findBaseBoard = findBoard.getBaseBoard();

            return ResponseEntity.ok().body(FindModelNeedResponseDto.builder()
                    .name(findUser.getName())
                    .title(findBaseBoard.getTitle())
                    .contents(findBaseBoard.getContents())
                    .price(findBoard.getPrice())
                    .place(findBoard.getPlace())
                    .category(findBoard.getCategory())
                    .createdAt(findBaseBoard.getCreatedAt())
                    .firstDate(findBaseBoard.getFirstDate())
                    .lastDate(findBaseBoard.getLastDate())
                    .titlePath(findBaseBoard.getTitlePath())
                    .detailPath(findBaseBoard.getDetailPaths())
                    .userRank(findUser.getUserRank())
                    .build()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public List<ModelBoard> findAll() {
        return modelNeedRepository.findAllByBigCategory(BigCategory.해줘);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> update(String token, UpdateModelDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            ModelBoard findBoard = modelNeedRepository
                    .findByBidAndBaseBoard_Email(request.getId(), request.getEmail());

            BaseBoard baseBoard = findBoard.getBaseBoard();

            String titlePath = updateImgFile(title, baseBoard.getTitlePath());
            List<String> detailsPaths = updateImgFiles(details, baseBoard.getDetailPaths());

            BaseBoard updateBaseBoard = BaseBoard.builder()
                    .email(request.getEmail())
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .createdAt(baseBoard.getCreatedAt())
                    .firstDate(request.getFirstDate())
                    .lastDate(request.getLastDate())
                    .titlePath(titlePath)
                    .detailPaths(detailsPaths)
                    .build();

            findBoard.updateBoard(updateBaseBoard, request.getCategory(), request.getPlace(), request.getPrice());

            modelNeedRepository.save(findBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 수정됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }

    @Transactional
    public ResponseEntity<Map<String, String>> delete(String token, BoardRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            modelNeedRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }
}
