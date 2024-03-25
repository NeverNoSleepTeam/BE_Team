package NS.pgmg.service.board;

import NS.pgmg.domain.board.Base;
import NS.pgmg.domain.board.ModelAssistance;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import NS.pgmg.dto.board.ModelAssistanceFindResponseDto;
import NS.pgmg.dto.board.ModelAssistanceUpdateDto;
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
    public ResponseEntity<Map<String, String>> createMA(String token, ModelAssistanceCreateDto request,
                                                        MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            String titleValue = saveFile(title);
            List<String> detailsValue = saveFiles(details);

            Base base = Base.setBaseBoard(request, requestEmail, titleValue, detailsValue);
            ModelAssistance modelAssistance = ModelAssistance.builder()
                    .base(base)
                    .modelAssistanceCategory(request.getModelAssistanceCategory())
                    .place(request.getPlace())
                    .price(request.getPrice())
                    .build();
            modelAssistanceRepository.save(modelAssistance);

            return ResponseEntity.ok().body(Map.of("message", "게시물이 생성됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> findMA(BoardRequestDto request) {
        try {
            User findUser = userRepository.findByEmail(request.getEmail());
            ModelAssistance findBoard = modelAssistanceRepository.
                    findByBidAndBase_Email(request.getId(), request.getEmail());

            Base findBase = findBoard.getBase();

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
    public List<ModelAssistance> findAllMA() {
        return modelAssistanceRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateMA(String token, ModelAssistanceUpdateDto request,
                                                     MultipartFile title, List<MultipartFile> details) {
        try {
            String requestEmail = tokenCheck(token);

            emailCheck(requestEmail, request.getEmail());

            ModelAssistance findBoard = modelAssistanceRepository
                    .findByBidAndBase_Email(request.getId(), request.getEmail());

            Base base = findBoard.getBase();

            String titlePath = updateFile(title, base.getTitlePath());
            List<String> detailsPaths = updateFiles(details, base.getDetailPaths());

            Base updateBase = Base.builder()
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

            modelAssistanceRepository.save(findBoard);

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

            modelAssistanceRepository.deleteByBid(request.getId());
            return ResponseEntity.ok().body(Map.of("message", "게시물이 삭제됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

    }
}
