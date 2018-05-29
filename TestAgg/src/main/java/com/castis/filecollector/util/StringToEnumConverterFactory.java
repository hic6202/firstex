package com.castis.filecollector.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

@SuppressWarnings("unchecked")
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

	public <T extends Enum<?>> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToEnumConverter<T>(targetType);
	}
	
	private class StringToEnumConverter<T extends Enum<?>> implements Converter<String, T> {
		private Class<?> enumClass;
		
		public StringToEnumConverter(Class<?> targetClass) {
			enumClass = targetClass;
		}

		public T convert(String source) {
			try {
				return (T) enumClass.getMethod("getEnum", ReflectionParam.STRING_PARAM_CLASS).invoke(this, source.trim());
			} catch (Exception e) {
				return (T) Enum.valueOf(Enum.class, source.trim()); 
			}
		}

	}
}
