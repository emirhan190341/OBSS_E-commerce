package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.dto.response.blacklist.BlackListResponse;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.mapper.BlackListMapper;
import com.obss_final_project.e_commerce.mapper.SellerMapper;
import com.obss_final_project.e_commerce.model.BlackList;
import com.obss_final_project.e_commerce.model.Seller;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.BlackListRepository;
import com.obss_final_project.e_commerce.repository.SellerRepository;
import com.obss_final_project.e_commerce.repository.UserRepository;
import com.obss_final_project.e_commerce.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
class BlackListServiceImpl implements BlackListService {


    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final BlackListRepository blackListRepository;

    @Autowired
    public BlackListServiceImpl(UserRepository userRepository, SellerRepository sellerRepository, BlackListRepository blackListRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.blackListRepository = blackListRepository;
    }

    @Override
    @Transactional
    public BlackListResponse addSellerToBlackList(Long userId, Long sellerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if(user.getIsAdmin()){
            seller.setIsBlackListed(true);
        }

        Optional<BlackList> existingBlackList = blackListRepository.findByUserAndSeller(user, seller);
        if (existingBlackList.isPresent()) {
            throw new RuntimeException("Seller is already in the blacklist");
        }

        user.getListOfBlacklistedSellers().add(sellerId);
        userRepository.save(user);

        BlackList blackList = new BlackList();
        blackList.setUser(user);
        blackList.setSeller(seller);

        return BlackListMapper.mapToBlackListResponse(blackListRepository.save(blackList));
    }

    @Override
    @Transactional
    public void removeSellerFromBlackList(Long userId, Long sellerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        BlackList blackList = blackListRepository.findByUserAndSeller(user, seller)
                .orElseThrow(() -> new RuntimeException("Seller is not in the blacklist"));

        user.getListOfBlacklistedSellers().remove(sellerId);
        userRepository.save(user);

        blackListRepository.delete(blackList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlackListResponse> getAllBlacklistedSellers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        List<BlackList> blackLists = blackListRepository.findAllByUser(user);

        return blackLists.stream().map(BlackListMapper::mapToBlackListResponse).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<SellerResponse> getAllBlacklistedSellersById(Long userId) {

       List<Seller> sellers = blackListRepository.findALlBlackListedSellersByUser(userId);

        return sellers
                .stream()
                .map(SellerMapper::mapEntityToResponse)
                .toList();

    }

}
