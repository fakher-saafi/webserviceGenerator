package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.DataType;

import com.mycompany.myapp.domain.enumeration.DatabaseType;

import com.mycompany.myapp.domain.enumeration.SqlProduct;

/**
 * A Webservice.
 */
@Entity
@Table(name = "webservice")
public class Webservice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "webservice_name")
    private String webserviceName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "datatype")
    private DataType datatype;

    @Enumerated(EnumType.STRING)
    @Column(name = "database_type")
    private DatabaseType databaseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "database_product")
    private SqlProduct databaseProduct;

    @Column(name = "database_path")
    private String databasePath;

    @Column(name = "db_username")
    private String dbUsername;

    @Column(name = "db_pass")
    private String dbPass;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebserviceName() {
        return webserviceName;
    }

    public Webservice webserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
        return this;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }

    public String getDescription() {
        return description;
    }

    public Webservice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public Webservice datatype(DataType datatype) {
        this.datatype = datatype;
        return this;
    }

    public void setDatatype(DataType datatype) {
        this.datatype = datatype;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public Webservice databaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public SqlProduct getDatabaseProduct() {
        return databaseProduct;
    }

    public Webservice databaseProduct(SqlProduct databaseProduct) {
        this.databaseProduct = databaseProduct;
        return this;
    }

    public void setDatabaseProduct(SqlProduct databaseProduct) {
        this.databaseProduct = databaseProduct;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public Webservice databasePath(String databasePath) {
        this.databasePath = databasePath;
        return this;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public Webservice dbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
        return this;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPass() {
        return dbPass;
    }

    public Webservice dbPass(String dbPass) {
        this.dbPass = dbPass;
        return this;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public User getUser() {
        return user;
    }

    public Webservice user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Webservice webservice = (Webservice) o;
        if (webservice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), webservice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Webservice{" +
            "id=" + getId() +
            ", webserviceName='" + getWebserviceName() + "'" +
            ", description='" + getDescription() + "'" +
            ", datatype='" + getDatatype() + "'" +
            ", databaseType='" + getDatabaseType() + "'" +
            ", databaseProduct='" + getDatabaseProduct() + "'" +
            ", databasePath='" + getDatabasePath() + "'" +
            ", dbUsername='" + getDbUsername() + "'" +
            ", dbPass='" + getDbPass() + "'" +
            "}";
    }
}
