package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.seller.CreateSellerRequest;
import com.obss_final_project.e_commerce.dto.request.seller.UpdateSellerRequest;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.exception.seller.CompanyNameAlreadyExistsException;
import com.obss_final_project.e_commerce.exception.seller.SellerNotFoundException;
import com.obss_final_project.e_commerce.exception.user.EmailAlreadyExistsException;
import com.obss_final_project.e_commerce.exception.user.UsernameAlreadyExistsException;
import com.obss_final_project.e_commerce.mapper.SellerMapper;
import com.obss_final_project.e_commerce.model.Seller;
import com.obss_final_project.e_commerce.repository.SellerRepository;
import com.obss_final_project.e_commerce.service.FileService;
import com.obss_final_project.e_commerce.service.SellerService;
import com.obss_final_project.e_commerce.service.rules.SellerValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final FileService fileService;
    private final SellerValidationRules sellerValidationRules;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, FileService fileService, SellerValidationRules sellerValidationRules) {
        this.sellerRepository = sellerRepository;
        this.fileService = fileService;
        this.sellerValidationRules = sellerValidationRules;
    }


    @Override
    @Transactional
    public SellerResponse saveSeller(CreateSellerRequest request, MultipartFile file) {

       sellerValidationRules.validateSellerUniqueness(request.getCompanyName(), request.getEmail());

        Seller seller = SellerMapper.mapInputToEntity(request);

        if (file != null && !file.isEmpty()) {
            ResponseEntity<String> stringResponseEntity = fileService.uploadFile(file);
            seller.setLogo(stringResponseEntity.getBody());
        }

        log.info("SellerServiceImpl.createSeller seller: {}", seller);
        return SellerMapper.mapEntityToResponse(sellerRepository.save(seller));
    }

    @Override
    @Transactional
    public SellerResponse updateSellerById(Long id, UpdateSellerRequest request) {

        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));

        sellerValidationRules.validateSellerUniquenessForUpdate(seller, request.getCompanyName());

        Seller updatedSeller = SellerMapper.updateEntity(seller, request);

        log.info("SellerServiceImpl.updateSeller updatedSeller: {}", updatedSeller);
        return SellerMapper.mapEntityToResponse(sellerRepository.save(updatedSeller));
    }

    @Override
    @Transactional
    public String deleteSellerById(Long id) {

        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));

        sellerRepository.delete(seller);
        return "Seller deleted successfully with id: " + id;

    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<SellerResponse> searchSellersByCompanyName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Seller> sellers = sellerRepository.findAllBySearchContaining(keyword, pageable);

        log.info("SellerServiceImpl.searchSellersByCompanyName sellers: {}", sellers);
        return CustomPageResponse.of(sellers.map(SellerMapper::mapEntityToResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public SellerResponse findSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));

        log.info("SellerServiceImpl.findSellerById seller: {}", seller);
        return SellerMapper.mapEntityToResponse(seller);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<SellerResponse> findAllSellers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Seller> sellers = sellerRepository.findAll(pageable);

        log.info("SellerServiceImpl.findAllSellers sellers: {}", sellers);
        return CustomPageResponse.of(sellers.map(SellerMapper::mapEntityToResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<SellerResponse> findAllSellersByRatingDesc(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Seller> sellers = sellerRepository.findAllByRatingDesc(pageable);

        log.info("SellerServiceImpl.findAllSellersByRatingDesc sellers: {}", sellers);
        return CustomPageResponse.of(sellers.map(SellerMapper::mapEntityToResponse));

    }

}
