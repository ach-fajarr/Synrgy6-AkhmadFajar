package id.achfajar.challenge6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel implements Serializable {

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
//    @CreatedDate
    private Date createdDate;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
//    @LastModifiedDate
    private Date updatedDate;

//    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Column(name = "deleted_date")
    private Date deletedDate;

    @Column(name = "isDeleted", length = 50)
    private Boolean isDeleted = false;

    @PrePersist
    private void prePersist() {
        this.createdDate = new Date();
    }
    @PreUpdate
    private void preUpdate() {
        this.updatedDate = new Date();
    }
    @PreRemove
    public void beforeAnyUpdate() {
        if (isDeleted != null && isDeleted && (getDeletedDate() == null)) {
                this.deletedDate = new Date();
        }
    }
}
