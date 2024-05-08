package NS.pgmg.domain.board;

import NS.pgmg.domain.board.enums.BigCategory;
import NS.pgmg.domain.board.enums.ModelCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ModelBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private BaseBoard baseBoard;

    @Enumerated(EnumType.STRING)
    private BigCategory bigCategory;

    @ElementCollection
    private List<ModelCategory> category;

    private String place;

    private Integer price;

    @Builder
    public ModelBoard(BaseBoard baseBoard, List<ModelCategory> category, String place, Integer price,
                      BigCategory bigCategory) {
        this.baseBoard = baseBoard;
        this.category = category;
        this.place = place;
        this.price = price;
        this.bigCategory = bigCategory;
    }

    public void updateBoard(BaseBoard baseBoard, List<ModelCategory> modelCategory, String place, Integer price) {
        this.baseBoard = baseBoard;
        this.category = modelCategory;
        this.place = place;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ModelAssistanceBoard{" +
                "bid=" + bid +
                ", base=" + baseBoard +
                ", modelCategory=" + category +
                ", place='" + place + '\'' +
                ", price=" + price +
                '}';
    }
}