package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PropertyRepository;
import domain.Property;

@Component
@Transactional
public class StringToPropertyConverter implements Converter<String, Property>{

	@Autowired
	PropertyRepository propertyRepository;
	
	@Override
	public Property convert(String text) {
		Property res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = propertyRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
