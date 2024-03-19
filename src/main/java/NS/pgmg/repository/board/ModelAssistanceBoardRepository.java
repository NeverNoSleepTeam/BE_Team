package NS.pgmg.repository.board;

import NS.pgmg.domain.board.ModelAssistanceBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelAssistanceBoardRepository extends JpaRepository<ModelAssistanceBoard, Long> {
    public ModelAssistanceBoard findByBidAndBase_Email(Long bid, String email);
    public void deleteByBid(Long bid);
}
