package pro.lecture_1;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SaveTo(path = "pro\\lecture_1\\textContainer")
public class TextContainer {
    String text = "It`s a TextContainer";


    @Saver
    public  void save(String path) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))){
            writer.write(text);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

class SaverMain {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class clazz = TextContainer.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Saver.class)) {
                if (clazz.isAnnotationPresent(SaveTo.class)) {
                    Annotation annotation = clazz.getAnnotation(SaveTo.class);
                    Class type = annotation.annotationType();
                    Method methodOfAnnotation = type.getMethod("path");
                    String path = (String) methodOfAnnotation.invoke(annotation); //PATH
                    try {
                        method.invoke(new TextContainer(),path);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface SaveTo{
    String path ();
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Saver{
    String value() default "saver";
}


