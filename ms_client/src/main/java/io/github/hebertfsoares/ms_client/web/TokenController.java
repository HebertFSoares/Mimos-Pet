package io.github.hebertfsoares.ms_client.web;

import io.github.hebertfsoares.ms_client.domain.repository.ClientRepository;
import io.github.hebertfsoares.ms_client.dto.LoginRequest;
import io.github.hebertfsoares.ms_client.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;

@RestController
@RequestMapping("clients")
@Tag(name = "Login Controller", description = "Endpoints for login client")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder, ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Operation(summary = "Client Login", description = "Authenticates a client and provides a token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var client = clientRepository.findByEmail(loginRequest.email());

        if (client.isEmpty()) {
            throw new BadCredentialsException("user or password is invalid");
        }

        if (!client.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var role = client.get().getRole().name();
        var roles = Collections.singletonList(role);

        var claims = JwtClaimsSet.builder()
                .issuer("ms_client")
                .subject(client.get().getCpf())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("roles", roles)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
