package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.VideoRepository;
import domain.Video;

@Component
@Transactional
public class StringToVideoConverter implements Converter<String, Video>{

	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public Video convert(String text) {
		Video res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = videoRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
