package com.andersen.bitlyclone.service;

import static com.andersen.bitlyclone.utils.Constants.HOST;
import static com.andersen.bitlyclone.utils.Constants.SHORT_LINK_LENGTH;

import com.andersen.bitlyclone.dto.UrlCreationRequestDTO;
import com.andersen.bitlyclone.entity.UrlMapping;
import com.andersen.bitlyclone.exception.CustomUrlExistsException;
import com.andersen.bitlyclone.exception.InternetPageDoesNotExistException;
import com.andersen.bitlyclone.exception.ShortUrlAlreadyExistsException;
import com.andersen.bitlyclone.exception.message.ExceptionMessage;
import com.andersen.bitlyclone.repository.UrlMappingRepository;
import com.andersen.bitlyclone.utils.LinkGenerationUtils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {

  private final UrlMappingRepository urlMappingRepository;

  public String getOriginalUrl(String shortUrl) {
    UrlMapping urlMapping = urlMappingRepository.findByShortLink(shortUrl);
    return (urlMapping != null) ? urlMapping.getLink() : null;
  }

  public String createUrl(UrlCreationRequestDTO urlCreationRequestDTO) {

    UrlMapping existingMapping = urlMappingRepository.findByLink(urlCreationRequestDTO.getUrl());
    if (existingMapping != null) {
      if(urlCreationRequestDTO.getCustomUrlName() != null){
        throw new ShortUrlAlreadyExistsException(String.format(ExceptionMessage.SHORT_URL_ALREADY_EXISTS.getMessage()) + HOST + existingMapping.getShortLink());
      }
      return HOST + existingMapping.getShortLink();
    }

    if (!pingUrl(urlCreationRequestDTO.getUrl())) {
      throw new InternetPageDoesNotExistException(String.format(ExceptionMessage.INTERNET_PAGE_DOESNT_EXIST.getMessage()));
    }

    String shortLink;
    if (urlCreationRequestDTO.getCustomUrlName() != null) {
      shortLink = urlCreationRequestDTO.getCustomUrlName();
      checkCustomShortLinkExists(shortLink);
    } else {
      shortLink = generateUniqueShortLink();
    }

    UrlMapping urlMapping = UrlMapping.builder()
        .link(urlCreationRequestDTO.getUrl())
        .shortLink(shortLink)
        .build();

    urlMappingRepository.save(urlMapping);

    return HOST + shortLink;
  }

  private void checkCustomShortLinkExists(String shortLink) {
    Optional<UrlMapping> existingUrlMapping = Optional.ofNullable(
        urlMappingRepository.findByShortLink(shortLink));

    existingUrlMapping.ifPresent(mapping -> {
      throw new CustomUrlExistsException(String.format(ExceptionMessage.CUSTOM_URL_ALREADY_EXISTS.getMessage()));
    });
  }

  private String generateUniqueShortLink() {
    String shortLink = LinkGenerationUtils.generateLink(SHORT_LINK_LENGTH);
    while (urlMappingRepository.findByShortLink(shortLink) != null) {
      shortLink = LinkGenerationUtils.generateLink(SHORT_LINK_LENGTH);
    }
    return shortLink;
  }

  private boolean pingUrl(String url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("HEAD");
      int responseCode = connection.getResponseCode();
      return responseCode == HttpURLConnection.HTTP_OK;
    } catch (Exception e) {
      return false;
    }
  }
}
