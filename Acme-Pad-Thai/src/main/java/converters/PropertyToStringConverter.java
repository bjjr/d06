package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Property;

@Component
@Transactional
public class PropertyToStringConverter implements Converter<Property, String>{

	@Override
	public String convert(Property property) {
		String res;
		
		if(property == null) {
			res = null;
		} else {
			res = String.valueOf(property.getId());
		}
		
		return res;
	}
}
