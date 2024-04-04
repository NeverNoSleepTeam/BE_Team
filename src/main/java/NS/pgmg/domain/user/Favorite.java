package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Nationality;
import NS.pgmg.domain.user.enums.UserRank;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRank fUserRank;

    private String fHeight;

    private String fWeight;

    private String fTop;

    private String fBottom;

    private String fShoes;

    @Enumerated(EnumType.STRING)
    private City fCity;

    @Enumerated(EnumType.STRING)
    private Nationality fNationality;

    @Builder
    public Favorite(String email, String name, UserRank fUserRank, String fHeight, String fWeight, String fTop,
                    String fBottom, String fShoes, City fCity, Nationality fNationality) {
        this.email = email;
        this.name = name;
        this.fUserRank = fUserRank;
        this.fHeight = fHeight;
        this.fWeight = fWeight;
        this.fTop = fTop;
        this.fBottom = fBottom;
        this.fShoes = fShoes;
        this.fCity = fCity;
        this.fNationality = fNationality;
    }
}
