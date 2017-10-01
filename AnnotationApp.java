package pro.lecture_1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationApp {

    @Test(a = 2, b = 5)
    public void test (int a, int b){
        System.out.println(a + " " + b);
    }

    public void superMethod () {
        Class cls = AnnotationApp.class;
        Method[] m = cls.getDeclaredMethods();
        for (Method method : m) {
            if (method.isAnnotationPresent(Test.class)) {
               int a =  method.getAnnotation(Test.class).a();
               int b =  method.getAnnotation(Test.class).b();
                try {
                    method.invoke(new AnnotationApp(),a,b);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        AnnotationApp ap = new AnnotationApp();
        ap.superMethod();
    }
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Test {
    int a();
    int b();
}
