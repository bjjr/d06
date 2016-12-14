package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikeSARepository;

import domain.LikeSA;

@Component
@Transactional
public class StringToLikeSAConverter implements Converter<String, LikeSA>{
	@Autowired LikeSARepository likeSARepository;

	@Override
	public LikeSA convert(String text) {
		LikeSA result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}
			else{
				id = Integer.valueOf(text);
				result = likeSARepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
