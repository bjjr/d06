package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SocialActorRepository;
import domain.SocialActor;

@Component
@Transactional
public class StringToSocialActorConverter implements Converter<String, SocialActor>{

	@Autowired
	SocialActorRepository SocialActorRepository;
	
	@Override
	public SocialActor convert(String text) {
		SocialActor res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = SocialActorRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
