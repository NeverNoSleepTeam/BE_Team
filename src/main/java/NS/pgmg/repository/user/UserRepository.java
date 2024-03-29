package NS.pgmg.repository.user;

import NS.pgmg.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByName(String name);
}
