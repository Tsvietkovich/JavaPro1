package pro.lecture_1.SerelizationMyApp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Tester {


       private int a;
    @Save
    private int b;
    @Save
    private String str;
    @Save
    private boolean flag;

        public Tester() {
        }

        public Tester(int a, int b, String str, boolean flag) {
            this.a = a;
            this.b = b;
            this.str = str;
            this.flag = flag;
        }

    @Override
    public String toString() {
        return "Tester{" +
                "a=" + a +
                ", b=" + b +
                ", str='" + str + '\'' +
                ", flag=" + flag +
                '}';
    }
}

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Save {}


