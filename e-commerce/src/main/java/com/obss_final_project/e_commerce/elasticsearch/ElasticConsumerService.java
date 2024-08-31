package com.obss_final_project.e_commerce.elasticsearch;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss_final_project.e_commerce.mapper.ProductMapper;
import com.obss_final_project.e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ElasticConsumerService {


    private final ProductESRepository productESRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ElasticConsumerService(ProductESRepository productESRepository, ObjectMapper objectMapper) {

        this.productESRepository = productESRepository;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "product-topic", groupId = "product-group")
    public void consumeProduct(String product) {
        try {

            Product product2 = objectMapper.readValue(product, Product.class);

            ProductES productES = ProductMapper.mapToProductES(product2);
            productESRepository.save(productES);
            System.out.println("Product saved to Elasticsearch: " + productES);
        } catch (Exception e) {
            System.err.println("Failed to process product: " + product);
            e.printStackTrace();
        }
    }


}
