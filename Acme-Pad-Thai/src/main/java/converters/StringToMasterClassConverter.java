package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MasterClassRepository;
import domain.MasterClass;

@Component
@Transactional
public class StringToMasterClassConverter implements Converter<String, MasterClass>{

	@Autowired
	MasterClassRepository masterClassRepository;
	
	@Override
	public MasterClass convert(String text) {
		MasterClass res;
		int id;
		
		try {
			if(StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = masterClassRepository.findOne(id);
			}
		} catch (Throwable th) {
			throw new IllegalArgumentException(th);
		}
		
		return res;
	}
}
