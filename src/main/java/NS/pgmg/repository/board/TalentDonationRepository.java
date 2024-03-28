package NS.pgmg.repository.board;

import NS.pgmg.domain.board.TalentDonationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalentDonationRepository extends JpaRepository<TalentDonationBoard, Long> {
    public TalentDonationBoard findByBidAndBase_Email(Long bid, String email);
    public void deleteByBid(Long bid);
}
