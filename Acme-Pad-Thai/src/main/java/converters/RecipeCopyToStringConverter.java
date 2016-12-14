package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RecipeCopy;

@Component
@Transactional
public class RecipeCopyToStringConverter implements Converter<RecipeCopy, String> {

	@Override
	public String convert(RecipeCopy recipeCopy) {
		String result;

		if (recipeCopy == null)
			result = null;
		else
			result = String.valueOf(recipeCopy.getId());

		return result;
	}

}
