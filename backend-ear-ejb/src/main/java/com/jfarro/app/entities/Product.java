package com.jfarro.app.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_productos")
public class Product implements Serializable {

    private static final long serialVersionUID = 324243242L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String code;

    @Column(name = "nombre")
    private String name;

    @Column(name = "cantidad")
    private int amount;

    @Column(name = "precio")
    private double price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "products")
    private List<Category> categories;

    @Embedded
    private UserHistory userHistory;

    public Product() {
        this.categories = new ArrayList<>();
        this.userHistory = new UserHistory();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", price=").append(price);
        sb.append(", categories=").append(categories);
        sb.append(", userHistory=").append(userHistory);
        sb.append('}');
        return sb.toString();
    }
}
