package com.minify.link;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Mono<UserUrlDTO> get(@PathVariable String shortCode) {
		log.info("Requesting To Get URL Details For Short Code {}", shortCode);
		return userUrlService.getUrl(shortCode);
	}

	@PostMapping
	public Mono<UserUrlDTO> create(@RequestParam String url) {
		log.info("Requesting To Create URL Details For Short Code {}", url);
		return userUrlService.create(url);
	}

	@PutMapping("/{shortCode}")
	public Mono<UserUrlDTO> update(@PathVariable String shortCode, @RequestBody String url) {
		log.info("Requesting To Update URL Details For Short Code {}", shortCode);
		return userUrlService.update(shortCode, url);
	}

	@DeleteMapping("/{shortCode}")
	public ResponseEntity<Void> delete(@PathVariable String shortCode) {
		log.info("Requesting To Delete URL Details For Short Code {}", shortCode);
		boolean result = userUrlService.delete(shortCode);
		if(result) {
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
