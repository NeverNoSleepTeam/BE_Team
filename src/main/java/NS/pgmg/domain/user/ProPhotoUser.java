package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.BusinessTrip;
import NS.pgmg.domain.user.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ProPhotoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private BusinessTrip businessTrip;

    @NotBlank
    private String portfolioURL;

    @Builder
    public ProPhotoUser(String email, String name, Gender gender, String intro,
                        BusinessTrip businessTrip, String portfolioURL) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
        this.businessTrip = businessTrip;
        this.portfolioURL = portfolioURL;
    }
}
