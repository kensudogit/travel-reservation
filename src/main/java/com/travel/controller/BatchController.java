package com.travel.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/batch")
@CrossOrigin(origins = "*")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("exportUserDataJob")
    private Job exportUserDataJob;

    @PostMapping("/export-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> exportUserData() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("timestamp", timestamp)
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(exportUserDataJob, jobParameters);

            return ResponseEntity.ok("User data export job started successfully. File will be generated as: users_"
                    + timestamp + ".csv");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to start export job: " + e.getMessage());
        }
    }

    @GetMapping("/job-status/{jobExecutionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getJobStatus(@PathVariable Long jobExecutionId) {
        // This would typically query the BATCH_JOB_EXECUTION table
        // For now, return a simple message
        return ResponseEntity.ok("Job status endpoint - implementation needed");
    }
}