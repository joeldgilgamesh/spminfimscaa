package com.spminfiscaa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ServPreDetteIntNoStruc.
 */
@Entity
@Table(name = "serv_pre_dette_int_no_struc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServPreDetteIntNoStruc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categorie")
    private String categorie;

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

    public String getCategorie() {
        return categorie;
    }

    public ServPreDetteIntNoStruc categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public ServPreDetteIntNoStruc echeance(LocalDate echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public ServPreDetteIntNoStruc date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTotal() {
        return total;
    }

    public ServPreDetteIntNoStruc total(Long total) {
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
        if (!(o instanceof ServPreDetteIntNoStruc)) {
            return false;
        }
        return id != null && id.equals(((ServPreDetteIntNoStruc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServPreDetteIntNoStruc{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", echeance='" + getEcheance() + "'" +
            ", date='" + getDate() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
