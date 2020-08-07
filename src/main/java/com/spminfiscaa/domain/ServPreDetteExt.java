package com.spminfiscaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ServPreDetteExt.
 */
@Entity
@Table(name = "serv_pre_dette_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServPreDetteExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bailleur")
    private String bailleur;

    @Column(name = "type_cooperation")
    private String type_cooperation;

    @Column(name = "type_fond")
    private String type_fond;

    @Column(name = "montant_a_rembourser")
    private Integer montant_a_rembourser;

    @Column(name = "interet")
    private Integer interet;

    @Column(name = "echeance")
    private LocalDate echeance;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total")
    private Long total;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBailleur() {
        return bailleur;
    }

    public ServPreDetteExt bailleur(String bailleur) {
        this.bailleur = bailleur;
        return this;
    }

    public void setBailleur(String bailleur) {
        this.bailleur = bailleur;
    }

    public String getType_cooperation() {
        return type_cooperation;
    }

    public ServPreDetteExt type_cooperation(String type_cooperation) {
        this.type_cooperation = type_cooperation;
        return this;
    }

    public void setType_cooperation(String type_cooperation) {
        this.type_cooperation = type_cooperation;
    }

    public String getType_fond() {
        return type_fond;
    }

    public ServPreDetteExt type_fond(String type_fond) {
        this.type_fond = type_fond;
        return this;
    }

    public void setType_fond(String type_fond) {
        this.type_fond = type_fond;
    }

    public Integer getMontant_a_rembourser() {
        return montant_a_rembourser;
    }

    public ServPreDetteExt montant_a_rembourser(Integer montant_a_rembourser) {
        this.montant_a_rembourser = montant_a_rembourser;
        return this;
    }

    public void setMontant_a_rembourser(Integer montant_a_rembourser) {
        this.montant_a_rembourser = montant_a_rembourser;
    }

    public Integer getInteret() {
        return interet;
    }

    public ServPreDetteExt interet(Integer interet) {
        this.interet = interet;
        return this;
    }

    public void setInteret(Integer interet) {
        this.interet = interet;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public ServPreDetteExt echeance(LocalDate echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public ServPreDetteExt date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public ServPreDetteExt total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServPreDetteExt)) {
            return false;
        }
        return id != null && id.equals(((ServPreDetteExt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteExt{" +
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
