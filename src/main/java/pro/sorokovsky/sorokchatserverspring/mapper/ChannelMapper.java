package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannel;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateChannel;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelMapper {
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    private static String chooseString(String oldString, String newString) {
        if (newString == null || newString.isBlank()) return oldString;
        return newString;
    }

    public ChannelModel toModel(ChannelEntity entity) {
        final var model = new ChannelModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setMembers(entity.getMembers().stream().map(userMapper::toModel).toList());
        model.setMessages(entity.getMessages().stream().map(messageMapper::toModel).toList());
        return model;
    }

    public ChannelEntity toEntity(NewChannel newChannel, List<UserModel> members) {
        final var now = Date.from(Instant.now());
        final var entity = new ChannelEntity();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setName(newChannel.name());
        entity.setDescription(newChannel.description());
        entity.setMembers(members.stream().map(userMapper::toEntity).toList());
        entity.setMessages(List.of());
        return entity;
    }

    public ChannelEntity merge(ChannelModel oldState, NewStateChannel newState) {
        final var entity = new ChannelEntity();
        entity.setId(oldState.getId());
        entity.setCreatedAt(oldState.getCreatedAt());
        entity.setUpdatedAt(Date.from(Instant.now()));
        entity.setName(chooseString(oldState.getName(), newState.name()));
        entity.setDescription(chooseString(oldState.getDescription(), newState.description()));
        entity.setMembers(oldState.getMembers().stream().map(userMapper::toEntity).toList());
        entity.setMessages(oldState.getMessages().stream().map(messageMapper::toEntity).toList());
        return entity;
    }

    public ChannelEntity toEntity(ChannelModel model) {
        final var entity = new ChannelEntity();
        entity.setId(model.getId());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setMembers(model.getMembers().stream().map(userMapper::toEntity).toList());
        entity.setMessages(model.getMessages().stream().map(messageMapper::toEntity).toList());
        return entity;
    }
}
