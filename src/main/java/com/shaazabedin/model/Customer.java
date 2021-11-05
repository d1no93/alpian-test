package com.shaazabedin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name="customers")
public class Customer {

    @Id
    @Column
    private String customerId;

    @Column
    private String externalId;

    @Column
    private Date createdAt;
}
