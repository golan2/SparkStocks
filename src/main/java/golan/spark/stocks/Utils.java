package golan.spark.stocks;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

/**
 * Created by golaniz on 20/05/2016.
 */
public class Utils {

    /**
     * For use in scenarios where you need to set specific environment values for unit tests, you might find the following hack useful.
     * It will change the environment variables throughout the JVM (so make sure you reset any changes after your test), but will not alter your system environment.
     * I found that a combination of the two dirty hacks by Edward Campbell and anonymous works best, as one of them does not work under linux, one does not work under windows 7.
     * @param newenv a map of environment variables (key => variable name ; value => environment var value)
     */
    protected static void setEnvironmentVariable(Map<String, String> newenv)
    {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newenv);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
