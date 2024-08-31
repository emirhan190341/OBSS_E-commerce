package com.obss_final_project.e_commerce.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductESRepository extends ElasticsearchRepository<ProductES, UUID> {
}
