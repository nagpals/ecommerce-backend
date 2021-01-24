package com.barclays.ecommerce.service;

import com.barclays.ecommerce.repository.CartRepository;
import com.barclays.ecommerce.repository.ProductRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("Product")
@Configuration
public class TestConfigurationsServices {
    @Bean
    @Primary
    public ProductManagementService productManagementService() {
        return Mockito.mock(ProductManagementService.class);
    }

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public CartRepository cartRepository() {
        return Mockito.mock(CartRepository.class);
    }
}
