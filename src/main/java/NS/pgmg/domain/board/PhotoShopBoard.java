package NS.pgmg.domain.board;

import NS.pgmg.domain.board.enums.PhotoShopCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PhotoShopBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private BaseBoard baseBoard;

    private PhotoShopCategory category;

    private Integer price;

    @Builder
    public PhotoShopBoard(BaseBoard baseBoard, PhotoShopCategory category, Integer price) {
        this.baseBoard = baseBoard;
        this.category = category;
        this.price = price;
    }

    public void updateBoard(BaseBoard baseBoard, PhotoShopCategory photoShopCategory, Integer price) {
        this.baseBoard = baseBoard;
        this.category = photoShopCategory;
        this.price = price;
    }
}
