package org.demo.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductHealthQueryDTO {
    @JsonProperty("min-health-score")
    private int minHealthScore;
    @JsonProperty("page")
    private int page;
    @JsonProperty("size")
    private int size;
    // Getters and Setters
}
