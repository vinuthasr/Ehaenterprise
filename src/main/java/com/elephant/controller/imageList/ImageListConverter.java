package com.elephant.controller.imageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.transaction.Transactional;

@Converter
public class ImageListConverter implements AttributeConverter<List<String>, String> {
@Override
  public String convertToDatabaseColumn(List<String> list) {
    return String.join(",", list); 
  }
  @Override
  public List<String> convertToEntityAttribute(String joined) {
    return new ArrayList<>(Arrays.asList(joined.split(",")));
  }

}
