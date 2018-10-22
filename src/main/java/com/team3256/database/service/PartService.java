package com.team3256.database.service;

import com.team3256.database.model.inventory.Part;
import com.team3256.database.model.inventory.PartRepository;
import com.team3256.database.model.inventory.PartVendorInformation;
import com.team3256.database.model.inventory.PartVendorInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartVendorInformationRepository partVendorInformationRepository;

    public Integer delete(Part part) {
        for (PartVendorInformation vendorInformation : part.getVendorInformation()) {
            System.out.println("delteing vendor info " + vendorInformation.getPartNumber());
            partVendorInformationRepository.deleteById(vendorInformation.getId());
        }
        partRepository.deleteById(part.getId());
        return part.getId();
    }

}
