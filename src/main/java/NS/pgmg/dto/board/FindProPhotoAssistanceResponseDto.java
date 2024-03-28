package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ProPhotoCategory;
import NS.pgmg.domain.user.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindProPhotoAssistanceResponseDto {
    private String name;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ProPhotoCategory> proPhotoCategory;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private Gender gender;
    private Nationality nationality;
    private City city;
    private BusinessTrip businessTrip;
    private Correction correction;
    private Production production;
    private String URL;
    private String portfolioPath;
    private String titlePath;
    private List<String> detailPath;
    private UserRank userRank;

    @Builder
    public FindProPhotoAssistanceResponseDto(String name, String title, String contents, Integer price, String place,
                                             List<ProPhotoCategory> proPhotoCategory, LocalDateTime createdAt,
                                             String firstDate, String lastDate, Gender gender, Nationality nationality,
                                             City city, BusinessTrip businessTrip, Correction correction,
                                             Production production, String URL, String portfolioPath, String titlePath,
                                             List<String> detailPath, UserRank userRank) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.proPhotoCategory = proPhotoCategory;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.gender = gender;
        this.nationality = nationality;
        this.city = city;
        this.businessTrip = businessTrip;
        this.correction = correction;
        this.production = production;
        this.URL = URL;
        this.portfolioPath = portfolioPath;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
        this.userRank = userRank;
    }
}
