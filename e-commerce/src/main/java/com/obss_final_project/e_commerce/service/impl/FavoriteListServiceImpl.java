package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.dto.response.favoritelist.FavoriteListResponse;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.exception.product.ProductNotFoundException;
import com.obss_final_project.e_commerce.mapper.FavoriteListMapper;
import com.obss_final_project.e_commerce.mapper.ProductMapper;
import com.obss_final_project.e_commerce.model.FavoriteList;
import com.obss_final_project.e_commerce.model.Product;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.*;
import com.obss_final_project.e_commerce.service.FavoriteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
class FavoriteListServiceImpl implements FavoriteListService {

    private final UserRepository userRepository;
    private final ProductRepository sellerRepository;
    private final FavoriteListRepository favoriteListRepository;

    @Autowired
    public FavoriteListServiceImpl(UserRepository userRepository, ProductRepository sellerRepository, FavoriteListRepository favoriteListRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.favoriteListRepository = favoriteListRepository;
    }

    @Override
    @Transactional
    public FavoriteListResponse addProductToFavoriteList(Long userId, UUID productId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = sellerRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product product1 = favoriteListRepository.findFavoriteProductsByUserId(userId)
                .stream()
                .filter(product2 -> product2.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if(product1 != null){
            throw new RuntimeException("Product is already in favorite list");
        }

        user.getListOfFavoriteProducts().add(productId);
        userRepository.save(user);

        FavoriteList favoriteList = new FavoriteList();
        favoriteList.setUser(user);
        favoriteList.setProduct(product);

        return FavoriteListMapper.mapToFavoriteListResponse(favoriteListRepository.save(favoriteList));
    }

    @Override
    @Transactional
    public void removeProductFromFavoriteList(Long userId, UUID productId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = sellerRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        FavoriteList favoriteList = favoriteListRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Product not found in favorite list"));

        user.getListOfFavoriteProducts().remove(productId);
        userRepository.save(user);

        favoriteListRepository.delete(favoriteList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getFavoriteListByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> favoriteList = favoriteListRepository.findFavoriteProductsByUserId(userId);

        return favoriteList.stream()
                .map(ProductMapper::mapToProductResponse)
                .toList();
    }
}
