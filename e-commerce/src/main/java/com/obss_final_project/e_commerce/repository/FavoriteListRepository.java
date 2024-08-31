package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.FavoriteList;
import com.obss_final_project.e_commerce.model.Product;
import com.obss_final_project.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {
    Optional<FavoriteList> findByUserAndProduct(User user, Product product);
    List<FavoriteList> findAllByUser(User user);

    @Query("SELECT f.product FROM FavoriteList f WHERE f.user.id = :userId")
    List<Product> findFavoriteProductsByUserId(@Param("userId") Long userId);

}

//