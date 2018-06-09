package uy.edu.ucu.si.grupo8.obligatorio3.models;

import lombok.*;
import uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts.AbstractBaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractBaseAuditableEntity<Long> {

    private String name;
    private String description;

    @Builder
    public Role(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}