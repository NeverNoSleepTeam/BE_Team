package NS.pgmg.service.board;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.PhotoShopBoard;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.*;
import NS.pgmg.repository.board.PhotoShopRepository;
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
public class PhotoShopService {

    private final PhotoShopRepository photoShopRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> create(String token, CreatePhotoShopDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            String titleValue = saveFile(title);
            List<String> detailsValue = saveFiles(details);

            BaseBoard baseBoard = BaseBoard.setPhotoShopBase(request, requestEmail, titleValue, detailsValue);
            PhotoShopBoard photoShopBoard = PhotoShopBoard.builder()
                    .baseBoard(baseBoard)
                    .photoShopCategory(request.getPhotoShopCategory())
                    .price(request.getPrice())
                    .build();
            photoShopRepository.save(photoShopBoard);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> find(BoardRequestDto request) {
        try {
            User findUser = userRepository.findByEmail(request.getEmail());
            PhotoShopBoard findBoard = photoShopRepository.
                    findByBidAndBase_Email(request.getId(), request.getEmail());

            BaseBoard findBaseBoard = findBoard.getBaseBoard();

            return ResponseEntity.ok().body(FindPhotoShopResponseDto.builder()
                    .name(findUser.getName())
                    .title(findBaseBoard.getTitle())
                    .contents(findBaseBoard.getContents())
                    .price(findBoard.getPrice())
                    .photoShopCategory(findBoard.getPhotoShopCategory())
                    .createdAt(findBaseBoard.getCreatedAt())
                    .firstDate(findBaseBoard.getFirstDate())
                    .lastDate(findBaseBoard.getLastDate())
                    .titlePath(findBaseBoard.getTitlePath())
                    .detailPath(findBaseBoard.getDetailPaths())
                    .build()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public List<PhotoShopBoard> findAll() {
        return photoShopRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Map<String, String>> update(String token, UpdatePhotoShopDto request,
                                                      MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            PhotoShopBoard findBoard = photoShopRepository
                    .findByBidAndBase_Email(request.getId(), request.getEmail());

            BaseBoard baseBoard = findBoard.getBaseBoard();

            String titlePath = updateFile(title, baseBoard.getTitlePath());
            List<String> detailsPaths = updateFiles(details, baseBoard.getDetailPaths());

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

            findBoard.updateBoard(updateBaseBoard, request.getPhotoShopCategory(), request.getPrice());

            photoShopRepository.save(findBoard);

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

            photoShopRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }
}
