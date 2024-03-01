package NS.pgmg.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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
