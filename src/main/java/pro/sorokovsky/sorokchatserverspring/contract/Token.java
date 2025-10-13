package pro.sorokovsky.sorokchatserverspring.contract;

import java.time.Instant;
import java.util.UUID;

public record Token(String subject, UUID id, Instant createdAt, Instant expiresAt) {
}
