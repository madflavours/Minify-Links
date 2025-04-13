package com.minify.link;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class StatisticsMapper implements Function<UserUrl, StatisticsDTO>{

	@Override
	public StatisticsDTO apply(UserUrl source) {
		return new StatisticsDTO(
				source.getId().toString(), 
				source.getUrl(), 
				source.getShortCode(), 
				source.getAccessCount(),
				source.getCreatedOn(), 
				source.getUpdatedOn());
	}

}
