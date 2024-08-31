package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s WHERE s.companyName LIKE %?1% ")
    Page<Seller> findAllBySearchContaining(String companyName, Pageable pageable);

    @Query(value = "SELECT s FROM Seller s ORDER BY s.rating DESC")
    Page<Seller> findAllByRatingDesc(Pageable pageable);

    boolean existsByCompanyName(String companyName);
    boolean existsByEmail(String email);

}
