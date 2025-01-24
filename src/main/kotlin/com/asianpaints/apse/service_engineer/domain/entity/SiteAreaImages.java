package com.asianpaints.apse.service_engineer.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "site_area_image")
public class SiteAreaImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//    @JsonBackReference
//    @JoinColumn(name = "site_area_id", nullable = false)

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonBackReference
    @JoinColumn(name = "site_area_id", nullable = false)
    public SiteArea siteArea;
    @Column(name = "image_url", length = 500, nullable = false)
    public String imageUrl;
    @Column(name = "uploaded_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
