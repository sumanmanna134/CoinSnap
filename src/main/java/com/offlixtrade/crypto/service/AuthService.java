package com.offlixtrade.crypto.service;

import com.offlixtrade.crypto.config.JwtProvider;
import com.offlixtrade.crypto.exception.UserEmailAlreadyExistException;
import com.offlixtrade.crypto.model.AuthResponse;
import com.offlixtrade.crypto.model.User;
import com.offlixtrade.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    public AuthResponse register(User user) {

        User isEmailAlreadyExist = userRepository.findByEmail(user.getEmail());
        if (isEmailAlreadyExist != null) {
            throw new UserEmailAlreadyExistException("Email already exist");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        String jwtToken = createJwtToken(user);
        userRepository.save(newUser);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken);
        authResponse.setStatus(true);
        return authResponse;


    }

    public AuthResponse login(User user) {

        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken);
        authResponse.setStatus(true);
        return authResponse;


    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }


    public void truncateTable() {
        userRepository.truncateTable();
    }

    private String createJwtToken(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtProvider.generateToken(authentication);

    }


}
