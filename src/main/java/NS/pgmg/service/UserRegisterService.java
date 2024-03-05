package NS.pgmg.service;

import NS.pgmg.domain.user.User;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.exception.PasswordMismatchException;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> createBasicUser(BasicRegisterDto request) {

        try {
            passwdValidation(request.getPasswd(), request.getPasswd2());
            basicUserEmailDuplicateCheck(request.getEmail());
            basicUserNameDuplicateCheck(request.getName());
        } catch (PasswordMismatchException | EmailDuplicateException | NameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .passwd(request.getPasswd())
                .gender(request.getGender())
                .intro(request.getIntro())
                .socialTF(false)
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("일반 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createModelUser(ModelRegisterDto request) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        findUser.setModelInfo(request);
        userRepository.save(findUser);
        return new ResponseEntity<>("모델 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createProPhotoUser(ProPhotoRegisterDto request) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        findUser.setProPhotoInfo(request);
        userRepository.save(findUser);
        return new ResponseEntity<>("기사 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    public void passwdValidation(String passwd, String passwd2) {
        if (!passwd.equals(passwd2)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserEmailDuplicateCheck(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            throw new EmailDuplicateException("이미 존재하는 이메일입니다.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserNameDuplicateCheck(String name) {
        User findUser = userRepository.findByEmail(name);
        if (findUser != null) {
            throw new NameDuplicateException("이미 존재하는 닉네임입니다.");
        }
    }
}

