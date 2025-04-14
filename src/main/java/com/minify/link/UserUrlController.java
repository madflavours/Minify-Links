package com.minify.link;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/shorten")
public class UserUrlController {

	private final UserUrlService userUrlService;

	public UserUrlController(UserUrlService userUrlService) {
		super();
		this.userUrlService = userUrlService;
	}

	@GetMapping("/{shortCode}")
	public Mono<ResponseEntity<UserUrlDTO>> get(@PathVariable String shortCode) {
		log.info("Requesting To Get URL Details For Short Code {}", shortCode);
		return userUrlService.getUrl(shortCode)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<UserUrlDTO>> create(@RequestParam String url) {
	    log.info("Requesting To Create URL Details For Short Code {}", url);

	    return userUrlService.create(url)
	        .map(saved -> {
	            URI location = URI.create("/users/" + saved.shortCode());
	            return ResponseEntity.created(location).body(saved);
	        })
	        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}


	@PutMapping("/{shortCode}")
	public Mono<ResponseEntity<UserUrlDTO>> update(@PathVariable String shortCode, @RequestParam String url) {
		log.info("Requesting To Update URL Details For Short Code {}", shortCode);
		return userUrlService.update(shortCode, url)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{shortCode}")
	public ResponseEntity<Void> delete(@PathVariable String shortCode) {
		log.info("Requesting To Delete URL Details For Short Code {}", shortCode);
		boolean result = userUrlService.delete(shortCode);
		if (result) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{shortCode}/stats")
	public Mono<StatisticsDTO> getStats(@PathVariable String shortCode) {
		log.info("Requesting To Statistics For URL Details For Short Code {}", shortCode);
		return userUrlService.getStats(shortCode);
	}
}
