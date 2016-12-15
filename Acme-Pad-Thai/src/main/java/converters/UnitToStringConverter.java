package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Unit;

@Component
@Transactional
public class UnitToStringConverter implements Converter<Unit, String>{

	@Override
	public String convert(Unit unit) {
		String result;
		
		if(unit == null){
			result = null;
		}
		else{
			result = String.valueOf(unit.getId());
		}
		return result;
	}
	
	

}
