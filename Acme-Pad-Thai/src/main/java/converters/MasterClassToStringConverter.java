package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MasterClass;

@Component
@Transactional
public class MasterClassToStringConverter implements Converter<MasterClass, String>{

	@Override
	public String convert(MasterClass masterClass) {
		String res;
		
		if(masterClass == null) {
			res = null;
		} else {
			res = String.valueOf(masterClass.getId());
		}
		
		return res;
	}
}
