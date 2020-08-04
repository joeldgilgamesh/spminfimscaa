package com.spminfiscaa.web.rest;

import com.spminfiscaa.service.ServPreDetteExtService;
import com.spminfiscaa.service.ServPreDetteIntNoStrucService;
import com.spminfiscaa.service.ServPreDetteIntStrucService;
import com.spminfiscaa.service.SolEngNonDecService;
import com.spminfiscaa.service.dto.ServPreDetteExtDTO;
import com.spminfiscaa.service.dto.ServPreDetteIntNoStrucDTO;
import com.spminfiscaa.service.dto.ServPreDetteIntStrucDTO;
import com.spminfiscaa.service.dto.SolEngNonDecDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AllData {
    private final ServPreDetteIntStrucService servPreDetteIntStrucService;
    private final ServPreDetteIntNoStrucService servPreDetteIntNoStrucService;
    private final ServPreDetteExtService servPreDetteExtService ;
    private final SolEngNonDecService solEngNonDecService;
    Map<String,Object > allObjectMap = new HashMap<>();
    Map<String,Object > allObjectTotalMap = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(AllData.class);


    public AllData(ServPreDetteIntStrucService servPreDetteIntStrucService, ServPreDetteIntNoStrucService servPreDetteIntNoStrucService, ServPreDetteExtService servPreDetteExtService, SolEngNonDecService solEngNonDecService) {
        this.servPreDetteIntStrucService = servPreDetteIntStrucService;
        this.servPreDetteIntNoStrucService = servPreDetteIntNoStrucService;
        this.servPreDetteExtService = servPreDetteExtService;
        this.solEngNonDecService = solEngNonDecService;
    }

    public Map<String,Object> AllObject(){
        List<ServPreDetteExtDTO> servPreDetteExtDTOS = servPreDetteExtService.findAll();
        List<ServPreDetteIntNoStrucDTO> servPreDetteIntNoStrucDTOS = servPreDetteIntNoStrucService.findAll();
        List<ServPreDetteIntStrucDTO> servPreDetteIntStrucs = servPreDetteIntStrucService.findAll();
        List<SolEngNonDecDTO> solEngNonDecDTOS = solEngNonDecService.findAll();
        allObjectMap.put("ListServPreDetteExtDTO", servPreDetteExtDTOS);
        allObjectMap.put("ListServPreDetteIntNoStrucDTO",servPreDetteIntNoStrucDTOS);
        allObjectMap.put("ListServPreDetteIntStrucDTO", servPreDetteIntStrucs);
        allObjectMap.put("ListSolEngNonDecDTO",solEngNonDecDTOS);
        log.debug("REST request to update ServPreDetteIntStruc : "+ servPreDetteExtDTOS);
        log.debug("REST request to update ServPreDetteIntStruc : "+ servPreDetteIntStrucs);
        log.debug("REST request to update ServPreDetteIntStruc : "+ solEngNonDecDTOS);
        log.debug("REST request to update ServPreDetteIntStruc : "+ servPreDetteIntNoStrucDTOS);
        return allObjectMap;
    }
    public Map<String, Object> AllTotal(){
        int data1 = solEngNonDecService.somme();
        int data2 = servPreDetteIntNoStrucService.somme();
        int data3 = servPreDetteExtService.somme();
        int data4 = servPreDetteIntStrucService.somme();
        int sum = data1 + data2 + data3 + data4 ;
        allObjectTotalMap.put("Tout les totaux SEND, SPDIS, SPDIN, SPDE", sum);
        return allObjectTotalMap;
    }
    @GetMapping("/all-data")
    public Map<String,Object> getAll(){
        log.debug("REST request to update ServPreDetteIntStruc : {}", AllObject());
        return AllObject();
    }
    @GetMapping("/all-total")
    public Map<String, Object> getAllTotal(){
        log.debug("Tous les totaux : {}", AllTotal());
        return AllTotal();
    }
}

