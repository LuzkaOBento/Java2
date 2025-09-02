package util;

import annotation.CsvColumn;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCsvExporter {

    public static <T> String exportToCsv(List<T> data, Class<T> clazz) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CsvColumn.class))
                .collect(Collectors.toList());

        
        if (fields.isEmpty()) {
            return "";
        }

        StringBuilder csvContent = new StringBuilder();

 
        String header = fields.stream()
                .map(field -> field.getAnnotation(CsvColumn.class).name())
                .collect(Collectors.joining(","));
        csvContent.append(header).append("\n");


        for (T item : data) {
            String row = fields.stream()
                    .map(field -> {
                        try {
                          
                            field.setAccessible(true);
                            Object value = field.get(item);
                            return value != null ? value.toString() : "";
                        } catch (IllegalAccessException e) {
                           
                            return "";
                        }
                    })
                    .collect(Collectors.joining(","));
            csvContent.append(row).append("\n");
        }

        return csvContent.toString();
    }
}