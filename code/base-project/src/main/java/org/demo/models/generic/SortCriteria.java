package org.demo.models.generic;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SortCriteria {
    private Map<String, String> sortCriteria;
}