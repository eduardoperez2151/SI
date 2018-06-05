package uy.edu.ucu.si.grupo8.obligatorio3.models.abstracts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseAuditableEntity<K extends Serializable> extends AbstractBaseEntity<K> {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime created;

    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime lastModified;

}