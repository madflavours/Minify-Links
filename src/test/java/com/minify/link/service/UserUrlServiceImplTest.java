package com.minify.link.service;

import com.minify.link.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserUrlServiceImplTest {

    @Mock
    private UrlMapper urlMapper;
    @Mock
    private UserUrlRepository userUrlRepository;

    @InjectMocks
    private UserUrlServiceImpl userUrlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnUserUrlDTO() {
        String url = "http://test.com";
        UserUrl userUrl = UserUrl.builder()
                .url(url)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .shortCode("ABCD1234")
                .accessCount(1L)
                .build();
        UserUrlDTO userUrlDTO = new UserUrlDTO(userUrl.getId() != null ? userUrl.getId().toString() : "", userUrl.getUrl(), userUrl.getShortCode(),
                userUrl.getCreatedOn(), userUrl.getUpdatedOn());

        when(userUrlRepository.save(any(UserUrl.class))).thenReturn(Mono.just(userUrl));
        when(userUrlRepository.findByShortCode(anyString())).thenReturn(Mono.just(userUrl));
        when(urlMapper.apply(any(UserUrl.class))).thenReturn(userUrlDTO);

        StepVerifier.create(userUrlService.create(url))
                .expectNext(userUrlDTO)
                .verifyComplete();
    }

    @Test
    void getUrl_shouldReturnUserUrlDTO() {
        String shortCode = "ABCD1234";
        UserUrl userUrl = UserUrl.builder().shortCode(shortCode).build();
        UserUrlDTO userUrlDTO = new UserUrlDTO(userUrl.getId() != null ? userUrl.getId().toString() : "", userUrl.getUrl(), userUrl.getShortCode(),
                userUrl.getCreatedOn(), userUrl.getUpdatedOn());

        when(userUrlRepository.findByShortCode(shortCode)).thenReturn(Mono.just(userUrl));
        when(userUrlRepository.save(any(UserUrl.class))).thenReturn(Mono.just(userUrl));
        when(urlMapper.apply(userUrl)).thenReturn(userUrlDTO);

        StepVerifier.create(userUrlService.getUrl(shortCode))
                .expectNext(userUrlDTO)
                .verifyComplete();
    }

    @Test
    void update_shouldUpdateAndReturnUserUrlDTO() {
        String shortCode = "ABCD1234";
        String url = "http://updated.com";
        UserUrl userUrl = UserUrl.builder().shortCode(shortCode).url(url).accessCount(1L).build();
        UserUrlDTO userUrlDTO = new UserUrlDTO(userUrl.getId() != null ? userUrl.getId().toString() : "", userUrl.getUrl(), userUrl.getShortCode(),
                userUrl.getCreatedOn(), userUrl.getUpdatedOn());

        when(userUrlRepository.findByShortCode(shortCode)).thenReturn(Mono.just(userUrl));
        when(userUrlRepository.save(any(UserUrl.class))).thenReturn(Mono.just(userUrl));
        when(urlMapper.apply(userUrl)).thenReturn(userUrlDTO);

        StepVerifier.create(userUrlService.update(shortCode, url))
                .expectNext(userUrlDTO)
                .verifyComplete();
    }

    @Test
    void delete_shouldReturnTrueOnSuccess() {
        String shortCode = "ABCD1234";
        when(userUrlRepository.deleteByShortCode(shortCode)).thenReturn(Mono.empty());

        StepVerifier.create(userUrlService.delete(shortCode))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void delete_shouldReturnFalseOnError() {
        String shortCode = "ABCD1234";
        when(userUrlRepository.deleteByShortCode(shortCode)).thenReturn(Mono.error(new RuntimeException("error")));

        StepVerifier.create(userUrlService.delete(shortCode))
                .expectNext(false)
                .verifyComplete();
    }
//
//    @Test
//    void getStats_shouldReturnStatisticsDTO() {
//        String shortCode = "ABCD1234";
//        UserUrl userUrl = UserUrl.builder().shortCode(shortCode).build();
//        StatisticsDTO statisticsDTO = new StatisticsDTO();
//
//        when(userUrlRepository.findByShortCode(shortCode)).thenReturn(Mono.just(userUrl));
//        when(statisticsMapper.apply(userUrl)).thenReturn(statisticsDTO);
//
//        StepVerifier.create(userUrlService.getStats(shortCode))
//                .expectNext(statisticsDTO)
//                .verifyComplete();
//    }
}