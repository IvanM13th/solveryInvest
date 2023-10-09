package com.example.solveryInvest.service.userService;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.User;
import com.example.solveryInvest.exception.NotFoundException;
import com.example.solveryInvest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import static com.example.solveryInvest.service.encoderService.PasswordEncoderService.passwordEncoder;

@Service("user-service")
@Slf4j
@RequiredArgsConstructor
public class UserServiceJooq implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", email)));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public boolean emailExists(String email) {
        var user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public User save(UserDto userDto) {
        var user = modelMapper.map(userDto, User.class);
        var encoded = passwordEncoder().encode(userDto.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
        return user;
    }
}
