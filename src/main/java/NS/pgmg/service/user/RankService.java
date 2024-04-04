package NS.pgmg.service.user;

import NS.pgmg.domain.user.User;
import NS.pgmg.domain.user.enums.UserRank;
import NS.pgmg.dto.userpage.*;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {

    private final UserRepository userRepository;

    /**
     * 회원 등급 변경(일반)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeBasicRank(String token, FindByEmailDto request) {
        return changeRank(token, request, UserRank.일반회원);
    }

    /**
     * 회원 등급 변경(모델)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeModelRank(String token, FindByEmailDto request) {
        return changeRank(token, request, UserRank.모델회원);
    }

    /**
     * 회원 등급 변경(사진기사)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeProPhotoRank(String token, FindByEmailDto request) {
        return changeRank(token, request, UserRank.사진기사회원);

    }

    private ResponseEntity<Map<String, String>> changeRank(String token, FindByEmailDto request, UserRank userRank) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        findUser.updateRank(userRank);
        userRepository.save(findUser);
        return ResponseEntity.ok().body(Map.of("message", "프로필 전환이 완료됐습니다."));
    }
}
