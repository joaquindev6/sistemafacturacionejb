package com.jfarro.app.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_facturas")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 3331151145L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serie")
    private String serie;

    @Column(name = "numero")
    private String number;

    @Column(name = "nombre")
    private String name;

    @Column(name = "fecha")
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "invoice")
    private List<DetailInvoice> detailInvoices;

    public Invoice() {
        this.client = new Client();
        this.user = new User();
        this.detailInvoices = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DetailInvoice> getDetailInvoices() {
        return detailInvoices;
    }

    public void setDetailInvoices(List<DetailInvoice> detailInvoices) {
        this.detailInvoices = detailInvoices;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("id=").append(id);
        sb.append(", serie='").append(serie).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append(", client=").append(client);
        sb.append(", user=").append(user);
        sb.append(", detailInvoices=").append(detailInvoices);
        sb.append('}');
        return sb.toString();
    }
}
