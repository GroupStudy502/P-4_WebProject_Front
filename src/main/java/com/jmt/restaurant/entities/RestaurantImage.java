package com.jmt.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor @NoArgsConstructor
public class RestaurantImage {

    @Id @GeneratedValue
    private Long seq;
    private String rstrImgUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restrId")
    private Restaurant restaurant;
}