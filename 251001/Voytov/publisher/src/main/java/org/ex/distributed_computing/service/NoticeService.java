package org.ex.distributed_computing.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ex.distributed_computing.config.DiscussionCommunicationProps;
import org.ex.distributed_computing.dto.request.NoticeRequestDTO;
import org.ex.distributed_computing.dto.response.NoticeResponseDTO;
import org.ex.distributed_computing.exception.NotFoundException;
import org.ex.distributed_computing.repository.NewsRepository;
import org.ex.distributed_computing.repository.NoticeRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private static final String STUB_COUNTRY = "US"; // since not supplied

  private final DiscussionCommunicationProps discussionProperties;
  private final NoticeRepository noticeRepository;
  private final NewsRepository newsRepository;
  private final RestTemplate restTemplate;

  public List<NoticeResponseDTO> getAllNotices() {
    return restTemplate.exchange(
        "%s%s/notices".formatted(discussionProperties.getAddress(), discussionProperties.getBaseApiPath()),
        HttpMethod.GET,
        HttpEntity.EMPTY,
        new ParameterizedTypeReference<List<NoticeResponseDTO>>() {
        }
    ).getBody();
  }

  public NoticeResponseDTO getNoticeById(Long id) {
    return restTemplate.getForObject(
        "%s%s/notices/%d".formatted(discussionProperties.getAddress(), discussionProperties.getBaseApiPath(), id),
        NoticeResponseDTO.class
    );
  }

  public NoticeResponseDTO createNotice(NoticeRequestDTO requestDTO) {
    newsRepository.findById(requestDTO.newsId())
        .orElseThrow(() -> new NotFoundException("News not found"));

    Long nextNoticeId = noticeRepository.nextId();

    return restTemplate.exchange(
        "%s%s/notices".formatted(discussionProperties.getAddress(), discussionProperties.getBaseApiPath()),
        HttpMethod.POST,
        new HttpEntity<>(new NoticeRequestDTO(nextNoticeId, requestDTO.newsId(), requestDTO.content(), STUB_COUNTRY)),
        NoticeResponseDTO.class
    ).getBody();
  }

  public NoticeResponseDTO updateNotice(NoticeRequestDTO requestDTO) {
    newsRepository.findById(requestDTO.newsId())
        .orElseThrow(() -> new NotFoundException("News not found"));

    return restTemplate.exchange(
        "%s%s/notices".formatted(discussionProperties.getAddress(), discussionProperties.getBaseApiPath()),
        HttpMethod.PUT,
        new HttpEntity<>(new NoticeRequestDTO(requestDTO.id(), requestDTO.newsId(), requestDTO.content(), STUB_COUNTRY)),
        NoticeResponseDTO.class
    ).getBody();
  }

  public void deleteNotice(Long id) {
    restTemplate.exchange(
        "%s%s/notices/%d".formatted(discussionProperties.getAddress(), discussionProperties.getBaseApiPath(), id),
        HttpMethod.DELETE,
        HttpEntity.EMPTY,
        Void.class
    );
  }
}

