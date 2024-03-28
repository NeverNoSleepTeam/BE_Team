package NS.pgmg.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class TalentDonationBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private BaseBoard baseBoard;

    private String place;


    @Builder
    public TalentDonationBoard(BaseBoard baseBoard, String place) {
        this.baseBoard = baseBoard;
        this.place = place;
    }

    public void updateBoard(BaseBoard baseBoard, String place) {
        this.baseBoard = baseBoard;
        this.place = place;
    }

    @Override
    public String toString() {
        return "ModelAssistanceBoard{" +
                "bid=" + bid +
                ", base=" + baseBoard +
                ", place='" + place + '\'' +
                '}';
    }
}