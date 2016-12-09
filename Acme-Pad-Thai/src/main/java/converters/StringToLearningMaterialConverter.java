package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LearningMaterialRepository;
import domain.LearningMaterial;

@Component
@Transactional
public class StringToLearningMaterialConverter implements Converter<String, LearningMaterial>{

	@Autowired
	LearningMaterialRepository learningMaterialRepository;
	
	@Override
	public LearningMaterial convert(String text) {
		LearningMaterial res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = learningMaterialRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
