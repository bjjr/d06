package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Recipe;

@Component
@Transactional
public class RecipeToStringConverter implements Converter<Recipe, String>{

	@Override
	public String convert(Recipe recipe) {
		String result;
		
		if(recipe == null){
			result = null;
		}
		else{
			result = String.valueOf(recipe.getId());
		}
		return result;
	}
	
	

}
