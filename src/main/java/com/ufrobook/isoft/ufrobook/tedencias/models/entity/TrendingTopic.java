package com.ufrobook.isoft.ufrobook.tedencias.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "trending_topics")
@Getter
@Setter
@Entity
public class TrendingTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    private static final long serialVersionUID = 1L;

}
