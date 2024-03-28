package NS.pgmg.service;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.ModelBoard;
import NS.pgmg.domain.board.ModelCategory;
import NS.pgmg.repository.board.ModelAssistanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ModelBoardTest {

    @Autowired
    ModelAssistanceRepository modelAssistanceRepository;

    @Test
    @Transactional
    void createModelAssistanceBoardTest() {

        BaseBoard baseBoard = BaseBoard.builder()
                .email("email")
                .title("title")
                .contents("contents")
                .createdAt(LocalDateTime.now())
                .titlePath("titlePath")
                .detailPaths(Collections.singletonList("파일이 없습니다."))
                .firstDate("firstDate")
                .lastDate("lastDate")
                .build();

        List<ModelCategory> modelCategory = Arrays.asList(new ModelCategory[]{
                ModelCategory.손, ModelCategory.얼굴});

        ModelBoard modelBoard = ModelBoard.builder()
                .baseBoard(baseBoard)
                .modelCategory(modelCategory)
                .price(10000)
                .place("place")
                .build();

        modelAssistanceRepository.save(modelBoard);

        ModelBoard findBoard = modelAssistanceRepository.findByBidAndBaseBoard_Email(1L, "email");

        assertThat(modelBoard).isEqualTo(findBoard);
    }
}
