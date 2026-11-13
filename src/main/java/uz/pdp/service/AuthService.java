package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.dto.AuthResponse;
import uz.pdp.dto.LoginDTO;
import uz.pdp.dto.RegisterDTO;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.exception.ConflictException;
import uz.pdp.repository.UserRepository;
import uz.pdp.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new ConflictException("username band");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new ConflictException("email band");
        }

        userRepository.save(User.builder()
                .fullName(dto.fullName())
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.ROLE_USER)
                .build());
    }

    public AuthResponse login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BadCredentialsException(" "));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("");
        }
        return new AuthResponse(jwtService.generateToken(user));
    }
}