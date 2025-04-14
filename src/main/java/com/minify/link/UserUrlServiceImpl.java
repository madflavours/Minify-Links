package com.minify.link;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserUrlServiceImpl implements UserUrlService {

	private final UrlMapper urlMapper;
	private final StatisticsMapper statisticsMapper;
	private final UserUrlRepository userUrlRepository;

	private Supplier<String> getAlphaCode = () -> RandomStringUtils.randomAlphabetic(4);
	private Supplier<String> getNumericCode = () -> RandomStringUtils.randomNumeric(4);

	public UserUrlServiceImpl(UrlMapper urlMapper, StatisticsMapper statisticsMapper,
			UserUrlRepository userUrlRepository) {
		super();
		this.urlMapper = urlMapper;
		this.statisticsMapper = statisticsMapper;
		this.userUrlRepository = userUrlRepository;
	}

	@Override
	public Mono<UserUrlDTO> create(String url) {
		UserUrl userUrl = UserUrl.builder().createdOn(LocalDateTime.now()).updatedOn(LocalDateTime.now()).url(url)
				.shortCode(getAlphaCode.get() + getNumericCode.get()).accessCount(1l).build();

		log.info("Saving New URL: {}", url);
		return userUrlRepository.save(userUrl).doOnNext(saved -> log.info("Saved New URL ID: {}", saved.getId()))
				.flatMap(saved -> getUrl(saved.getShortCode())).onErrorResume(error -> {
					log.error("Error while saving URL: {}", error.getMessage());
					return Mono.empty();
				});
	}

	@Override
	public Mono<UserUrlDTO> getUrl(String shortCode) {
		log.debug("Get URL For ShortCode {]", shortCode);
		Mono<UserUrlDTO> result = userUrlRepository.findByShortCode(shortCode)
				.flatMap(source -> updateAccessCount(shortCode).thenReturn(source)).map(urlMapper);
		return result;
	}

	@Override
	public Mono<UserUrlDTO> update(String shortCode, String url) {
		log.debug("Get URL For ShortCode {]", shortCode);

		return userUrlRepository.findByShortCode(shortCode).flatMap(source -> {
			source.setUrl(url);
			source.setAccessCount(source.getAccessCount() + 1);
			source.setUpdatedOn(LocalDateTime.now());
			return userUrlRepository.save(source);
		}).map(urlMapper);
	}

	@Override
	public Mono<Boolean> delete(String shortCode) {
		log.debug("Get URL For ShortCode {}", shortCode);
		return userUrlRepository.deleteByShortCode(shortCode).then(Mono.just(true)).onErrorResume(e -> {
			log.error("Error deleting record: {}", e.getMessage());
			return Mono.just(false);
		});
	}

	@Override
	public Mono<StatisticsDTO> getStats(String shortCode) {
		log.debug("Get URL For ShortCode {]", shortCode);
		return userUrlRepository.findByShortCode(shortCode).map(statisticsMapper);
	}

	private Mono<UserUrl> updateAccessCount(String shortCode) {
		return userUrlRepository.findByShortCode(shortCode).flatMap(source -> {
			source.setAccessCount(source.getAccessCount() + 1);
			source.setUpdatedOn(LocalDateTime.now());
			return userUrlRepository.save(source);
		});
	}

}
