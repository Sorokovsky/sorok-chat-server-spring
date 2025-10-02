package pro.sorokovsky.sorokchatserverspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelsRepository extends CrudRepository<ChannelEntity, Long> {
    Optional<ChannelEntity> findByName(String name);

    Optional<ChannelEntity> findById(Long id);

    List<ChannelEntity> findAllByMembersContains(UserEntity member);

    ChannelEntity save(ChannelEntity channel);

    void deleteById(Long id);
}
