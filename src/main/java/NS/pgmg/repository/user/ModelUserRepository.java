package NS.pgmg.repository.user;

import NS.pgmg.domain.user.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelUserRepository extends JpaRepository<ModelUser, Long> {
    public ModelUser findByEmail(String email);

    public ModelUser findByName(String name);
}
