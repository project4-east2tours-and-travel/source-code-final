package com.example.demo.model;

import com.example.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerModel extends PagingAndSortingRepository<Customer, Integer> {
    Page<Customer> findByStatusIsNot(int i, Pageable pageable);
    Page<Customer> findByStatus(int a, Pageable pageable);
}
