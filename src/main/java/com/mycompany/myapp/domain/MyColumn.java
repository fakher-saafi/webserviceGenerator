package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MyColumn.
 */
@Entity
@Table(name = "my_column")
public class MyColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "jhi_type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("")
    private MyTable table;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public MyColumn columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public MyColumn type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyTable getTable() {
        return table;
    }

    public MyColumn table(MyTable myTable) {
        this.table = myTable;
        return this;
    }

    public void setTable(MyTable myTable) {
        this.table = myTable;
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
        MyColumn myColumn = (MyColumn) o;
        if (myColumn.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myColumn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyColumn{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
