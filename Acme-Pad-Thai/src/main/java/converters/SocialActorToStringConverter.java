package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialActor;

@Component
@Transactional
public class SocialActorToStringConverter implements Converter<SocialActor, String>{

	@Override
	public String convert(SocialActor socialActor) {
		String res;
		
		if(socialActor == null) {
			res = null;
		} else {
			res = String.valueOf(socialActor.getId());
		}
		
		return res;
	}
}
