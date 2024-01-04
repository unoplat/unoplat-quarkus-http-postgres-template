package org.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_health")
@Setter
@Getter
public class ProductHealth {

    @JsonProperty("product_name")
    @Column(name = "product_name", nullable = false)
    @Id
    private String productName;
    
    @JsonProperty("health_score") 
    @Column(name = "health_score")
    private int healthScore;

}