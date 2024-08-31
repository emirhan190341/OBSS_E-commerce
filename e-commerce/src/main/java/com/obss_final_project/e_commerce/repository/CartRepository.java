package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserIdAndProductId(Long userId, UUID productId);

    Page<Cart> findByUserId(Long userId, Pageable pageable);

    List<Cart> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
