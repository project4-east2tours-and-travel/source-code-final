package com.example.demo.model;

import com.example.demo.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TourModel extends PagingAndSortingRepository<Tour, Integer> {
    Page<Tour> findByStatusIsNot(int i, Pageable pageable);
    Page<Tour> findByStatus(int a, Pageable pageable);
}
