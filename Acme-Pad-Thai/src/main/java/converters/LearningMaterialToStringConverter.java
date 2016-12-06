package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LearningMaterial;

@Component
@Transactional
public class LearningMaterialToStringConverter implements Converter <LearningMaterial, String>{

	@Override
	public String convert(LearningMaterial learningMaterial) {
		String res;
		
		if(learningMaterial == null) {
			res = null;
		} else {
			res = String.valueOf(learningMaterial.getId());
		}
		
		return res;
	}
}
