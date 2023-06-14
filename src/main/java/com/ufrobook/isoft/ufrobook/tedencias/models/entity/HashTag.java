package com.ufrobook.isoft.ufrobook.tedencias.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "hashtags")
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "palabra_clave")
    private String palabraClave;

    private String hashTag;

    @ManyToOne
    @JoinColumn(name = "trending_topic_id")
    private TrendingTopic trendingTopic;

    private static final long serialVersionUID = 1L;

}
