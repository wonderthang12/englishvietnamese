package com.example.english.dao.model;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity extends AuditEntity {
    @Column(name = "active", nullable = false)
    @Audited
    private Boolean active;

    @Column(name = "active", insertable = false, updatable = false)
    private Boolean oldActive;

    @Column(name = "deleted", nullable = false)
    @Audited
    private Long deleted = 0L;

    public abstract Long getId();

    public abstract void setId(Long id);

    public BaseEntity(Long id) {
        setId(id);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (active == null) {
            active = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (active == null) {
            active = oldActive;
        }
    }

    @PostPersist
    public void postPersist() {
    }

    @PostUpdate
    public void postUpdate() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseEntity)) {
            return false;
        }

        BaseEntity entity = (BaseEntity) obj;

        if (entity.getId() == null) {
            return false;
        }

        return entity.getId().equals(getId());
    }
}
