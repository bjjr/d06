package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Presentation;

@Component
@Transactional
public class PresentationToStringConverter implements Converter<Presentation, String>{

	@Override
	public String convert(Presentation presentation) {
		String res;
		
		if(presentation == null) {
			res = null;
		} else {
			res = String.valueOf(presentation.getId());
		}
		
		return res;
	}
}
