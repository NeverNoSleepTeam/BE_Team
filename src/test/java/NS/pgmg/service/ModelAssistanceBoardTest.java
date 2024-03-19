package NS.pgmg.service;

import NS.pgmg.domain.board.BaseBoard;
import NS.pgmg.domain.board.ModelAssistanceBoard;
import NS.pgmg.domain.board.ModelAssistanceCategory;
import NS.pgmg.repository.board.ModelAssistanceBoardRepository;
import lombok.extern.slf4j.Slf4j;
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
public class ModelAssistanceBoardTest {

    @Autowired
    ModelAssistanceBoardRepository modelAssistanceBoardRepository;

    @Test
    @Transactional
    void createModelAssistanceBoardTest() {

        BaseBoard base = BaseBoard.builder()
                .email("email")
                .title("title")
                .contents("contents")
                .createdAt(LocalDateTime.now())
                .titlePath("titlePath")
                .detailPaths(Collections.singletonList("파일이 없습니다."))
                .firstDate("firstDate")
                .lastDate("lastDate")
                .build();

        List<ModelAssistanceCategory> modelAssistanceCategory = Arrays.asList(new ModelAssistanceCategory[]{
                ModelAssistanceCategory.손, ModelAssistanceCategory.얼굴});

        ModelAssistanceBoard modelAssistanceBoard = ModelAssistanceBoard.builder()
                .base(base)
                .modelAssistanceCategory(modelAssistanceCategory)
                .price(10000)
                .place("place")
                .build();

        modelAssistanceBoardRepository.save(modelAssistanceBoard);

        ModelAssistanceBoard findBoard = modelAssistanceBoardRepository.findByBidAndBase_Email(1L, "email");

        assertThat(modelAssistanceBoard).isEqualTo(findBoard);
    }
}
