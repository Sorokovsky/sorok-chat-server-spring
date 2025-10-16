package pro.sorokovsky.sorokchatserverspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sorokovsky.sorokchatserverspring.contract.LoginDto;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.service.AuthenticationService;

@RestController
@RequestMapping("authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("registration")
    public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser) {
        final var created = service.register(newUser);
        return ResponseEntity.ok(created);
    }

    @PutMapping("login")
    ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        service.login(loginDto);
        return ResponseEntity.noContent().build();
    }
}
