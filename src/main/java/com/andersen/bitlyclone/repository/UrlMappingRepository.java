package com.andersen.bitlyclone.repository;

import com.andersen.bitlyclone.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {

  UrlMapping findByShortLink(String shortLink);
  UrlMapping findByLink(String link);
}
