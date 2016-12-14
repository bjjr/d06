package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;
import domain.Presentation;

@Component
@Transactional
public class StringToPresentationConverter implements Converter<String, Presentation>{

	@Autowired
	PresentationRepository presentationRepository;
	
	@Override
	public Presentation convert(String text) {
		Presentation res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = presentationRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
