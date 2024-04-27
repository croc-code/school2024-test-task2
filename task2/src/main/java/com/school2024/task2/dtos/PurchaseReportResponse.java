package com.school2024.task2.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PurchaseReportResponse {
    @JsonProperty("categories")
    private List<String> categories = new ArrayList<>();

}
