package com.obss_final_project.e_commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_seller")
public class Seller extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Column(name = "logo")
    private String logo;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "rating", columnDefinition = "DECIMAL(2,1)")
    private Double rating;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private Boolean isBlackListed = false;

}
