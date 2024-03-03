package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ModelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @NotBlank
    private String height;

    @NotBlank
    private String weight;

    @NotBlank
    private String top;

    @NotBlank
    private String bottom;

    @NotBlank
    private String shoes;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private City city;

    @Builder
    public ModelUser(String email, Gender gender, String intro, String height, String weight,
                     String top, String bottom, String shoes, Nationality nationality, City city) {
        this.email = email;
        this.gender = gender;
        this.intro = intro;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.nationality = nationality;
        this.city = city;
    }
}
