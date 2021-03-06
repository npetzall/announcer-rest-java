package com.github.npetzall.announcer.rest.controller;


import com.github.npetzall.announcer.rest.domain.WorkLogEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/worklog/v1")
public class WorkLogEntryController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newWorkLogEntry(@RequestBody WorkLogEntry workLogEntry) {
        return ResponseEntity.ok(workLogEntry);
    }
}
