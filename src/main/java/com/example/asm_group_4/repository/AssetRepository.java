package com.example.asm_group_4.repository;

import com.example.asm_group_4.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    @Query("SELECT a FROM Asset a WHERE " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.note) LIKE LOWER(concat('%', :keyword, '%')) ")
    List<Asset> search(@Param("keyword") String keyword);

    Page<Asset> findAll(Pageable pageable);
}
