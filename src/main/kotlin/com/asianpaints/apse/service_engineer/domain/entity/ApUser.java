package com.asianpaints.apse.service_engineer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AP_USER")
public class ApUser {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "user_type_id")
    private UserType userType;
    @ManyToOne()
    @JoinColumn(name = "zone_id")
    private Zone zone;
    private String email;
    @ManyToOne()
    @JoinColumn(name = "designation_id")
    private UserDesignation userDesignation;
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;
}
