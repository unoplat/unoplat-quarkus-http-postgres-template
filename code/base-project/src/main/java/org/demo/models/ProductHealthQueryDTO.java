package org.demo.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductHealthQueryDTO {
    private int minHealthScore;
    private int page;
    private int size;
    private Map<String, String> sortBy;   
    // Getters and Setters
}
