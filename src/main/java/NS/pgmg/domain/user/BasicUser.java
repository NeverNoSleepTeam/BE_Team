package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class BasicUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String passwd;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Builder
    public BasicUser(String email, String passwd, String name, Gender gender, String intro) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
