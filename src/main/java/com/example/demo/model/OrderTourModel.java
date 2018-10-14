package com.example.demo.model;

import com.example.demo.entity.Order_tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderTourModel extends PagingAndSortingRepository<Order_tour, Integer> {
    Page<Order_tour> findByIdOrderdetail(int a, Pageable pageable);
    Page<Order_tour> findByStatus(int a, Pageable pageable);
}
