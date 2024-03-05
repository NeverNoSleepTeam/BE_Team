package NS.pgmg.service;

import NS.pgmg.domain.user.BasicUser;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.repository.user.BasicUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class FindUserTest {

    @Autowired
    BasicUserRepository basicUserRepository;

    @Test
    @Transactional
    void findByEmailTest() {
        BasicUser basicUser = BasicUser.builder()
                .email("email")
                .name("name")
                .passwd("passwd")
                .gender(Gender.남성)
                .intro("intro")
                .build();

        basicUserRepository.save(basicUser);
        BasicUser findBasicUser = basicUserRepository.findByEmail("email");

        Assertions.assertThat(basicUser).isEqualTo(findBasicUser);
    }
}
