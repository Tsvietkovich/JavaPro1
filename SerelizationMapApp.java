package pro.lecture_1.SerelizationMyApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class SerelizationMapApp {
    public static void serialize(Object o, String path) throws IllegalAccessException {
        Class clzz = o.getClass();
        Field[] fields = clzz.getDeclaredFields();
        Map<String, String> mapOfValues = new HashMap<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)) {
                field.setAccessible(true);
                String key = field.getName();
                if (field.getType() == int.class) {
                    Integer velue = field.getInt(o);
                    mapOfValues.put(key, velue.toString());
                } else if (field.getType() == String.class) {
                    String velue = (String) field.get(o);
                    mapOfValues.put(key, velue);
                } else if (field.getType() == boolean.class) {
                    Boolean velue = (Boolean) field.getBoolean(o);
                    mapOfValues.put(key, velue.toString());
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        Set <Map.Entry<String, String>> set = mapOfValues.entrySet();
        for (Map.Entry<String, String> elem : set) {
            builder.append(elem.getKey()+ "=");
            builder.append(elem.getValue()+ ";");
        }

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(builder.toString());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static <T> T deserialize(String path, Class<T> cls) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T result = (T) cls.newInstance();
        String str = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            str = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        String [] tokens = str.split(";");
        for (String token : tokens) {
            token = token.trim();
            String[] pair = token.split("=");
            Field field = cls.getDeclaredField(pair[0]);
            field.setAccessible(true);
            if (field.isAnnotationPresent(Save.class)) {
                if (field.getType() == int.class) {
                    field.setInt(result, Integer.parseInt(pair[1]));
                } else if (field.getType() == String.class) {
                    field.set(result, pair[1]);
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(result, Boolean.parseBoolean(pair[1]));
                }
            }
        }
    return result;
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Tester clas = new Tester(1,2,"3",true);
        Tester clas2 = null;
        SerelizationMapApp.serialize(clas, "E:\\JAVA\\Projects\\OOP\\src\\pro\\lecture_1\\SerelizationMyApp\\file.txt");
        clas2 = SerelizationMapApp.deserialize("E:\\JAVA\\Projects\\OOP\\src\\pro\\lecture_1\\SerelizationMyApp\\file.txt",clas.getClass());
        System.out.println(clas2);
    }
}
