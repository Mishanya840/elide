/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package example;

import com.yahoo.elide.annotation.Include;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Include(rootLevel = true)
@Audited // Ensure envers does not cause any issues
public class Person {
    @Setter
    private long id;

    @Setter
    @Getter
    private String name;

    @Setter
    private AddressFragment address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "address", columnDefinition = "TEXT")
    @Type(type = "com.yahoo.elide.datastores.hibernate5.usertypes.JsonType", parameters = {
            @Parameter(name = "class", value = "example.AddressFragment")
    })
    public AddressFragment getAddress() {
        return address;
    }

    private Set<House> houses;
    @OneToMany(targetEntity = House.class,
            mappedBy = "owner")
    public Set<House> getHouse() {
        return houses;
    }
    public void setHouse(Set<House> houses) {
        this.houses = houses;
    }
}
