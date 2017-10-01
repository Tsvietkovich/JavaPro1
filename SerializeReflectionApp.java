package pro.lecture_1;

import sun.reflect.FieldInfo;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;



public class SerializeReflectionApp {

    public static void main(String[] args) {
        File file = new File("E:\\JAVA\\Projects\\OOP\\src\\pro\\lecture_1\\serelizableFile.txt");

        SomeObject ob = new SomeObject();
        serelization(ob,file);
        SomeObject ob2 = (SomeObject) deseralization(file);


    }

    public static void serelization(SomeObject obg, File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(obg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deseralization(File file) {
        Object obj = null;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            try {
                obj = in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

    class SomeObject implements Serializable {

        public double valueD = 0.123;
        @Save
        public int valueI = 90;

        public String valueS = "90";

        public SomeObject() {
        }

        @Override
        public String toString() {
            return "SomeObject{" +
                    "valueD=" + valueD +
                    ", valueI=" + valueI +
                    ", valueS='" + valueS + '\'' +
                    '}';
        }
        private void readObject(ObjectInputStream in)
                throws IOException, ClassNotFoundException {
            Class cl = SomeObject.class;
            Field[] fields = cl.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    try {
                        field.set(this,in.readObject());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void writeObject(ObjectOutputStream out)
                throws IOException {
            Class cl = SomeObject.class;
            Field[] fields = cl.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)) {
                    try {
                        out.writeObject(field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Save{}
