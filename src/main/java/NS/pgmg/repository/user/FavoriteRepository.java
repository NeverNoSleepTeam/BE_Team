package NS.pgmg.repository.user;

import NS.pgmg.domain.user.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    public void deleteByEmailAndName(String email, String fName);

    public List<Favorite> findAllByEmail(String email);
}
