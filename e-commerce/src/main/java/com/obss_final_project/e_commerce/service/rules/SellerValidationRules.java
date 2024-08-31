package com.obss_final_project.e_commerce.service.rules;

import com.obss_final_project.e_commerce.exception.seller.CompanyNameAlreadyExistsException;
import com.obss_final_project.e_commerce.exception.user.EmailAlreadyExistsException;
import com.obss_final_project.e_commerce.model.Seller;
import com.obss_final_project.e_commerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerValidationRules {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerValidationRules(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void validateSellerUniqueness(String companyName, String email) {
        if (sellerRepository.existsByCompanyName(companyName)) {
            throw new CompanyNameAlreadyExistsException("Company name already exists.");
        }
        if (sellerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }
    }

    public void validateSellerUniquenessForUpdate(Seller existingSeller, String companyName) {
        if (companyName != null && !companyName.equals(existingSeller.getCompanyName())
                && sellerRepository.existsByCompanyName(companyName)) {
            throw new CompanyNameAlreadyExistsException("Company name already exists.");
        }
    }
}
