package uy.edu.ucu.si.grupo8.obligatorio3.models;

import lombok.*;
import uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts.AbstractBaseAuditableEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractBaseAuditableEntity<Long> {

    private String password;
    private String username;
    private String email;
    private Boolean active;

    @Builder
    public User(final Long id, final String password, final String username, final String email, final Boolean active, final List<Role> roles) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


}