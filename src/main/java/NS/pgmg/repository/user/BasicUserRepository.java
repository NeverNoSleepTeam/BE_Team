package NS.pgmg.repository.user;

import NS.pgmg.domain.user.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long> {
    public BasicUser findByEmail(String email);

    public BasicUser findByName(String name);
}
