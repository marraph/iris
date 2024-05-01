package com.marraph.iris.model;

import com.marraph.iris.model.organisation.User;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable extends AbstractPersistable<Long> {

    @CreatedBy
    protected User createdBy;

    @CreatedDate
    protected Date createdDate;

    @LastModifiedBy
    protected User lastModifiedBy;

    @LastModifiedDate
    protected Date lastModifiedDate;
}