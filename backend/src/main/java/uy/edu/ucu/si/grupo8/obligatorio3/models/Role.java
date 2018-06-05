package uy.edu.ucu.si.grupo8.obligatorio3.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts.AbstractBaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
@Builder
@EqualsAndHashCode
public class Role extends AbstractBaseAuditableEntity<Long> {

    private String Name;
    private String description;

}