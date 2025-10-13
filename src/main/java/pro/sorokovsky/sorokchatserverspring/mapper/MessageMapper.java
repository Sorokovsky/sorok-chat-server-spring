package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;

@Service
@RequiredArgsConstructor
public class MessageMapper {
    private final UserMapper userMapper;

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
}
