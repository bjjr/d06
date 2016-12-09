package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RecipeCopyRepository;
import domain.RecipeCopy;

@Component
@Transactional
public class StringToRecipeCopyConverter implements Converter<String, RecipeCopy> {

	@Autowired
	RecipeCopyRepository recipeCopyRepository;

	@Override
	public RecipeCopy convert(String text) {
		RecipeCopy result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = recipeCopyRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}