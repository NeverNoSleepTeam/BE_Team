package NS.pgmg.repository.board;

import NS.pgmg.domain.board.BigCategory;
import NS.pgmg.domain.board.ModelBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelAssistanceRepository extends JpaRepository<ModelBoard, Long> {
    public ModelBoard findByBidAndBaseBoard_Email(Long bid, String email);

    public List<ModelBoard> findAllByBigCategory(BigCategory bigCategory);

    public void deleteByBid(Long bid);
}
