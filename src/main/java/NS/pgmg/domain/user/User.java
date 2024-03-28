package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.*;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.UpdateBasicInfoRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(unique = true)
    private String email;

    private String passwd;

    @Column(length = 10, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    private City city;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @ElementCollection
    private List<String> profileImgPath;

    private UserRank userRank;

    private boolean isSocial;

    // ---------------------------------- Model Info ----------------------------------
    private String height;

    private String weight;

    private String top;

    private String bottom;

    private String shoes;

    private boolean isModel;

    // ---------------------------------- ProPhoto Info ----------------------------------
    @Enumerated(EnumType.STRING)
    private BusinessTrip businessTrip;

    @Enumerated(EnumType.STRING)
    private Correction correction;

    @Enumerated(EnumType.STRING)
    private Production production;

    private String URL;

    private String portfolioPath;

    private boolean isProPhoto;


    @Builder
    public User(String email, String passwd, String name, Gender gender, City city, Nationality nationality,
                String intro, UserRank userRank, List<String> profileImgPath, boolean isSocial) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
        this.city = city;
        this.intro = intro;
        this.userRank = userRank;
        this.profileImgPath = profileImgPath;
        this.isSocial = isSocial;
    }

    public void setModelInfo(ModelRegisterDto modelInfo) {
        this.height = modelInfo.getHeight();
        this.weight = modelInfo.getWeight();
        this.top = modelInfo.getTop();
        this.bottom = modelInfo.getBottom();
        this.shoes = modelInfo.getShoes();
        this.isModel = true;
    }

    public void setProPhotoInfo(ProPhotoRegisterDto proPhotoInfo, String filePath) {
        this.businessTrip = proPhotoInfo.getBusinessTrip();
        this.correction = proPhotoInfo.getCorrection();
        this.production = proPhotoInfo.getProduction();
        this.URL = proPhotoInfo.getURL();
        this.portfolioPath = filePath;
        this.isProPhoto = true;
    }

    public void updateBasicInfo(UpdateBasicInfoRequestDto basicInfo) {
        this.passwd = basicInfo.getPasswd();
        this.gender = basicInfo.getGender();
        this.nationality = basicInfo.getNationality();
        this.city = basicInfo.getCity();
        this.intro = basicInfo.getIntro();
    }

    public void updateRank(UserRank userRank) {
        this.userRank = userRank;
    }
}
