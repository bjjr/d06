package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LikeSA;

@Component
@Transactional
public class LikeSAToStringConverter implements Converter<LikeSA, String>{

	@Override
	public String convert(LikeSA likeSA) {
		String result;
		
		if(likeSA == null){
			result = null;
		}
		else{
			result = String.valueOf(likeSA.getId());
		}
		return result;
	}
	
	

}
