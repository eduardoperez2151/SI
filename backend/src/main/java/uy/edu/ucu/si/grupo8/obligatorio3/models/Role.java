package uy.edu.ucu.si.grupo8.obligatorio3.models;

import lombok.*;
import uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts.AbstractBaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractBaseAuditableEntity<Long> {

    private String Name;
    private String description;

}