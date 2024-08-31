package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //@Query(value = "SELECT p FROM Product p LEFT JOIN p.categories c WHERE (:keyword IS NULL OR p.name LIKE %:keyword% OR c LIKE %:keyword%)")
    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


    @Query(value = "SELECT p FROM Product p ORDER BY p.price DESC")
    Page<Product> findAllByPriceDesc(Pageable pageable);

    @Query(value = "SELECT p FROM Product p ORDER BY p.price ASC")
    Page<Product> findAllByPriceAsc(Pageable pageable);


    @Query(value = "SELECT p FROM Product p ORDER BY p.createdDate DESC")
    Page<Product> findAllByCreatedDateDesc(Pageable pageable);

    Page<Product> findAllBySellerId(Long sellerId, Pageable pageable);



    @Query(value = "SELECT p FROM Product p " +
            "LEFT JOIN BlackList b ON p.seller.id = b.seller.id AND b.user.id = :userId " +
            "WHERE b.seller.id IS NULL " +
            "AND p.seller.isBlackListed = false " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> findAllByNonBlackListedSellersAndKeyword(@Param("userId") Long userId,
                                                           @Param("keyword") String keyword,
                                                           Pageable pageable);


}
