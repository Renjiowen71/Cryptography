import Utility.Utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.math.BigInteger;

public class ClassUtility {
    public static String[] getParameterName(Constructor<?> constructor){
        Parameter[] parameters= constructor.getParameters();
        String[] parameterNames = new String[parameters.length];
        for(int i=0;i< parameterNames.length;i++){
            parameterNames[i] = parameters[i].getName();
        }
        return  parameterNames;
    }
    public static Object initClass(String className)throws Exception{
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructors()[0];
        String[] parameterNames = getParameterName(constructor);
        Class[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = getInputParams(parameterNames, parameterTypes);
        return constructor.newInstance(parameters);
    }

    public static Object[] getInputParams(String[] parameterNames, Class[] parameterTypes){
        Object[] params = new Object[parameterNames.length];
        for(int i =0; i<parameterNames.length; i++){
            System.out.println("Input "+parameterNames[i]+": ");
            String input = Utility.SCANNER.nextLine();
            if(parameterTypes[i].getName().toLowerCase().contains("string")){
                params[i] = input;
            } else if(parameterTypes[i].getName().toLowerCase().contains("biginteger")){
                params[i] = new BigInteger(input);
            } else {
                params[i] = Integer.valueOf(input);
            }
        }
        return params;
    }

    public static Object getObj(String packageName) throws Exception{
        boolean done = false;
        while(!done) {
            try {
                System.out.println("Type your "+packageName+":");
                String algName = Utility.SCANNER.nextLine();
                Object alg = ClassUtility.initClass(packageName+"."+algName);
                done = true;
                return alg;
            } catch (NoClassDefFoundError | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Please try again");
            } catch (Exception e){
                throw e;
            }
        }
        return null;
    }

}
