package com.sebastianherrera.laboratorio.service;

import com.sebastianherrera.laboratorio.dto.LoginRequestDTO;
import com.sebastianherrera.laboratorio.dto.LoginResponseDTO;
import com.sebastianherrera.laboratorio.model.Rol;
import com.sebastianherrera.laboratorio.model.Usuario;
import com.sebastianherrera.laboratorio.repository.RolRepository;
import com.sebastianherrera.laboratorio.repository.UsuarioRepository;
import com.sebastianherrera.laboratorio.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        String rol = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        return new LoginResponseDTO(token, userDetails.getUsername(), rol);
    }

    public Optional<LoginResponseDTO> register(LoginRequestDTO request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            return Optional.empty();
        }

        Optional<Rol> rolUser = rolRepository.findByNombre("USER");
        if (rolUser.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.getRoles().add(rolUser.get());
        usuarioRepository.save(usuario);

        return Optional.of(login(request));
    }
}
