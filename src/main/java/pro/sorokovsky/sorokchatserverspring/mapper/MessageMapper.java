package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessage;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateMessage;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageMapper {
    private final UserMapper userMapper;

    private static String chooseString(String oldString, String newString) {
        if (newString == null || newString.isBlank()) return oldString;
        return newString;
    }

    public MessageModel toModel(MessageEntity entity) {
        final var model = new MessageModel();
        model.setId(entity.getId());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setText(entity.getText());
        model.setAuthor(userMapper.toModel(entity.getAuthor()));
        return model;
    }

    public MessageEntity toEntity(MessageModel model) {
        final var entity = new MessageEntity();
        entity.setId(model.getId());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setText(model.getText());
        entity.setAuthor(userMapper.toEntity(model.getAuthor()));
        return entity;
    }

    public MessageEntity toEntity(NewMessage newMessage, UserModel author) {
        final var entity = new MessageEntity();
        final var now = Date.from(Instant.now());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setAuthor(userMapper.toEntity(author));
        entity.setText(newMessage.text());
        return entity;
    }

    public MessageEntity merge(MessageModel oldState, NewStateMessage newState) {
        final var entity = new MessageEntity();
        entity.setId(oldState.getId());
        entity.setCreatedAt(oldState.getCreatedAt());
        entity.setUpdatedAt(Date.from(Instant.now()));
        entity.setText(chooseString(oldState.getText(), newState.text()));
        entity.setAuthor(userMapper.toEntity(oldState.getAuthor()));
        return entity;
    }
}
