package NS.pgmg.service;

import NS.pgmg.domain.user.BasicUser;
import NS.pgmg.domain.user.ModelUser;
import NS.pgmg.domain.user.ProPhotoUser;
import NS.pgmg.dto.BasicUserSignUpDto;
import NS.pgmg.dto.ModelUserSignUpDto;
import NS.pgmg.dto.ProPhotoUserSignUpDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.exception.PasswordMismatchException;
import NS.pgmg.repository.user.BasicUserRepository;
import NS.pgmg.repository.user.ModelUserRepository;
import NS.pgmg.repository.user.ProPhotoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserRegisterService {

    private final BasicUserRepository basicUserRepository;
    private final ModelUserRepository modelUserRepository;
    private final ProPhotoUserRepository proPhotoUserRepository;

    @Transactional
    public ResponseEntity<String> createBasicUser(BasicUserSignUpDto request) {

        try {
            passwdValidation(request.getPasswd(), request.getPasswd2());
            basicUserEmailDuplicateCheck(request.getEmail());
            basicUserNameDuplicateCheck(request.getName());
        } catch (PasswordMismatchException | EmailDuplicateException | NameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        BasicUser basicUser = BasicUser.builder()
                .email(request.getEmail())
                .name(request.getName())
                .passwd(request.getPasswd())
                .gender(request.getGender())
                .intro(request.getIntro())
                .build();

        basicUserRepository.save(basicUser);

        return new ResponseEntity<>("일반 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createModelUser(ModelUserSignUpDto request) {

        ModelUser modelUser = ModelUser.builder()
                .email(request.getEmail())
                .gender(request.getGender())
                .intro(request.getIntro())
                .height(request.getHeight())
                .weight(request.getWeight())
                .top(request.getTop())
                .bottom(request.getBottom())
                .shoes(request.getShoes())
                .nationality(request.getNationality())
                .city(request.getCity())
                .build();

        modelUserRepository.save(modelUser);

        return new ResponseEntity<>("모델 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createProPhotoUser(ProPhotoUserSignUpDto request) {

        ProPhotoUser proPhotoUser = ProPhotoUser.builder()
                .email(request.getEmail())
                .name(request.getName())
                .gender(request.getGender())
                .intro(request.getIntro())
                .businessTrip(request.getBusinessTrip())
                .portfolioURL(request.getPortfolioURL())
                .build();

        proPhotoUserRepository.save(proPhotoUser);

        return new ResponseEntity<>("기사 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    public void passwdValidation(String passwd, String passwd2) {
        if (!passwd.equals(passwd2)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserEmailDuplicateCheck(String email) {
        BasicUser findBasicUser = basicUserRepository.findByEmail(email);
        if (findBasicUser != null) {
            throw new EmailDuplicateException("이미 존재하는 이메일입니다.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserNameDuplicateCheck(String name) {
        BasicUser findBasicUser = basicUserRepository.findByEmail(name);
        if (findBasicUser != null) {
            throw new NameDuplicateException("이미 존재하는 닉네임입니다.");
        }
    }
}

