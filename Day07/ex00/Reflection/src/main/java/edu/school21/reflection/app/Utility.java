package edu.school21.reflection.app;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;


public class Utility {
    public static Set<Class> getAllClasses(String packageName) throws URISyntaxException {

        Set<Class> classes = new HashSet<>();
        try {
            File folder = new File(Utility.class.getResource(packageName).toURI());
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.getName().endsWith(".class")) {
                    String name = packageName.replace('/', '.').substring(1) + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(name));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(classes).orElse(new HashSet<>());
    }

    public static String getClassName(Class clazz) {
        return getValue(clazz.getName());
    }

    public static String getPackageName(Class clazz) {
        String name = clazz.getName();
        name = name.substring(0, name.lastIndexOf('.'));
        return name;
    }

    public static String getFieldName(Field field) {
        String str = field.getType().getName();
        return getValue(str) + " " + field.getName();
    }

    public static String getFieldTypeName(Field field) {
        String str = field.getType().getName();
        return getValue(str);
    }

    public static String getMethodSignature(Method method) {
        String rType = method.getReturnType().getName();
        return getValue(rType) + " " + getMethodName(method);
    }

    public static String getMethodName(Method method) {
        String type = method.toString().substring(0, method.toString().indexOf('(') + 1);
        type = getValue(type);

        Class[] params = method.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            type += getValue(params[i].getName());

            if (i + 1 < params.length) {
                type += ", ";
            }
        }
        type += ")";
        return type;
    }

    public static String getValue(String str) {
        if (str.contains(".")) {
            str = str.substring(str.lastIndexOf('.') + 1);
        }
        return str;
    }


}
