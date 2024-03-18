package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.FindUserPageRequestDto;
import NS.pgmg.dto.userpage.FindUserPageResponseDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.exception.PasswordMismatchException;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${pdf.path}")
    private String filePath;

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
                .city(request.getCity())
                .nationality(request.getNationality())
                .intro(request.getIntro())
                .socialTF(false)
                .build();

        userRepository.save(user);
        log.info("일반 회원가입 완료, email = {}", request.getEmail());

        return new ResponseEntity<>("일반 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createModelUser(ModelRegisterDto request) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.CREATED);
        }
        findUser.setModelInfo(request);
        userRepository.save(findUser);
        log.info("모델 회원가입 완료, email = {}", request.getEmail());
        return new ResponseEntity<>("모델 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createProPhotoUser(ProPhotoRegisterDto request, MultipartFile file) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        if (findUser == null){
            return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
        String savedPath = null;
        try {
            savedPath = emptyFileCheck(file, email);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        findUser.setProPhotoInfo(request, savedPath);
        userRepository.save(findUser);
        log.info("기사 회원가입 완료, email = {}", request.getEmail());
        return new ResponseEntity<>("기사 회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<String> login(LoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (!request.getEmail().equals(findUser.getEmail())) {
            return new ResponseEntity<>("이메일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if (!request.getPasswd().equals(findUser.getPasswd())) {
            return new ResponseEntity<>("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            log.info("로그인 이메일 = {}", requestEmail);
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<String> socialLogin(SocialRegisterAndLoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (findUser == null) {
            User user = User.builder()
                    .email(requestEmail)
                    .socialTF(true)
                    .build();
            userRepository.save(user);
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        } else if (findUser.isSocialTF()) {
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("일반 유저이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public FindUserPageResponseDto findUserPage(FindUserPageRequestDto request) {

        User findUser = userRepository.findByName(request.getName());

        return FindUserPageResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .baseUser("일반유저")
                .intro(findUser.getIntro())
                .build();
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

    private String emptyFileCheck(MultipartFile file, String email) throws IOException {

        if (file == null) {
            return "파일이 없습니다.";
        }

        String fullPath = filePath + email + ".pdf";
        String savedPath = "/portfolio/" + email + ".pdf";

        try {
            file.transferTo(new File(fullPath));
            log.info("PDF 저장 = {}", savedPath);
        } catch (IOException e) {
            log.warn("message = PDF 저장 오류");
            throw new IOException("PDF 저장 오류");
        }

        return savedPath;
    }
}
