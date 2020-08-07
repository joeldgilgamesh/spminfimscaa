package com.spminfiscaa.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.spminfiscaa.domain.ServPreDetteIntStruc} entity.
 */
public class ServPreDetteIntStrucDTO implements Serializable {
    
    private Long id;

    private String organisme;

    private String categorie;

    private String groupe;

    private Integer principale;

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

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public Integer getPrincipale() {
        return principale;
    }

    public void setPrincipale(Integer principale) {
        this.principale = principale;
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
        if (!(o instanceof ServPreDetteIntStrucDTO)) {
            return false;
        }

        return id != null && id.equals(((ServPreDetteIntStrucDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteIntStrucDTO{" +
            "id=" + getId() +
            ", organisme='" + getOrganisme() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", groupe='" + getGroupe() + "'" +
            ", principale=" + getPrincipale() +
            ", interet=" + getInteret() +
            ", echeance='" + getEcheance() + "'" +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
