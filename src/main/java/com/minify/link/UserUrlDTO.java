package com.minify.link;

import java.time.LocalDateTime;

public record UserUrlDTO(
		String id, 
		String url, 
		String shortCode,  
		LocalDateTime createdOn,
		LocalDateTime updatedOn)

{}
