package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannel;
import pro.sorokovsky.sorokchatserverspring.contract.NewStateChannel;
import pro.sorokovsky.sorokchatserverspring.exception.channel.ChannelNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.ChannelsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelsService {
    private final ChannelsRepository repository;
    private final ChannelMapper mapper;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public Optional<ChannelModel> getByName(String name) {
        return repository.findByName(name).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public Optional<ChannelModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public List<ChannelModel> getAllWhereContainsUser(UserModel user) {
        final var userEntity = userMapper.toEntity(user);
        return repository.findAllByMembersContains(userEntity).stream().map(mapper::toModel).toList();
    }

    @Transactional
    public ChannelModel create(NewChannel newChannel, List<UserModel> members) {
        final var channel = mapper.toEntity(newChannel, members);
        return mapper.toModel(repository.save(channel));
    }

    @Transactional
    public ChannelModel update(NewStateChannel newStateChannel, Long id) {
        final var channel = getById(id).orElse(null);
        if (channel == null) throw new ChannelNotFoundException("id", id.toString());
        final var merged = mapper.merge(channel, newStateChannel);
        return mapper.toModel(repository.save(merged));
    }

    @Transactional
    public ChannelModel addMembers(Long channelId, List<UserModel> members) {
        final var channel = getById(channelId).orElse(null);
        if (channel == null) throw new ChannelNotFoundException("id", channelId.toString());
        for (var member : members) {
            if (!channel.getMembers().contains(member)) {
                channel.getMembers().add(member);
            }
        }
        return mapper.toModel(repository.save(mapper.toEntity(channel)));
    }

    @Transactional
    public ChannelModel removeMembers(Long channelId, List<UserModel> members) {
        final var channel = getById(channelId).orElse(null);
        if (channel == null) throw new ChannelNotFoundException("id", channelId.toString());
        for (var member : members) {
            channel.getMembers().remove(member);
        }
        return mapper.toModel(repository.save(mapper.toEntity(channel)));
    }

    @Transactional
    public ChannelModel addMessage(Long channelId, MessageModel message) {
        final var channel = getById(channelId).orElse(null);
        if (channel == null) throw new ChannelNotFoundException("id", channelId.toString());
        if (!channel.getMessages().contains(message)) {
            channel.getMessages().add(message);
        }
        return mapper.toModel(repository.save(mapper.toEntity(channel)));
    }

    @Transactional
    public ChannelModel removeMessage(Long channelId, MessageModel message) {
        final var channel = getById(channelId).orElse(null);
        if (channel == null) throw new ChannelNotFoundException("id", channelId.toString());
        channel.getMessages().remove(message);
        return mapper.toModel(repository.save(mapper.toEntity(channel)));
    }

    @Transactional
    public ChannelModel remove(Long id) {
        final var candidate = getById(id).orElseThrow(() -> new ChannelNotFoundException("id", id.toString()));
        repository.deleteById(id);
        return candidate;
    }
}
