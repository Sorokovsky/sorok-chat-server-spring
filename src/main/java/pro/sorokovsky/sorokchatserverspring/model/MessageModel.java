package pro.sorokovsky.sorokchatserverspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel extends BaseModel {
    private String text;
    private UserModel author;
}
