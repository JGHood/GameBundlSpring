package com.example.gaming.repository;

import com.example.gaming.model.Bundle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long> {

    Optional<Bundle> findById(Long bundleId);

    // bundles created by a specific user
    Iterable<Bundle> findByCreatedBy(Long userId);

    List<Bundle> findByName(String name);

    List<Bundle> findByPrice(Double price);

    List<Bundle> findByFeatured(Boolean featured);

    Boolean existsByName(String name);

    @Query(value = "SELECT bundles.id, bundles.created_at, bundles.updated_at, bundles.created_by, bundles.updated_by, bundles.name, bundles.price, bundles.featured, bundles.discount_rate\n" +
            "FROM users RIGHT JOIN bundles\n" +
            "ON users.id = bundles.created_by\n" +
            "WHERE users.role_id=2",
            nativeQuery = true)
    List<Bundle> findBundlesCreatedByEmployees();
}
