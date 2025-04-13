package com.minify.link;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserUrlRepository extends ReactiveMongoRepository<UserUrl, String> {
	public Mono<UserUrl> save(UserUrl url);

	public Mono<UserUrl> findById(String id);

	public Mono<UserUrl> findByShortCode(String shortCode);
	
	public Mono<Void> deleteByShortCode(String shortCode);
}
