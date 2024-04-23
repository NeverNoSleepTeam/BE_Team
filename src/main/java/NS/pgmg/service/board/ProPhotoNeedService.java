package NS.pgmg.service.board;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.enums.BigCategory;
import NS.pgmg.domain.board.ProPhotoBoard;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.*;
import NS.pgmg.repository.board.ProPhotoNeedRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static NS.pgmg.service.CommonMethod.*;

@Service
@RequiredArgsConstructor
public class ProPhotoNeedService {

    private final ProPhotoNeedRepository proPhotoNeedRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> create(String token, CreateProPhotoDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            String titleValue = updateImgFile(title, null);
            List<String> detailsValue = updateImgFiles(details, null);

            BaseBoard baseBoard = BaseBoard.setProPhotoBase(request, requestEmail, titleValue, detailsValue);
            ProPhotoBoard proPhotoBoard = ProPhotoBoard.builder()
                    .baseBoard(baseBoard)
                    .proPhotoCategory(request.getProPhotoCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .bigCategory(BigCategory.해줘)
                    .build();
            proPhotoNeedRepository.save(proPhotoBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> find(BoardRequestDto request) {
        try {
            User findUser = userRepository.findByEmail(request.getEmail());
            ProPhotoBoard findBoard = proPhotoNeedRepository.
                    findByBidAndBaseBoard_Email(request.getId(), request.getEmail());

            BaseBoard findBaseBoard = findBoard.getBaseBoard();

            return ResponseEntity.ok().body(FindProPhotoNeedResponseDto.builder()
                    .name(findUser.getName())
                    .title(findBaseBoard.getTitle())
                    .contents(findBaseBoard.getContents())
                    .price(findBoard.getPrice())
                    .place(findBoard.getPlace())
                    .proPhotoCategory(findBoard.getProPhotoCategory())
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
    public List<ProPhotoBoard> findAll() {
        return proPhotoNeedRepository.findAllByBigCategory(BigCategory.해줘);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> update(String token, UpdateProPhotoDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            ProPhotoBoard findBoard = proPhotoNeedRepository
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

            findBoard.updateBoard(updateBaseBoard, request.getProPhotoCategory(), request.getPlace(), request.getPrice());

            proPhotoNeedRepository.save(findBoard);

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

            proPhotoNeedRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }
}
