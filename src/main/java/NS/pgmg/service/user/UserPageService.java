package NS.pgmg.service.user;

import NS.pgmg.domain.user.User;
import NS.pgmg.dto.userpage.*;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static NS.pgmg.service.CommonMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPageService {

    private final UserRepository userRepository;

    /**
     * 일반 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findBasicInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindBasicInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .profileBasicImgPath(findUser.getProfileBasicImgPath())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 모델 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findModelInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindModelInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .profileModelImgPath(findUser.getProfileModelImgPath())
                .height(findUser.getHeight())
                .weight(findUser.getWeight())
                .top(findUser.getTop())
                .bottom(findUser.getBottom())
                .shoes(findUser.getShoes())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 사진기사 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findProPhotoInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindProPhotoInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .businessTrip(findUser.getBusinessTrip())
                .correction(findUser.getCorrection())
                .production(findUser.getProduction())
                .profileProPhotoImgPath(findUser.getProfileProPhotoImgPath())
                .portfolioPath(findUser.getPortfolioPath())
                .portfolioURL(findUser.getURL())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 전체 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findAllInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }
        return ResponseEntity.ok().body(Map.of("requestBody", findUser, "self", isSelf));
    }

    /**
     * 일반 정보 수정
     */
    @Transactional
    public ResponseEntity<Map<String, String>> updateBasicInfo(String token, MultipartFile file,
                                                               UpdateBasicInfoRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            String profileImg = updateImgFile(file, findUser.getProfileMainImg());
            findUser.updateBasicInfo(request, profileImg);
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateModelInfo(String token, MultipartFile file,
                                                               UpdateModelInfoRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            String profileImg = updateImgFile(file, findUser.getProfileMainImg());
            findUser.updateModelInfo(request, profileImg);
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateProPhotoInfo(String token, UpdateProPhotoInfoRequestDto request,
                                                                  MultipartFile img, MultipartFile pdf) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            String profileImg = updateImgFile(img, findUser.getProfileMainImg());
            findUser.updateProPhotoInfo(request, profileImg, updatePDF(pdf, findUser.getPortfolioPath()));
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String,String>> changeBasicImg(String token, FindByEmailDto request,
                                                             List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeBasicImg(updateImgFiles(files, findUser.getProfileBasicImgPath()));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> changeModelImg(String token, FindByEmailDto request,
                                                              List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeModelImg(updateImgFiles(files, findUser.getProfileModelImgPath()));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> changeProPhotoImg(String token, FindByEmailDto request,
                                                                 List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeProPhotoImg(updateImgFiles(files, findUser.getProfileProPhotoImgPath()));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }
}

