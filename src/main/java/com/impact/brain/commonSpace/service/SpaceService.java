package com.impact.brain.commonSpace.service;

import com.impact.brain.commonSpace.entity.Space;
import com.impact.brain.commonSpace.entity.SpaceEquipment;
import com.impact.brain.commonSpace.entity.SpaceStatus;
import com.impact.brain.commonSpace.entity.SpaceType;
import com.impact.brain.commonSpace.repository.SpaceEquipmentRepository;
import com.impact.brain.commonSpace.repository.SpaceRepository;
import com.impact.brain.commonSpace.repository.SpaceStatusRepository;
import com.impact.brain.commonSpace.repository.SpaceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service("spaceService")
public class SpaceService {
    @Autowired
    private SpaceTypeRepository spaceTypeRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceEquipmentRepository spaceEquipmentRepository;

    @Autowired
    private SpaceStatusRepository spaceStatusRepository;

    /* FindAll() methods to retrieve the entire list of each object type respectively  */
    public Iterable<SpaceType> spaceTypes() { return spaceTypeRepository.findAll(); }
    public Iterable<SpaceEquipment> spaceEquipments() { return spaceEquipmentRepository.findAll(); }
    public Iterable<SpaceStatus> spaceStatuses() { return spaceStatusRepository.findAll(); }

    public Iterable<Space> spaces() { return spaceRepository.findAll(); }

    /* Save methods for each of the classes that have relationships with the Space class */
    public void saveSpaceType(SpaceType spaceType) {
        spaceTypeRepository.save(spaceType);
        System.out.println("Saving spaceType: " + spaceType);
    }

    public void saveSpace(Space space) {
        spaceRepository.save(space);
        System.out.println("Saving space: " + space);
    }

    public void saveSpaceEquipment(SpaceEquipment spaceEquipment) {
        spaceEquipmentRepository.save(spaceEquipment);
        System.out.println("Saving spaceEquipment: " + spaceEquipment);
    }

    /* Methods to get objetcts */
    public Optional<SpaceType> spaceTypeById(int id) {
        return spaceTypeRepository.findById(id);
    }

}
