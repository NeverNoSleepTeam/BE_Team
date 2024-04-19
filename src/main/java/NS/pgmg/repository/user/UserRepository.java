package NS.pgmg.repository.user;

import NS.pgmg.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByName(String name);

    public List<User> findAllByIsModelOrderByUidDesc(boolean model);
}
