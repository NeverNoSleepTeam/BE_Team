package NS.pgmg.repository.board;

import NS.pgmg.domain.board.PhotoShopBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoShopRepository extends JpaRepository<PhotoShopBoard, Long> {
    public PhotoShopBoard findByBidAndBaseBoard_Email(Long bid, String email);

    public void deleteByBid(Long bid);
}
