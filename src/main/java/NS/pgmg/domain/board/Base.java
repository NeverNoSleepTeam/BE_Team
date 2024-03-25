package NS.pgmg.domain.board;

import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
public class Base {

    private String email;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    private String titlePath;

    @ElementCollection
    private List<String> detailPaths;

    private LocalDateTime createdAt;

    private String firstDate;

    private String lastDate;

    @Builder
    public Base(String email, String title, String contents,
                LocalDateTime createdAt, String firstDate, String lastDate,
                String titlePath, List<String> detailPaths) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.titlePath = titlePath;
        this.detailPaths = detailPaths;
    }

    public static Base setBaseBoard(ModelAssistanceCreateDto request, String email,
                                    String titlePath, List<String> detailPaths) {
        return Base.builder()
                .email(email)
                .title(request.getTitle())
                .contents(request.getContents())
                .titlePath(titlePath)
                .detailPaths(detailPaths)
                .createdAt(LocalDateTime.now())
                .firstDate(request.getFirstDate())
                .lastDate(request.getLastDate())
                .build();
    }
}
