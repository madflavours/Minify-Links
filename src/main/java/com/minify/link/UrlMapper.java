package com.minify.link;

import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class UrlMapper implements Function<UserUrl, UserUrlDTO> {

	@Override
	public UserUrlDTO apply(UserUrl source) {
		return new UserUrlDTO(
				source.getId().toHexString(), 
				source.getUrl(), 
				source.getShortCode(), 
				source.getCreatedOn(), 
				source.getUpdatedOn()
				);
	}
}
