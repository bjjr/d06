package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SpamWord;

@Component
@Transactional
public class SpamWordToStringConverter implements Converter<SpamWord, String>{

	@Override
	public String convert(SpamWord spamWord) {
		String result;
		
		if(spamWord == null){
			result = null;
		}
		else{
			result = String.valueOf(spamWord.getId());
		}
		
		return result;
	}

}
