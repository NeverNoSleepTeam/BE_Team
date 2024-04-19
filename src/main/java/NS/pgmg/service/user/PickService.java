package NS.pgmg.service.user;

import NS.pgmg.domain.user.User;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickService {

    private final UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAllByIsModelOrderByUidDesc(true);
    }
}
