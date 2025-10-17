package pro.sorokovsky.sorokchatserverspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.sorokchatserverspring.contract.GetChannel;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannel;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessage;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateChannel;
import pro.sorokovsky.sorokchatserverspring.exception.channel.ChannelNotFoundException;
import pro.sorokovsky.sorokchatserverspring.exception.message.MessageNotFoundException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.service.ChannelsService;
import pro.sorokovsky.sorokchatserverspring.service.MessagesService;
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
    private final MessagesService messagesService;

    @GetMapping("by-me")
    @Operation(summary = "Мої чати", description = "Отримання всіх чатів користувача")
    public ResponseEntity<List<GetChannel>> getByMe(@AuthenticationPrincipal UserModel user) {
        final var channels = service.getAllWhereContainsUser(user);
        return ResponseEntity.ok(channels.stream().map(mapper::toGetChannel).toList());
    }

    @GetMapping("by-name/{name}")
    @Operation(summary = "Шукати за ім'ям")
    public ResponseEntity<GetChannel> getByName(@PathVariable String name) {
        final var channel = service.getByName(name)
                .orElseThrow(() -> new ChannelNotFoundException("name", name));
        return ResponseEntity.ok(mapper.toGetChannel(channel));
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

    @PutMapping("add-message/{channelId}")
    @Operation(summary = "Написати до чату", description = "Додати повідомлення до чату")
    public ResponseEntity<GetChannel> addMessage(
            @PathVariable Long channelId,
            @Valid @RequestBody NewMessage newMessage,
            @AuthenticationPrincipal UserModel user
    ) {
        final var message = messagesService.create(newMessage, user);
        final var channel = service.addMessage(channelId, message);
        return ResponseEntity.ok(mapper.toGetChannel(channel));
    }

    @PutMapping("remove-message/{channelId}/{messageId}")
    @Operation(summary = "Видалити повідомлення з чату", description = "Вилучити повідомлення з чату")
    public ResponseEntity<GetChannel> removeMessage(@PathVariable Long channelId, @PathVariable Long messageId) {
        final var message = messagesService.getById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("id", messageId.toString()));
        final var channel = service.removeMessage(channelId, message);
        messagesService.remove(messageId);
        return ResponseEntity.ok(mapper.toGetChannel(channel));
    }

    @PatchMapping("{id}")
    @Operation(summary = "Оновлення чату", description = "Оновлення чату")
    public ResponseEntity<GetChannel> updateChannel(
            @PathVariable Long id, @Valid @RequestBody NewStateChannel newStateChannel) {
        final var channel = service.update(newStateChannel, id);
        return ResponseEntity.ok(mapper.toGetChannel(channel));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Видаллення чату", description = "Видалення чату")
    public ResponseEntity<GetChannel> deleteChannel(@PathVariable Long id) {
        final var deleted = service.remove(id);
        return ResponseEntity.ok(mapper.toGetChannel(deleted));
    }
}
