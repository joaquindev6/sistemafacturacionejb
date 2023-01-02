package com.jfarro.app.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_tipo_documento")
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 3242342342L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String cod;

    @Column(name = "nombre")
    private String name;

    @Embedded
    private UserHistory userHistory;

    public DocumentType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocumentType{");
        sb.append("id=").append(id);
        sb.append(", cod='").append(cod).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
