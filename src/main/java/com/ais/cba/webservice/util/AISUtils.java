package com.ais.cba.webservice.util;

import com.ais.cba.webservice.config.model.Param;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: son.nguyen
 * Date: 10/28/13
 * Time: 4:49 PM
 */
public class AISUtils {

    private static Logger logger = LoggerFactory.getLogger(AISUtils.class);

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    protected static ObjectMapper mapper = new ObjectMapper();

    public static void prOut(Object o) {
        try {
            logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
        } catch (IOException e) {
        }
    }

    public static String currDate2Str() {
        Date date = Calendar.getInstance().getTime();
        return df.format(date);
    }

    public static Map<String, Object> ob2MapValue(Object config) {

        Field[] fields = config.getClass().getDeclaredFields();
        Map<String, Object> mapFields = new HashMap<String, Object>();
        for (Field field : fields) {
            try {
                mapFields.put(field.getName(), field.get(config));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mapFields;
    }


    public static <T> void overrideFromParams(T config, Map<String, Param> defaultParams) {
        Field[] fields = config.getClass().getDeclaredFields();
        Map<String, Field> mapFields = new HashMap<String, Field>();
        for (Field field : fields) {
            mapFields.put(field.getName(), field);
        }
        //get one level supper class
        if (config.getClass().getSuperclass() != null) {
            Field[] supperFields = config.getClass().getSuperclass().getDeclaredFields();
            for (Field field : supperFields) {
                mapFields.put(field.getName(), field);
            }
        }
        Set set = defaultParams.keySet();
        Iterator it = set.iterator();
        Method method, methodGet;
        String key, key1, key2;
        Type type;

        while (it.hasNext()) {
            key = (String) it.next();
            key1 = upperCaseFirst(key);
            Param obv = defaultParams.get(key);
            Field configField = mapFields.get(key);
            if (configField != null) {
                try {
                    method = config.getClass().getMethod(
                            "set" + key1, new Class[]{configField.getType()});

                    methodGet = config.getClass().getMethod(
                            "get" + key1);

                    Object ob = methodGet.invoke(config);
                    type = configField.getGenericType();

                    if (configField.getType().isPrimitive()) {
                        //logger.info(String.format("override primitive params: name(%s), type(%s), value(%s)", configField.getName(), type, obv.getValue()));
                        if ("long".equals(type.toString())) {
                            if (((Long) ob).longValue() <= 0) {
                                method.invoke(config, AISUtils.ob2l(obv.getValue()));
                            }
                        } else if ("int".equals(type.toString())) {
                            if (((Integer) ob).intValue() <= 0) {
                                method.invoke(config, AISUtils.ob2i(obv.getValue()));
                            }

                        } else if ("double".equals(type.toString())) {
                            if (((Double) ob).doubleValue() <= 0) {
                                method.invoke(config, AISUtils.ob2db(obv.getValue()));
                            }
                        }
                    }  else if(ob == null){
                        //logger.info(String.format("override object params: name(%s), type(%s), value(%s)", configField.getName(), type, obv.getValue()));
                        //need override
                        if (type.equals(Integer.class)) {
                            method.invoke(config, AISUtils.ob2I(obv.getValue()));
                        } else if (type.equals(Long.class)) {
                            method.invoke(config, AISUtils.ob2L(obv.getValue()));
                        } else if (type.equals(Date.class)) {
                            method.invoke(config, AISUtils.ob2D(obv.getValue()));
                        } else if (type.equals(Double.class)) {
                            method.invoke(config, AISUtils.ob2Db(obv.getValue()));
                        } else {
                            method.invoke(config, AISUtils.ob2S(obv.getValue()));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String modifyFieldName(String replace, boolean lowerFist, String value) {
        String rs = value;
        if (StringUtils.isNotEmpty(replace)) {
            rs = value.replace(replace, "");
        }
        if (lowerFist) {
            rs = lowserCaseFirst(rs);
        }
        return rs;
    }
    public static <T> void overrideFromParams(T config, Map<String, Param> defaultParams, String replace, boolean lowerFirst) {
        Field[] fields = config.getClass().getDeclaredFields();
        Map<String, Field> mapFields = new HashMap<String, Field>();
        for (Field field : fields) {
            mapFields.put(modifyFieldName(replace, lowerFirst, field.getName()), field);
        }
        //get one level supper class
        if (config.getClass().getSuperclass() != null) {
            Field[] supperFields = config.getClass().getSuperclass().getDeclaredFields();
            for (Field field : supperFields) {
                mapFields.put(modifyFieldName(replace, lowerFirst, field.getName()), field);
            }
        }
        Set set = defaultParams.keySet();
        Iterator it = set.iterator();
        Method method, methodGet;
        String key, key1, key2;
        Type type;

        while (it.hasNext()) {
            key = (String) it.next();
            key1 = upperCaseFirst(key);
            Param obv = defaultParams.get(key);
            Field configField = mapFields.get(key);
            if (configField != null) {
                try {
                    method = config.getClass().getMethod(
                            "set" + key1, new Class[]{configField.getType()});

                    methodGet = config.getClass().getMethod(
                            "get" + key1);

                    Object ob = methodGet.invoke(config);
                    type = configField.getGenericType();

                    if (configField.getType().isPrimitive()) {
                        //logger.info(String.format("override primitive params: name(%s), type(%s), value(%s)", configField.getName(), type, obv.getValue()));
                        if ("long".equals(type.toString())) {
                            if (((Long) ob).longValue() <= 0) {
                                method.invoke(config, AISUtils.ob2l(obv.getValue()));
                            }
                        } else if ("int".equals(type.toString())) {
                            if (((Integer) ob).intValue() <= 0) {
                                method.invoke(config, AISUtils.ob2i(obv.getValue()));
                            }

                        } else if ("double".equals(type.toString())) {
                            if (((Double) ob).doubleValue() <= 0) {
                                method.invoke(config, AISUtils.ob2db(obv.getValue()));
                            }
                        }
                    }  else if(ob == null){
                        //logger.info(String.format("override object params: name(%s), type(%s), value(%s)", configField.getName(), type, obv.getValue()));
                        //need override
                        if (type.equals(Integer.class)) {
                            method.invoke(config, AISUtils.ob2I(obv.getValue()));
                        } else if (type.equals(Long.class)) {
                            method.invoke(config, AISUtils.ob2L(obv.getValue()));
                        } else if (type.equals(Date.class)) {
                            method.invoke(config, AISUtils.ob2D(obv.getValue()));
                        } else if (type.equals(Double.class)) {
                            method.invoke(config, AISUtils.ob2Db(obv.getValue()));
                        } else {
                            method.invoke(config, AISUtils.ob2S(obv.getValue()));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void map2Object(T config, Map<String, Object> map) {
        Field[] fields = config.getClass().getDeclaredFields();
        Map<String, Field> mapFields = new HashMap<String, Field>();
        for (Field field : fields) {
            mapFields.put(field.getName(), field);
        }
        //get one level supper class
        if (config.getClass().getSuperclass() != null) {
            Field[] supperFields = config.getClass().getSuperclass().getDeclaredFields();
            for (Field field : supperFields) {
                mapFields.put(field.getName(), field);
            }
        }
        Set set = map.keySet();
        Iterator it = set.iterator();
        Method method;
        String key, key1, key2;
        Type type;

        while (it.hasNext()) {
            key = (String) it.next();
            key1 = toCamelCase(key);
            key2 = lowserCaseFirst(key1);
            Object obv = map.get(key);
            Field configField = mapFields.get(key2);
            if (configField != null) {
                try {
                    method = config.getClass().getMethod(
                            "set" + key1, new Class[]{configField.getType()});
                    type = configField.getGenericType();

                    if (type.equals(Integer.class)) {
                        method.invoke(config, AISUtils.ob2I(obv));
                    } else if (type.equals(Long.class)) {
                        method.invoke(config, AISUtils.ob2L(obv));
                    } else if (type.equals(Date.class)) {
                        method.invoke(config, AISUtils.ob2D(obv));
                    } else if (type.equals(Double.class)) {
                        method.invoke(config, AISUtils.ob2Db(obv));
                    } else {
                        method.invoke(config, AISUtils.ob2S(obv));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static String toRevertCamelCase(String str) {
        String camelCaseString = "";
        if (StringUtils.isNotEmpty(str)) {
            String[] r = str.split("(?=\\p{Upper})");
            return camelCaseString = StringUtils.join(r, "_").toUpperCase();
        }
        return null;
    }

    private static String lowserCaseFirst(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return "";
    }

    public static String upperCaseFirst(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return "";
    }

    private static String toCamelCase(String str) {
        String camelCaseString = "";
        if (StringUtils.isNotEmpty(str)) {
            str = str.replaceAll("OUT_", "");
            String[] parts = str.split("_");
            for (String part : parts) {
                camelCaseString = camelCaseString + toProperCase(part);
            }
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    public static Long ob2L(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).longValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0l;
                }
                return Long.valueOf(String.valueOf(v));
            }
        }
        return null;
    }

    public static long ob2l(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).longValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0;
                }
                return Long.valueOf(String.valueOf(v));
            }
        }
        return 0;
    }

    public static Integer ob2I(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).intValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0;
                }
                return Integer.valueOf(String.valueOf(v));
            }
        }
        return null;
    }

    public static int ob2i(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).intValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0;
                }
                return Integer.valueOf(String.valueOf(v));
            }
        }
        return 0;
    }

    public static Double ob2Db(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).doubleValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0d;
                }
                return Double.valueOf(String.valueOf(v));
            }
        }
        return null;
    }

    public static double ob2db(Object v) {
        if (v != null) {
            if (v instanceof BigDecimal) {
                return ((BigDecimal) v).doubleValue();
            } else {
                if ("".equals(String.valueOf(v))) {
                    return 0;
                }
                return Double.valueOf(String.valueOf(v));
            }
        }
        return 0;
    }

    public static String ob2S(Object v) {
        if (v != null) {
            return v.toString().trim();
        }
        return null;
    }

    public static Date ob2D(Object v) {
        if (v != null) {
            return (Date) v;
        }
        return null;
    }



    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    public static long nll2l(Long v, long defaultV) {
        if (v == null) {
            return defaultV;
        }
        return v;
    }

    public static double nll2d(Double v, double defaultV) {
        if (v == null) {
            return defaultV;
        }
        return v;
    }

    public static String getConfigFile(String rootPath, String fileName, int level) throws Exception {
        if (level < 2) {
            String path = rootPath;
            if (path == null) {
                path = AISUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            }
            logger.debug(">>>>>>>>>>>>>>> current path : " + level + " : " + path);
            path = new File(URLDecoder.decode(path, "UTF-8")).getAbsolutePath();
            if (StringUtils.isNotEmpty(path)) {
                int i = path.lastIndexOf(File.separator);
                if (i > 0) {
                    path = path.substring(0, i);
                    String npath = path + File.separator + fileName;
                    File f = new File(npath);
                    if (f.exists()) {
                        return f.getAbsolutePath();
                    } else {
                        //try 1 uplevel
                        return AISUtils.getConfigFile(path, fileName, ++level);
                    }
                }
            }
        }
        return null;
    }

}
