package com.example.budget.summary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService){
        this.summaryService = summaryService;
    }

    @GetMapping
    public ResponseEntity<SummaryResponse> summary(){
        SummaryResponse response =  summaryService.getSummary();
        return ResponseEntity.status(200).body(response);


    }

}
