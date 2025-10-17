package pro.sorokovsky.sorokchatserverspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.sorokchatserverspring.contract.GetChannel;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannel;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.service.ChannelsService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("channels")
@Tag(name = "Чати")
@RequiredArgsConstructor
public class ChannelsController {
    private final ChannelsService service;
    private final ChannelMapper mapper;
    private final UsersService usersService;

    @GetMapping("by-me")
    @Operation(summary = "Мої чати", description = "Отримання всіх чатів користувача")
    public ResponseEntity<List<GetChannel>> getByMe(@AuthenticationPrincipal UserModel user) {
        final var channels = service.getAllWhereContainsUser(user);
        return ResponseEntity.ok(channels.stream().map(mapper::toGetChannel).toList());
    }

    @PostMapping
    @Operation(summary = "Новий чат", description = "Створити новий чат")
    public ResponseEntity<GetChannel> createChannel(
            @AuthenticationPrincipal UserModel user,
            @RequestBody NewChannel newChannel,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        final var channel = service.create(newChannel, List.of(user));
        final var uri = uriComponentsBuilder.path("/channels/{id}").buildAndExpand(channel.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toGetChannel(channel));
    }

    @PutMapping("add-user/{channelId}/{userId}")
    @Operation(summary = "Додати до чату", description = "Додати користувача до чату")
    public ResponseEntity<GetChannel> addUser(@PathVariable Long channelId, @PathVariable Long userId) {
        final var user = usersService.getById(userId)
                .orElseThrow(() -> new UserNotFoundException("id", userId.toString()));
        final var channel = service.addMembers(channelId, List.of(user));
        return ResponseEntity.ok(mapper.toGetChannel(channel));
    }

    @PutMapping("remove-user/{channelId}/{userId}")
    @Operation(summary = "Вилучити з чату", description = "Вилучити користувача з чату")
    public ResponseEntity<GetChannel> removeUser(@PathVariable Long channelId, @PathVariable Long userId) {
        final var user = usersService.getById(userId)
                .orElseThrow(() -> new UserNotFoundException("id", userId.toString()));
        final var channel = service.removeMembers(channelId, List.of(user));
        return ResponseEntity.ok(mapper.toGetChannel(channel));
    }
}
