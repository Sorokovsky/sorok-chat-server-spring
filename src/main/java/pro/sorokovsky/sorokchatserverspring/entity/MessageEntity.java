package pro.sorokovsky.sorokchatserverspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    String mac;

    @ManyToOne
    private UserEntity author;
}
