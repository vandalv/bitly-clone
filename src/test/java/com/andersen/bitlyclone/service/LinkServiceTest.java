package com.andersen.bitlyclone.service;

import static com.andersen.bitlyclone.utils.Constants.HOST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.andersen.bitlyclone.data.TestDataFactory;
import com.andersen.bitlyclone.dto.UrlCreationRequestDTO;
import com.andersen.bitlyclone.entity.UrlMapping;
import com.andersen.bitlyclone.exception.CustomUrlExistsException;
import com.andersen.bitlyclone.exception.InternetPageDoesNotExistException;
import com.andersen.bitlyclone.exception.ShortUrlAlreadyExistsException;
import com.andersen.bitlyclone.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

  private String shortUrl;
  private String originalUrl;
  private UrlMapping urlMapping;
  UrlCreationRequestDTO urlCreationRequestDTO;

  @BeforeEach
  public void setUp() {
    shortUrl = TestDataFactory.shortUrl();
    originalUrl = TestDataFactory.originalUrl();
    urlMapping = TestDataFactory.urlMapping();
    urlCreationRequestDTO = TestDataFactory.urlCreationRequestDTO();
  }

  @Mock
  private UrlMappingRepository urlMappingRepository;
  @InjectMocks
  private LinkService linkService;

  @Test
  void testGetOriginalUrl() {
    urlMapping.setLink(originalUrl);
    when(urlMappingRepository.findByShortLink(shortUrl)).thenReturn(urlMapping);

    String result = linkService.getOriginalUrl(shortUrl);

    assertEquals(originalUrl, result);
  }

  @Test
  void testCreateUrl() {
    urlCreationRequestDTO.setUrl(originalUrl);
    when(urlMappingRepository.findByLink(originalUrl)).thenReturn(null);

    String result = linkService.createUrl(urlCreationRequestDTO);

    assertNotNull(result);
  }

  @Test
  void testGetOriginalUrlWhenMappingNotFound() {

    when(urlMappingRepository.findByShortLink(shortUrl)).thenReturn(null);

    String result = linkService.getOriginalUrl(shortUrl);

    assertNull(result);
  }

  @Test
  void testCreateUrlWithCustomUrlNameAlreadyExists() {
    urlCreationRequestDTO.setUrl(originalUrl);
    urlCreationRequestDTO.setCustomUrlName(shortUrl);
    urlMapping.setShortLink(shortUrl);
    when(urlMappingRepository.findByLink(originalUrl)).thenReturn(urlMapping);

    assertThrows(ShortUrlAlreadyExistsException.class, () -> {
      linkService.createUrl(urlCreationRequestDTO);
    });
  }

  @Test
  void testCreateUrlWithCustomUrlNameNoConflict() {
    urlCreationRequestDTO.setUrl(originalUrl);
    urlCreationRequestDTO.setCustomUrlName(shortUrl);
    when(urlMappingRepository.findByLink(originalUrl)).thenReturn(null);

    String result = linkService.createUrl(urlCreationRequestDTO);

    assertTrue(result.contains(shortUrl));
  }

  @Test
  void testCreateUrlWithExistingLink() {
    urlCreationRequestDTO.setUrl(originalUrl);
    urlMapping.setShortLink(shortUrl);
    when(urlMappingRepository.findByLink(originalUrl)).thenReturn(urlMapping);

    String result = linkService.createUrl(urlCreationRequestDTO);

    assertEquals(HOST + shortUrl, result);
  }

  @Test
  void testCreateUrlWithInvalidUrl() {
    urlCreationRequestDTO.setUrl(shortUrl);
    assertThrows(InternetPageDoesNotExistException.class, () -> {
      linkService.createUrl(urlCreationRequestDTO);
    });
  }

  @Test
  void testCreateUrlWithCustomUrlNameConflict() {
    urlCreationRequestDTO.setUrl(originalUrl);
    urlCreationRequestDTO.setCustomUrlName(shortUrl);
    urlMapping.setShortLink(shortUrl);
    when(urlMappingRepository.findByShortLink(shortUrl)).thenReturn(urlMapping);

    assertThrows(CustomUrlExistsException.class, () -> {
      linkService.createUrl(urlCreationRequestDTO);
    });
  }
}