package com.example.demo.model;

import com.example.demo.entity.Tour_detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TourDetailModel extends PagingAndSortingRepository<Tour_detail, Integer> {
    Page<Tour_detail> findByStatusIsNot(int i, Pageable pageable);
    Page<Tour_detail> findByStatus(int a, Pageable pageable);
    Page<Tour_detail> findByNameStartsWithAndStatusIsNot(String name,int i, Pageable pageable);
    Page<Tour_detail> findByDepartureIsAndNameStartsWithAndStatusIs(String departure, String name, int i ,Pageable pageable);
}
