package pro.sorokovsky.sorokchatserverspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;

import java.util.Optional;

@Repository
public interface MessagesRepository extends CrudRepository<MessageEntity, Long> {
    Optional<MessageEntity> findById(long id);

    MessageEntity save(MessageEntity message);

    void deleteById(long id);
}
