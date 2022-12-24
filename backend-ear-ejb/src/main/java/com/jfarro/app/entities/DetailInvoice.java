package com.jfarro.app.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_facturas_det")
public class DetailInvoice implements Serializable {

    private static final long serialVersionUID = 322225522L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private int amount;

    @Column(name = "precio_unit")
    private double priceUnit;

    @Column(name = "subtotal")
    private double subtotal;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    private Invoice invoice;

    public DetailInvoice() {
        this.product = new Product();
        this.invoice = new Invoice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DetailInvoice{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", priceUnit=").append(priceUnit);
        sb.append(", subtotal=").append(subtotal);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
