package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
       //Mockito.when(repository.existsById(dependentId)).thenReturn(true);


        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){


        Assertions.assertDoesNotThrow (() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }
}
