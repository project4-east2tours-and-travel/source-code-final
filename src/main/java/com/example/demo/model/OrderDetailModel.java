package com.example.demo.model;

import com.example.demo.entity.Order_detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderDetailModel extends PagingAndSortingRepository<Order_detail, Integer> {
    Page<Order_detail> findByIdCustomerAndStatus(int ia, int i, Pageable pageable);
    Page<Order_detail> findByIdCustomerAndStatusIsNot(int ia, int s1, Pageable pageable);
    Page<Order_detail> findByStatus(int a, Pageable pageable);
}
