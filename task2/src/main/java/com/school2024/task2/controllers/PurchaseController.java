package com.school2024.task2.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.school2024.task2.dtos.PurchaseReportResponse;
import com.school2024.task2.dtos.PurchaseRequest;
import com.school2024.task2.services.ReportService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/purchases")
@Log4j2
public class PurchaseController {
    final private ReportService reportService;

    PurchaseController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/analyze")
    public PurchaseReportResponse analyzePurchase(@Valid @RequestBody List<PurchaseRequest> purchaseRequests) {
        log.info(purchaseRequests);
        List<String> mostPopularCategories = reportService.createPurchaseReportWithMostPopularItemsBetween1And31December(purchaseRequests);
        log.info(mostPopularCategories);

        PurchaseReportResponse purchaseReportResponse = new PurchaseReportResponse();
        purchaseReportResponse.setCategories(mostPopularCategories);
        return purchaseReportResponse;
    }
    
}
