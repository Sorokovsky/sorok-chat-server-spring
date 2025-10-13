package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessage;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateMessage;
import pro.sorokovsky.sorokchatserverspring.exception.message.MessageNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.MessagesRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagesService {
    private final MessagesRepository repository;
    private final MessageMapper mapper;

    @Transactional(readOnly = true)
    public Optional<MessageModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional
    public MessageModel create(NewMessage newMessage, UserModel author) {
        return mapper.toModel(repository.save(mapper.toEntity(newMessage, author)));
    }

    @Transactional
    public MessageModel update(Long id, NewStateMessage newMessage) {
        final var message = getById(id).orElseThrow(() -> new MessageNotFoundException("id", id.toString()));
        final var merged = mapper.merge(message, newMessage);
        return mapper.toModel(repository.save(merged));
    }

    @Transactional
    public MessageModel remove(Long id) {
        final var candidate = getById(id).orElseThrow(() -> new MessageNotFoundException("id", id.toString()));
        repository.deleteById(id);
        return candidate;
    }
}
