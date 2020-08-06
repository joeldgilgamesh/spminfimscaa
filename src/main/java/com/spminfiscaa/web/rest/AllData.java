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
    Map<String,Object > allDonneeObject = new HashMap<>();
    Map<String,Object > allObjectDate = new HashMap<>();
    Map<String,Object > total = new HashMap<>();


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
        log.debug("REST request to update servPreDetteExtDTOS : "+ servPreDetteExtDTOS);
        log.debug("REST request to update ServPreDetteIntStruc : "+ servPreDetteIntStrucs);
        log.debug("REST request to update solEngNonDecDTOS : "+ solEngNonDecDTOS);
        log.debug("REST request to update servPreDetteIntNoStrucDTOS : "+ servPreDetteIntNoStrucDTOS);
        return allObjectMap;
    }

    public Map<String, Object> detteTotal(){
        int data2 = servPreDetteIntNoStrucService.somme();
        int data3 = servPreDetteExtService.somme();
        int data4 = servPreDetteIntStrucService.somme();
        int sum =  data2 + data3 + data4 ;
        allObjectTotalMap.put("Tout les totaux , SPDIS, SPDIN, SPDE", sum);
        return allObjectTotalMap;
    }
    public Map<String, Object> Total(){
        total.put("Total solde engage non decaissee", solEngNonDecService.somme());
        total.put("Total dette extérieur", servPreDetteExtService.somme());
        total.put("Total dette interieur structurer", servPreDetteIntStrucService.somme());
        total.put("Total dette interieur non structurer", servPreDetteIntNoStrucService.somme());
        return total;
    }

    public Map<String, Object> AllDate(){
        allObjectDate.put("Date SEND", solEngNonDecService.showDate());
        allObjectDate.put("Date SPDE", servPreDetteExtService.showDate());
        allObjectDate.put("Date SPDINS", servPreDetteIntNoStrucService.showDate());
        allObjectDate.put("Date SPDIS", servPreDetteIntStrucService.showDate());
        return allObjectDate;
    }
    public Map<String, Object> AllDataObject(){
        allDonneeObject.put("Dette Total", detteTotal());
        allDonneeObject.put("Somme SEND, SPDE, SPDINS, SPDIS", Total());
        allDonneeObject.put("Total dette interieur ", servPreDetteIntStrucService.somme()+servPreDetteIntNoStrucService.somme());
        allDonneeObject.put("Date derinère importation", AllDate());
        return allDonneeObject;
    }
    @GetMapping("/all-data")
    public Map<String,Object> getAll(){
        log.debug("REST request to update ServPreDetteIntStruc : {}", AllDataObject());
        return AllDataObject();
    }
  /*  @GetMapping("/all-total")
    public Map<String, Object> getAllTotal(){
        log.debug("Tous les totaux : {}", detteTotal());
        return detteTotal();
    }

    @GetMapping("/all-lastdate")
    public Map<String, Object> getAllDate(){
        log.debug("Tous les totaux : {}", AllDate());
        return AllDate();
    }*/
}

