package NS.pgmg.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class PhotoShopBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private BaseBoard baseBoard;

    private List<PhotoShopCategory> photoShopCategory;

    private Integer price;

    @Builder
    public PhotoShopBoard(BaseBoard baseBoard, List<PhotoShopCategory> photoShopCategory, Integer price) {
        this.baseBoard = baseBoard;
        this.photoShopCategory = photoShopCategory;
        this.price = price;
    }

    public void updateBoard(BaseBoard baseBoard, List<PhotoShopCategory> photoShopCategory, Integer price) {
        this.baseBoard = baseBoard;
        this.photoShopCategory = photoShopCategory;
        this.price = price;
    }
}
