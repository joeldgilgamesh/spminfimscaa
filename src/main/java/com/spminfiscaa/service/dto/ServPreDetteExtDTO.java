package com.spminfiscaa.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.spminfiscaa.domain.ServPreDetteExt} entity.
 */
public class ServPreDetteExtDTO implements Serializable {
    
    private Long id;

    private String bailleur;

    private String type_cooperation;

    private String type_fond;

    private Integer montant_a_rembourser;

    private Integer interet;

    private LocalDate echeance;

    private LocalDate date;

    private Long total;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBailleur() {
        return bailleur;
    }

    public void setBailleur(String bailleur) {
        this.bailleur = bailleur;
    }

    public String getType_cooperation() {
        return type_cooperation;
    }

    public void setType_cooperation(String type_cooperation) {
        this.type_cooperation = type_cooperation;
    }

    public String getType_fond() {
        return type_fond;
    }

    public void setType_fond(String type_fond) {
        this.type_fond = type_fond;
    }

    public Integer getMontant_a_rembourser() {
        return montant_a_rembourser;
    }

    public void setMontant_a_rembourser(Integer montant_a_rembourser) {
        this.montant_a_rembourser = montant_a_rembourser;
    }

    public Integer getInteret() {
        return interet;
    }

    public void setInteret(Integer interet) {
        this.interet = interet;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServPreDetteExtDTO)) {
            return false;
        }

        return id != null && id.equals(((ServPreDetteExtDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteExtDTO{" +
            "id=" + getId() +
            ", bailleur='" + getBailleur() + "'" +
            ", type_cooperation='" + getType_cooperation() + "'" +
            ", type_fond='" + getType_fond() + "'" +
            ", montant_a_rembourser=" + getMontant_a_rembourser() +
            ", interet=" + getInteret() +
            ", echeance='" + getEcheance() + "'" +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
