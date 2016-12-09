package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Text;

@Component
@Transactional
public class TextToStringConverter implements Converter<Text, String>{

	@Override
	public String convert(Text text) {
		String res;
		
		if(text == null) {
			res = null;
		} else {
			res = String.valueOf(text.getId());
		}
		
		return res;
	}
}
