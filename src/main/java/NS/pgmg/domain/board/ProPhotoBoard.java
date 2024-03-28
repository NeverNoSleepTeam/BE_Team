package NS.pgmg.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ProPhotoBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private BaseBoard baseBoard;

    private List<ProPhotoCategory> proPhotoCategory;

    private String place;

    private Integer price;

    @Builder
    public ProPhotoBoard(BaseBoard baseBoard, List<ProPhotoCategory> proPhotoCategory,
                         String place, Integer price) {
        this.baseBoard = baseBoard;
        this.proPhotoCategory = proPhotoCategory;
        this.place = place;
        this.price = price;
    }

    public void updateBoard(BaseBoard baseBoard, List<ProPhotoCategory> proPhotoCategory,
                            String place, Integer price) {
        this.baseBoard = baseBoard;
        this.proPhotoCategory = proPhotoCategory;
        this.place = place;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ModelAssistanceBoard{" +
                "bid=" + bid +
                ", base=" + baseBoard +
                ", proPhotoCategory=" + proPhotoCategory +
                ", place='" + place + '\'' +
                ", price=" + price +
                '}';
    }
}