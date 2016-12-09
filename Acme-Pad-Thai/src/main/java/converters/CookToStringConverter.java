package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Cook;

@Component
@Transactional
public class CookToStringConverter implements Converter<Cook, String>{
	
	@Override
	public String convert(Cook cook) {
		String res;
		
		if(cook == null) {
			res = null;
		} else {
			res = String.valueOf(cook.getId());
		}
		
		return res;
	}
}
