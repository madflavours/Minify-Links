package com.minify.link;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public interface UserUrlService {

	public Mono<UserUrlDTO> create(String url);

	public Mono<UserUrlDTO> getUrl(String shortCode);

	public Mono<UserUrlDTO> update(String shortCode, String url);

	public boolean delete(String shortCode);

	public Mono<StatisticsDTO> getStats(String shortCode);

}
