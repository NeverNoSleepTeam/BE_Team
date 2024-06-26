package NS.pgmg.controller.user;

import NS.pgmg.domain.user.User;
import NS.pgmg.service.user.PickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Pick", description = "추천 Pick 관련 API")
@RestController
@RequiredArgsConstructor
public class PickController {

    private final PickService pickService;

    @Operation(summary = "추천 Pick")
    @GetMapping("/pick")
    public List<User> findAllUser() {
        log.info("Call PickController.findAllUser");
        return pickService.findAllUser();
    }
}
