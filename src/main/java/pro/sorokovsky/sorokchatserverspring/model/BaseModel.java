package pro.sorokovsky.sorokchatserverspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}
