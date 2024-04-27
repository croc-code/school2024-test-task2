package com.school2024.task2.services;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.school2024.task2.dtos.PurchaseRequest;
import com.school2024.task2.dtos.PurchaseRequest.Item;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReportService {
    public List<String> createPurchaseReportWithMostPopularItemsBetween1And31December(List<PurchaseRequest> purchases) {
        List<PurchaseRequest> filteredByDatePurchases = filterByDateBetween1And31December(purchases);

        List<String> purchaseReport = findMostPopularCategoriesInPurchases(filteredByDatePurchases);

        return sortPurchaseReport(purchaseReport);
    }

    private List<PurchaseRequest> filterByDateBetween1And31December(List<PurchaseRequest> purchases) {
        return purchases.stream()
            .filter(x -> isPreNewYearDate(x.getOrderedAt()))
            .collect(Collectors.toList());
    }

    private boolean isPreNewYearDate(LocalDateTime currentdate) {
        if (currentdate.getMonth() == Month.DECEMBER) {
            if (currentdate.getDayOfMonth() >= 1 && currentdate.getDayOfMonth() <= 31) {
                return true;
            }
        }
        return false;
    }

    private List<String> findMostPopularCategoriesInPurchases(List<PurchaseRequest> purchases) {
        Map<String, Integer> tableOfCategoryFrequency = createTableOfCategoryFrequencyFromPurchases(purchases);

        Integer maxCategoryFrequency = findMaxCategoryFrequencyInCategoryFrequencyTable(tableOfCategoryFrequency);

        return findCategoriesByFrequency(tableOfCategoryFrequency, maxCategoryFrequency);
    }

    private Map<String, Integer> createTableOfCategoryFrequencyFromPurchases(List<PurchaseRequest> purchasesRequest) {
        Map<String, Integer> tableOfCategoryFrequency = new HashMap<>();
        for (PurchaseRequest purchaseRequest: purchasesRequest) {
            for (Item item: purchaseRequest.getItems()) {
                String categoryName = item.getCategory().getName();
                if (tableOfCategoryFrequency.get(categoryName) == null) {
                    tableOfCategoryFrequency.put(categoryName, 1);
                    continue;
                }
                tableOfCategoryFrequency.replace(categoryName, tableOfCategoryFrequency.get(categoryName) + 1);
            }
        }
        return tableOfCategoryFrequency;
    }

    private Integer findMaxCategoryFrequencyInCategoryFrequencyTable(Map<String, Integer> tableOfCategoryFrequency) {
        Integer maxPurchaseFrequency = tableOfCategoryFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .orElse(0);

        return maxPurchaseFrequency;
    } 

    private List<String> findCategoriesByFrequency(Map<String, Integer> tableOfCategoryFrequency, Integer maxPurchaseFrequency) {
        return tableOfCategoryFrequency.entrySet().stream()
                .filter(x -> x.getValue().equals(maxPurchaseFrequency))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<String> sortPurchaseReport(List<String> purchaseReport) {
        return purchaseReport.stream()
            .sorted()
            .collect(Collectors.toList());
    }
}
