package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/zone")
@RequiredArgsConstructor
public class ZoneController {

    private ZoneService zoneService;

    @GetMapping()
    public ResponseEntity<Object> getAllZone(){
        List<Zone> zones = zoneService.getAllZone();
        return ResponseEntity.ok(zones);
    }

}
