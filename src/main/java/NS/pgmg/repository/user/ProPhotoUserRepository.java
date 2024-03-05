package NS.pgmg.repository.user;

import NS.pgmg.domain.user.ProPhotoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProPhotoUserRepository extends JpaRepository<ProPhotoUser, Long> {
    public ProPhotoUser findByEmail(String email);

    public ProPhotoUser findByName(String name);
}
