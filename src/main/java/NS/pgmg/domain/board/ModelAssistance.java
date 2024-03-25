package NS.pgmg.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class ModelAssistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Embedded
    private Base base;

    private List<ModelAssistanceCategory> modelAssistanceCategory;

    private String place;

    private Integer price;

    @Builder
    public ModelAssistance(Base base, List<ModelAssistanceCategory> modelAssistanceCategory,
                           String place, Integer price) {
        this.base = base;
        this.modelAssistanceCategory = modelAssistanceCategory;
        this.place = place;
        this.price = price;
    }

    public void setUpdateBoard(Base base, List<ModelAssistanceCategory> modelAssistanceCategory,
                               String place, Integer price) {
        this.base = base;
        this.modelAssistanceCategory = modelAssistanceCategory;
        this.place = place;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ModelAssistanceBoard{" +
                "bid=" + bid +
                ", base=" + base +
                ", modelAssistanceCategory=" + modelAssistanceCategory +
                ", place='" + place + '\'' +
                ", price=" + price +
                '}';
    }
}