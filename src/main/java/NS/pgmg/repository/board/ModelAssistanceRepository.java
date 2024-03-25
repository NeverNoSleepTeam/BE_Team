package NS.pgmg.repository.board;

import NS.pgmg.domain.board.ModelAssistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelAssistanceRepository extends JpaRepository<ModelAssistance, Long> {
    public ModelAssistance findByBidAndBase_Email(Long bid, String email);
    public void deleteByBid(Long bid);
}
