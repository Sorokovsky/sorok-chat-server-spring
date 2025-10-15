package pro.sorokovsky.sorokchatserverspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
