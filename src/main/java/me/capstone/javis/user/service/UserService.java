package me.capstone.javis.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.jwt.JwtProvider;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.dto.request.SignInReqDto;
import me.capstone.javis.user.data.dto.request.SignUpReqDto;
import me.capstone.javis.user.data.dto.request.UserUpdateDto;
import me.capstone.javis.user.data.dto.response.UserResDto;
import me.capstone.javis.user.data.dto.response.calendar.CategoryAndAllTodoResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.CategoryAndTodosResDto;
import me.capstone.javis.user.data.dto.response.SignInResDto;
import me.capstone.javis.user.data.dto.response.SignUpResDto;
import me.capstone.javis.user.data.dto.response.userhomePage.TeamAndTeamTodosResDto;
import me.capstone.javis.user.data.repository.UserRepository;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public SignUpResDto getHomePageInfo(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        return SignUpResDto.toDto(user);
    }

    public SignUpResDto signUp(SignUpReqDto userReqDto) {

        User user = User.builder()
                .name(userReqDto.name())
                .loginId(userReqDto.loginId())
                .password(passwordEncoder.encode(userReqDto.password()))
                .email(userReqDto.email())
                //단일 권한을 가진 리스트 생성, 하나의 요소를 가진 불변의 리스트 생성
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return SignUpResDto.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public SignInResDto signIn(SignInReqDto signInReqDto) {
        User user = userRepository.findByLoginId(signInReqDto.loginId()).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(signInReqDto.password(), user.getPassword())) {
            throw new CustomException(ExceptionCode.PASSWORD_NOT_EQUAL);
        }
        String accessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getRoles());

        SignInResDto signInResponseDto = SignInResDto.builder()
                .accessToken(accessToken)
                .build();

        return signInResponseDto;
    }

    public UserResDto updateUser(String loginId, UserUpdateDto userUpdateDto) {

        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        if (userUpdateDto.name() != null && !userUpdateDto.name().isEmpty()) {
            user.updateUserName(userUpdateDto.name());
        }

        if (userUpdateDto.loginId() != null && !userUpdateDto.loginId().isEmpty()) {
            user.updateUserLoginId(userUpdateDto.loginId());
        }

        if (userUpdateDto.email() != null && !userUpdateDto.email().isEmpty()) {
            user.updateUserEmail(userUpdateDto.email());
        }

        return UserResDto.toDto(user);
    }

    public void updatePassword(String loginId, String password){
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));

            user.updateUserPassword(passwordEncoder.encode(password));
    }

    public Boolean checkPassword(String loginId, String password){
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteUser(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        userRepository.deleteById(user.getId());
    }

    @Transactional(readOnly = true)
    public List<CategoryAndTodosResDto> getCategoryAndTodos(String loginId){

        List<CategoryAndTodosResDto> categoryAndTodosResDtoList = userRepository.findCategoryAndTodosByLoginId(loginId);

        return categoryAndTodosResDtoList;
    }

    @Transactional(readOnly = true)
    public List<CategoryAndAllTodoResDto> getCategoryAndAllTodos(String loginId){

        List<CategoryAndAllTodoResDto> categoryAndAllTodoResDtoList = userRepository.findCategoryAndAllTodosByLoginId(loginId);

        return categoryAndAllTodoResDtoList;
    }

    @Transactional(readOnly = true)
    public List<TeamAndTeamTodosResDto> getTeamAndAllTeamTodos(String loginId){

        List<TeamAndTeamTodosResDto> teamAndTeamTodosResDtoList = userRepository.findTeamAndTeamTodosByLoginId(loginId);

        return teamAndTeamTodosResDtoList;
    }
}
