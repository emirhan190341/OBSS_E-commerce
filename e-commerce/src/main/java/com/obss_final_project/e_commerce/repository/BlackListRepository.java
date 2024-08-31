package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.BlackList;
import com.obss_final_project.e_commerce.model.Seller;
import com.obss_final_project.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    Optional<BlackList> findByUserAndSeller(User user, Seller seller);

    List<BlackList> findAllByUser(User user);

    @Query("SELECT s FROM Seller s WHERE s.id IN (SELECT b.seller.id FROM BlackList b WHERE b.user.id = ?1)")
    List<Seller> findALlBlackListedSellersByUser(@RequestParam Long userId);
}
