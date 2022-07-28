package edu.school21.reflection.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    private static Class clazz;
    private static Object object;
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Class> allClasses = new ArrayList<>();
    private static List<Field> fields;
    private static List<Method> methods = new ArrayList<>();

    public static void main(String[] args) {
        try {
            showAllClasses();
            createObject();
            System.out.println("________________________");
            showFields();
            System.out.println("________________________");
            initObject();
            System.out.println("________________________");
            changeValue();
            System.out.println("________________________");
            callMethods();
            System.out.println("________________________");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private static void showAllClasses() throws URISyntaxException {
        allClasses.addAll(Utility.getAllClasses("/edu/school21/reflection/classes"));
        System.out.println("Classes:");
        allClasses.forEach(c -> System.out.println(Utility.getClassName(c)));
        System.out.println("________________________");
    }

    private static void createObject() {
        System.out.println("Enter class name:");
        List<Class> list = new ArrayList<>();
        String answer = scanner.nextLine();
        for (Class tempClass: allClasses) {
            if(Utility.getClassName(tempClass).equals(answer)) {
                list.add(tempClass);
            }
        }
        if (list.isEmpty()) {
            System.out.println("Invalid class name for: " + answer);
            createObject();
        } else {
            clazz = list.get(0);
        }
    }

    private static void showFields() {
        fields = Arrays.asList(clazz.getDeclaredFields());
        List<Method> allMethods = Arrays.asList(clazz.getMethods());
        for (Method checkMethod: allMethods) {
            if (checkMethod.toString().contains(Utility.getPackageName(clazz))) {
                if (!checkMethod.toString().contains("toString()")) {
                    methods.add(checkMethod);
                }
            }
        }
        System.out.println("fields: ");
        fields.forEach(f -> System.out.println("\t" + Utility.getFieldName(f)));
        System.out.println("methods: ");
        methods.forEach(m -> System.out.println("\t" + Utility.getMethodSignature(m)));
    }

    private static void initObject() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("Let's create an object.");
        List<Object> parameters = new ArrayList<>();
        Constructor constructor = Arrays.stream(clazz.getConstructors()).
                filter(c -> (c.getParameterCount() > 0))
                .collect(Collectors.toList()).get(0);

        for (Field field : fields) {
            addParameters(field.getName() + ":", field.getType(), parameters);
        }
        object = constructor.newInstance(parameters.toArray());
        System.out.println("Object created: " + object);
    }

    private static void addParameters(String title, Class param, List<Object> parameters) {
        while (true) {
            System.out.println(title);
            String value = scanner.nextLine().trim();

            try {
                if (param.getName().contains("String")) {
                    parameters.add(value);
                } else if (param.getName().contains("int") || param.getName().contains("Integer")) {
                    parameters.add(Integer.parseInt(value));
                } else if (param.getName().contains("boolean") || param.getName().contains("Boolean")) {
                    parameters.add(Boolean.parseBoolean(value));
                } else if (param.getName().contains("double") || param.getName().contains("Double")) {
                    parameters.add(Double.parseDouble(value));
                } else if (param.getName().contains("long") || param.getName().contains("Long")) {
                    parameters.add(Long.parseLong(value));
                } else {
                    System.out.println("UNKNOWN PARAMETER TYPE");
                    System.exit(-1);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value: " + e.getMessage());
            }
        }
    }

    private static void changeValue() throws IllegalAccessException {
        while (true) {
            System.out.println("Enter name of the field for changing:");
            String name = scanner.nextLine().trim();
            List<Field> list = fields.stream()
                    .filter(f -> name.equalsIgnoreCase(f.getName()))
                    .collect(Collectors.toList());

            if (list.isEmpty()) {
                System.out.println("Field not found: " + name);
            } else {
                Field field = list.get(0);
                field.setAccessible(true);
                System.out.println("Enter " + Utility.getFieldTypeName(field) + " value:");
                String value = scanner.nextLine().trim();
                String str = field.getType().getName();

                if (str.contains("String")) {
                    field.set(object, value);
                } else if (str.contains("int") || str.contains("Integer")) {
                    field.set(object, Integer.parseInt(value));
                } else if (str.contains("boolean") || str.contains("Boolean")) {
                    field.set(object, Boolean.parseBoolean(value));
                } else if (str.contains("double") || str.contains("Double")) {
                    field.set(object, Double.parseDouble(value));
                } else if (str.contains("long") || str.contains("Long")) {
                    field.set(object, Long.parseLong(value));
                } else {
                    System.out.println("UNKNOWN PARAMETER TYPE");
                    System.exit(-1);
                }
                System.out.println("Object updated: " + object);
                break;
            }
        }
    }

    private static void callMethods() throws InvocationTargetException, IllegalAccessException {
        while (true) {
            List<Object> parameters = new ArrayList<>();
            System.out.println("Enter name of the method for call:");
            String name = scanner.nextLine().trim();
            List<Method> list = methods.stream()
                    .filter(m -> name.equalsIgnoreCase(Utility.getMethodName(m)))
                    .collect(Collectors.toList());

            if (list.isEmpty()) {
                System.out.println("Method not found: " + name);
            } else {
                Method method = list.get(0);

                if (!method.isAccessible())
                    method.setAccessible(true);

                for (Class param : method.getParameterTypes()) {
                    addParameters("Enter " + Utility.getValue(param.getName()) + " value:",
                            param, parameters);
                }
                returnedValue(method, method.invoke(object, parameters.toArray()));
                break;
            }
        }
    }

    private static void returnedValue(Method method, Object obj) {
        if (!method.getReturnType().getName().equalsIgnoreCase("void")) {
            System.out.println("Method returned: ");
            System.out.println(obj);
        }
    }

}
