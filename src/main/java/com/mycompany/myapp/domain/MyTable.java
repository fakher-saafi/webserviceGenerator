package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MyTable.
 */
@Entity
@Table(name = "my_table")
public class MyTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "nb_colomn")
    private Integer nbColomn;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Webservice webservice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public MyTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getNbColomn() {
        return nbColomn;
    }

    public MyTable nbColomn(Integer nbColomn) {
        this.nbColomn = nbColomn;
        return this;
    }

    public void setNbColomn(Integer nbColomn) {
        this.nbColomn = nbColomn;
    }

    public Webservice getWebservice() {
        return webservice;
    }

    public MyTable webservice(Webservice webservice) {
        this.webservice = webservice;
        return this;
    }

    public void setWebservice(Webservice webservice) {
        this.webservice = webservice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyTable myTable = (MyTable) o;
        if (myTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyTable{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", nbColomn=" + getNbColomn() +
            "}";
    }
}
