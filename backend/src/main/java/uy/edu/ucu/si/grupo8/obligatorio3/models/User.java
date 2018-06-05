package uy.edu.ucu.si.grupo8.obligatorio3.models;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts.AbstractBaseAuditableEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@EqualsAndHashCode
public class User extends AbstractBaseAuditableEntity<Long> {

    private String password;
    private String username;
    private String email;
    private Boolean active;
    private String activationToken;
    private String passwordResetToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}