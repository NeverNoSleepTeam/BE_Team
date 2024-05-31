package NS.pgmg.domain.user;

import NS.pgmg.domain.user.enums.*;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.UpdateBasicInfoRequestDto;
import NS.pgmg.dto.userpage.UpdateModelInfoRequestDto;
import NS.pgmg.dto.userpage.UpdateProPhotoInfoRequestDto;
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

    private String profileMainImg;

    @ElementCollection
    private List<String> profileBasicImgPath;

    @Enumerated(EnumType.STRING)
    private UserRank userRank;

    private boolean isSocial;

    // ---------------------------------- Model Info ----------------------------------
    private String height;

    private String weight;

    private String top;

    private String bottom;

    private String shoes;

    @ElementCollection
    private List<String> profileModelImgPath;

    private boolean isModel;

    // ---------------------------------- ProPhoto Info ----------------------------------
    @Enumerated(EnumType.STRING)
    private BusinessTrip businessTrip;

    @Enumerated(EnumType.STRING)
    private Correction correction;

    @Enumerated(EnumType.STRING)
    private Production production;

    @ElementCollection
    private List<String> profileProPhotoImgPath;

    private String URL;

    private String portfolioPath;

    private boolean isProPhoto;


    @Builder
    public User(String email, String passwd, String name, Gender gender, City city, Nationality nationality,
                String intro, UserRank userRank, boolean isSocial) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
        this.city = city;
        this.intro = intro;
        this.userRank = userRank;
        this.isSocial = isSocial;
    }

    public void setModelInfo(ModelRegisterDto modelInfo) {
        this.height = modelInfo.getHeight();
        this.weight = modelInfo.getWeight();
        this.top = modelInfo.getTop();
        this.bottom = modelInfo.getBottom();
        this.shoes = modelInfo.getShoes();
        this.isModel = true;
        this.userRank = UserRank.모델회원;
    }

    public void setProPhotoInfo(ProPhotoRegisterDto proPhotoInfo, String filePath) {
        this.businessTrip = proPhotoInfo.getBusinessTrip();
        this.correction = proPhotoInfo.getCorrection();
        this.production = proPhotoInfo.getProduction();
        this.URL = proPhotoInfo.getURL();
        this.portfolioPath = filePath;
        this.isProPhoto = true;
        this.userRank = UserRank.사진기사회원;
    }

    public void updateBasicInfo(UpdateBasicInfoRequestDto basicInfo, String profileMainImg) {
        this.gender = basicInfo.getGender();
        this.nationality = basicInfo.getNationality();
        this.city = basicInfo.getCity();
        this.intro = basicInfo.getIntro();
        this.profileMainImg = profileMainImg;
        this.userRank = UserRank.일반회원;
    }

    public void updateModelInfo(UpdateModelInfoRequestDto modelInfo, String profileMainImg) {
        this.gender = modelInfo.getGender();
        this.nationality = modelInfo.getNationality();
        this.city = modelInfo.getCity();
        this.intro = modelInfo.getIntro();
        this.height = modelInfo.getHeight();
        this.weight = modelInfo.getWeight();
        this.top = modelInfo.getTop();
        this.bottom = modelInfo.getBottom();
        this.shoes = modelInfo.getShoes();
        this.profileMainImg = profileMainImg;
        this.userRank = UserRank.모델회원;
        this.isModel = true;
    }

    public void updateProPhotoInfo(UpdateProPhotoInfoRequestDto proPhotoInfo, String profileMainImg,
                                   String portfolioPath) {
        this.gender = proPhotoInfo.getGender();
        this.nationality = proPhotoInfo.getNationality();
        this.city = proPhotoInfo.getCity();
        this.intro = proPhotoInfo.getIntro();
        this.businessTrip = proPhotoInfo.getBusinessTrip();
        this.correction = proPhotoInfo.getCorrection();
        this.production = proPhotoInfo.getProduction();
        this.URL = proPhotoInfo.getURL();
        this.portfolioPath = portfolioPath;
        this.profileMainImg = profileMainImg;
        this.userRank = UserRank.사진기사회원;
        this.isProPhoto = true;
    }

    public void updateRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public void changeBasicImg(List<String> profileBasicImgPath) {
        this.profileBasicImgPath = profileBasicImgPath;
    }

    public void changeModelImg(List<String> profileModelImgPath) {
        this.profileModelImgPath = profileModelImgPath;
    }

    public void changeProPhotoImg(List<String> profileProPhotoImgPath) {
        this.profileProPhotoImgPath = profileProPhotoImgPath;
    }
}
