package NS.pgmg.service.user;

import NS.pgmg.domain.user.Favorite;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.userpage.FindByNameDto;
import NS.pgmg.repository.user.FavoriteRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.tokenCheck;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> add(String token, FindByNameDto request) {
        try {
            String requestEmail = tokenCheck(token);

            User findUser = userRepository.findByName(request.getName());

            Favorite favorite = Favorite.builder()
                    .email(requestEmail)
                    .name(findUser.getName())
                    .fUserRank(findUser.getUserRank())
                    .fHeight(findUser.getHeight())
                    .fWeight(findUser.getWeight())
                    .fTop(findUser.getTop())
                    .fBottom(findUser.getBottom())
                    .fShoes(findUser.getShoes())
                    .fCity(findUser.getCity())
                    .fNationality(findUser.getNationality())
                    .build();

            favoriteRepository.save(favorite);

            return ResponseEntity.ok().body(Map.of("message", "좋아요 추가가 완료됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> delete(String token, FindByNameDto request) {
        try {
            String requestEmail = tokenCheck(token);

            favoriteRepository.deleteByEmailAndName(requestEmail, request.getName());

            return ResponseEntity.ok().body(Map.of("message", "좋아요 삭제가 완료됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> findAll(String token) {
        try {
            String requestEmail = tokenCheck(token);

            return ResponseEntity.ok().body(favoriteRepository.findAllByEmail(requestEmail));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
