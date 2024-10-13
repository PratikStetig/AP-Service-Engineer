package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    private ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> getAllZone(){
        return zoneRepository.findAll();
    }
}
