package NS.pgmg.repository.board;

import NS.pgmg.domain.board.ProPhotoBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProPhotoNeedRepository extends JpaRepository<ProPhotoBoard, Long> {
    public ProPhotoBoard findByBidAndBase_Email(Long bid, String email);
    public void deleteByBid(Long bid);
}
