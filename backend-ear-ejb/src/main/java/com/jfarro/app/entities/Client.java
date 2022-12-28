package com.jfarro.app.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_clientes")
public class Client implements Serializable {

    private static final long serialVersionUID = 324234234222L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String code;

    @Column(name = "nombres")
    private String names;

    @Column(name = "ape_paterno")
    private String apePat;

    @Column(name = "ape_materno")
    private String apeMat;

    @Column(name = "nro_doc")
    private String nroDoc;

    @Column(name = "celular")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "direccion")
    private String direction;

    @OneToOne
    @JoinColumn(name = "id_tipo_documento")
    private DocumentType documentType;

    @Embedded
    private UserHistory userHistory;

    public Client() {
        this.documentType = new DocumentType();
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", names='").append(names).append('\'');
        sb.append(", apePat='").append(apePat).append('\'');
        sb.append(", apeMat='").append(apeMat).append('\'');
        sb.append(", nroDoc='").append(nroDoc).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", direction='").append(direction).append('\'');
        sb.append(", documentType=").append(documentType);
        sb.append(", userHistory=").append(userHistory);
        sb.append('}');
        return sb.toString();
    }
}
