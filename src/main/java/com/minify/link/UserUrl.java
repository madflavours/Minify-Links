package com.minify.link;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "UrlDb")
public class UserUrl {

	@Id
	private ObjectId id;
	private String url;
	private String shortCode;
	private Long accessCount;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
}
