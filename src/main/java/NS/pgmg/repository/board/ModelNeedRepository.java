package NS.pgmg.repository.board;

import NS.pgmg.domain.board.ModelBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelNeedRepository extends JpaRepository<ModelBoard, Long> {
    public ModelBoard findByBidAndBaseBoard_Email(Long bid, String email);
    public void deleteByBid(Long bid);
}
