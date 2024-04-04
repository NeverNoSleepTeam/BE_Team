package NS.pgmg.domain.board;

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

    private List<ModelCategory> modelCategory;

    private String place;

    private Integer price;

    @Builder
    public ModelBoard(BaseBoard baseBoard, List<ModelCategory> modelCategory, String place, Integer price,
                      BigCategory bigCategory) {
        this.baseBoard = baseBoard;
        this.modelCategory = modelCategory;
        this.place = place;
        this.price = price;
        this.bigCategory = bigCategory;
    }

    public void updateBoard(BaseBoard baseBoard, List<ModelCategory> modelCategory, String place, Integer price) {
        this.baseBoard = baseBoard;
        this.modelCategory = modelCategory;
        this.place = place;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ModelAssistanceBoard{" +
                "bid=" + bid +
                ", base=" + baseBoard +
                ", modelCategory=" + modelCategory +
                ", place='" + place + '\'' +
                ", price=" + price +
                '}';
    }
}