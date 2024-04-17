package com.example.springtraining.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.dto.SignupDto;
import com.example.springtraining.entity.User;
import com.example.springtraining.exception.FormatException;
import com.example.springtraining.repository.UserRepository;
import com.example.springtraining.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (authentication != null && authentication.isAuthenticated()) {
            // return ((User) authentication.getPrincipal()).
        }
        return null;
    }

    @Override
    public Optional<User> findByEmailUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Optional<User> saveUser(SignupDto signupDto) throws FormatException {
        if (signupDto == null) {
            throw new FormatException("Dữ liệu nhập vào null");
        }

        String email = signupDto.getEmail();
        String password = signupDto.getPassword();
        String confirmPassword = signupDto.getConfirmPassword();

        // Kiểm tra dữ liệu đầu vào từ client
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)) {
            throw new FormatException("Dữ liệu đầu vào không hợp lệ!");
        }

        if (!StringUtils.equals(password, confirmPassword)) {
            throw new FormatException("Mật khẩu không khớp!");
        }

        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
        if (userRepository.existsByEmail(email)) {
            throw new FormatException("Email đã tồn tại!");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setAddress(signupDto.getAddress());
        newUser.setPhone(signupDto.getPhone());
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(password));

        // Lưu người dùng vào cơ sở dữ liệu
        newUser = userRepository.saveAndFlush(newUser);

        logger.debug("Đăng ký thành công: {}", newUser.getEmail());
        return Optional.of(newUser);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
