package NS.pgmg.service;

import NS.pgmg.domain.user.User;
import NS.pgmg.domain.user.enums.*;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void findByEmailTest() {
        User user = User.builder()
                .email("email")
                .name("name")
                .passwd("passwd")
                .gender(Gender.남성)
                .intro("intro")
                .build();

        userRepository.save(user);
        User findUser = userRepository.findByEmail("email");

        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @Transactional
    void createModelUserTest() {
        User user = User.builder()
                .email("email")
                .name("name")
                .passwd("passwd")
                .gender(Gender.남성)
                .intro("intro")
                .build();

        userRepository.save(user);

        ModelRegisterDto modelRegisterDto = ModelRegisterDto.builder()
                .email("email")
                .height("height")
                .weight("weight")
                .top("top")
                .bottom("bottom")
                .shoes("shoes")
                .nationality(Nationality.내국인)
                .city(City.강원)
                .build();

        user.setModelInfo(modelRegisterDto);
        userRepository.save(user);

        List<User> all = userRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
        assertThat(user.getCity()).isEqualTo(City.강원);
    }

    @Test
    @Transactional
    void createProPhotoUser() {
        User user = User.builder()
                .email("email")
                .name("name")
                .passwd("passwd")
                .gender(Gender.남성)
                .intro("intro")
                .build();

        userRepository.save(user);

        ProPhotoRegisterDto proPhotoRegisterDto = ProPhotoRegisterDto.builder()
                .businessTrip(BusinessTrip.가능)
                .correction(Correction.가능)
                .production(Production.가능)
                .portfolioURL("url")
                .build();

        user.setProPhotoInfo(proPhotoRegisterDto);
        userRepository.save(user);

        List<User> all = userRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
        assertThat(user.getBusinessTrip()).isEqualTo(BusinessTrip.가능);
    }
}
