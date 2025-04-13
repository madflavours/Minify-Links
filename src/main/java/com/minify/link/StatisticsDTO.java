package com.minify.link;

import java.time.LocalDateTime;

public record StatisticsDTO(
		String id, 
		String url, 
		String shortCode, 
		Long accessCount,
		LocalDateTime createdOn, 
		LocalDateTime updatedOn) {

}
