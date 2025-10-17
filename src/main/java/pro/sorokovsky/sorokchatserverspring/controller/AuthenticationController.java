package pro.sorokovsky.sorokchatserverspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sorokovsky.sorokchatserverspring.contract.GetUser;
import pro.sorokovsky.sorokchatserverspring.contract.LoginDto;
import pro.sorokovsky.sorokchatserverspring.contract.NewUser;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.service.AuthenticationService;

@RestController
@RequestMapping("authentication")
@Tag(name = "Автентифікація")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserMapper mapper;

    @PostMapping("registration")
    @Operation(summary = "Реєстрація", description = "Реєстрація користувача")
    public ResponseEntity<GetUser> register(@Valid @RequestBody NewUser newUser) {
        final var created = service.register(newUser);
        return ResponseEntity.ok(mapper.toGetUser(created));
    }

    @PutMapping("login")
    @Operation(summary = "Вхід", description = "Автентифікація користувача")
    ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        service.login(loginDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("logout")
    @Operation(summary = "Вихід", description = "Вихід користувача")
    public ResponseEntity<Void> logout() {
        service.logout();
        return ResponseEntity.noContent().build();
    }
}
