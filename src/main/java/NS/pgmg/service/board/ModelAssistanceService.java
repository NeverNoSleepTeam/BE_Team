package NS.pgmg.service.board;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.BigCategory;
import NS.pgmg.domain.board.ModelBoard;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.CreateModelDto;
import NS.pgmg.dto.board.FindModelAssistanceResponseDto;
import NS.pgmg.dto.board.UpdateModelDto;
import NS.pgmg.repository.board.ModelAssistanceRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static NS.pgmg.service.CommonMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelAssistanceService {

    private final ModelAssistanceRepository modelAssistanceRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> create(String token, CreateModelDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());

            User findUser = userRepository.findByEmail(request.getEmail());

            if (!findUser.isModel()) {
                return ResponseEntity.badRequest().body(Map.of("message", "권한이 없습니다."));
            }

            String titleValue = updateImgFile(title, null);
            List<String> detailsValue = updateImgFiles(details, null);

            BaseBoard baseBoard = BaseBoard.setModelBase(request, requestEmail, titleValue, detailsValue);
            ModelBoard modelBoard = ModelBoard.builder()
                    .baseBoard(baseBoard)
                    .modelCategory(request.getModelCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .bigCategory(BigCategory.해줄게)
                    .build();
            modelAssistanceRepository.save(modelBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> find(BoardRequestDto request) {
        try {
            User findUser = userRepository.findByEmail(request.getEmail());
            ModelBoard findBoard = modelAssistanceRepository.
                    findByBidAndBaseBoard_Email(request.getId(), request.getEmail());

            BaseBoard findBaseBoard = findBoard.getBaseBoard();

            return ResponseEntity.ok().body(FindModelAssistanceResponseDto.builder()
                    .name(findUser.getName())
                    .createdAt(findBaseBoard.getCreatedAt())
                    .title(findBaseBoard.getTitle())
                    .contents(findBaseBoard.getContents())
                    .price(findBoard.getPrice())
                    .place(findBoard.getPlace())
                    .modelCategory(findBoard.getModelCategory())
                    .firstDate(findBaseBoard.getFirstDate())
                    .lastDate(findBaseBoard.getLastDate())
                    .gender(findUser.getGender())
                    .height(findUser.getHeight())
                    .weight(findUser.getWeight())
                    .top(findUser.getTop())
                    .bottom(findUser.getBottom())
                    .shoes(findUser.getShoes())
                    .nationality(findUser.getNationality())
                    .city(findUser.getCity())
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
        return modelAssistanceRepository.findAllByBigCategory(BigCategory.해줄게);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> update(String token, UpdateModelDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            ModelBoard findBoard = modelAssistanceRepository
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

            findBoard.updateBoard(updateBaseBoard, request.getModelCategory(), request.getPlace(), request.getPrice());

            modelAssistanceRepository.save(findBoard);

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

            modelAssistanceRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }
}
