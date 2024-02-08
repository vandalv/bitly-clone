package com.andersen.bitlyclone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "url_mapping", schema = "bitly_db")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UrlMapping {

  @Id
  @Column(name = "link", nullable = false, unique = true)
  private String link;

  @Column(name = "short_link", nullable = false, unique = true)
  private String shortLink;
}
