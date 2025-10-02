package pro.sorokovsky.sorokchatserverspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {
    @Column(nullable = false)
    private String text;

    @ManyToOne
    private UserEntity author;
}
