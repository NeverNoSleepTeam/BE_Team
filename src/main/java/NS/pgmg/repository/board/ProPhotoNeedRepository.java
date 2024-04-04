package NS.pgmg.repository.board;

import NS.pgmg.domain.board.BigCategory;
import NS.pgmg.domain.board.ProPhotoBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProPhotoNeedRepository extends JpaRepository<ProPhotoBoard, Long> {
    public ProPhotoBoard findByBidAndBaseBoard_Email(Long bid, String email);

    public List<ProPhotoBoard> findAllByBigCategory(BigCategory bigCategory);

    public void deleteByBid(Long bid);
}
