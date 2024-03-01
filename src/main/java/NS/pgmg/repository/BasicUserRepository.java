package NS.pgmg.repository;

import NS.pgmg.domain.user.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long> {
}
