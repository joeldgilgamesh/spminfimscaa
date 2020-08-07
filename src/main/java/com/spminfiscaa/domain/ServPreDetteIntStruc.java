package com.spminfiscaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ServPreDetteIntStruc.
 */
@Entity
@Table(name = "serv_pre_dette_int_struc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServPreDetteIntStruc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organisme")
    private String organisme;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "groupe")
    private String groupe;

    @Column(name = "principale")
    private Integer principale;

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

    public String getOrganisme() {
        return organisme;
    }

    public ServPreDetteIntStruc organisme(String organisme) {
        this.organisme = organisme;
        return this;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getCategorie() {
        return categorie;
    }

    public ServPreDetteIntStruc categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getGroupe() {
        return groupe;
    }

    public ServPreDetteIntStruc groupe(String groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public Integer getPrincipale() {
        return principale;
    }

    public ServPreDetteIntStruc principale(Integer principale) {
        this.principale = principale;
        return this;
    }

    public void setPrincipale(Integer principale) {
        this.principale = principale;
    }

    public Integer getInteret() {
        return interet;
    }

    public ServPreDetteIntStruc interet(Integer interet) {
        this.interet = interet;
        return this;
    }

    public void setInteret(Integer interet) {
        this.interet = interet;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public ServPreDetteIntStruc echeance(LocalDate echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public ServPreDetteIntStruc date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public ServPreDetteIntStruc total(Long total) {
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
        if (!(o instanceof ServPreDetteIntStruc)) {
            return false;
        }
        return id != null && id.equals(((ServPreDetteIntStruc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteIntStruc{" +
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
