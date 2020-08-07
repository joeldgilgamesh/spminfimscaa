package com.spminfiscaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SolEngNonDec.
 */
@Entity
@Table(name = "sol_eng_non_dec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SolEngNonDec implements Serializable {

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

    @Column(name = "commission")
    private Integer commission;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "solde")
    private Long solde;

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

    public SolEngNonDec bailleur(String bailleur) {
        this.bailleur = bailleur;
        return this;
    }

    public void setBailleur(String bailleur) {
        this.bailleur = bailleur;
    }

    public String getType_cooperation() {
        return type_cooperation;
    }

    public SolEngNonDec type_cooperation(String type_cooperation) {
        this.type_cooperation = type_cooperation;
        return this;
    }

    public void setType_cooperation(String type_cooperation) {
        this.type_cooperation = type_cooperation;
    }

    public String getType_fond() {
        return type_fond;
    }

    public SolEngNonDec type_fond(String type_fond) {
        this.type_fond = type_fond;
        return this;
    }

    public void setType_fond(String type_fond) {
        this.type_fond = type_fond;
    }

    public Integer getCommission() {
        return commission;
    }

    public SolEngNonDec commission(Integer commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public LocalDate getDate() {
        return date;
    }

    public SolEngNonDec date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getSolde() {
        return solde;
    }

    public SolEngNonDec solde(Long solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Long solde) {
        this.solde = solde;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolEngNonDec)) {
            return false;
        }
        return id != null && id.equals(((SolEngNonDec) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolEngNonDec{" +
            "id=" + getId() +
            ", bailleur='" + getBailleur() + "'" +
            ", type_cooperation='" + getType_cooperation() + "'" +
            ", type_fond='" + getType_fond() + "'" +
            ", commission=" + getCommission() +
            ", date='" + getDate() + "'" +
            ", solde=" + getSolde() +
            "}";
    }
}
