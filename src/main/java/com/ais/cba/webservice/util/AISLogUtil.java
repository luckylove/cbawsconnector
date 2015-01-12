package com.ais.cba.webservice.util;

import com.ais.cba.webservice.config.model.Service;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: son.nguyen
 * Date: 12/25/13
 * Time: 10:55 AM
 */
public class AISLogUtil {

    protected static ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> ob2Map(Object config) {
        Map<String, Object> result = new HashMap<String, Object>();
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

        Method method;
        for (Field f : mapFields.values()) {
            String name = f.getName();
            try {
                method = config.getClass().getMethod(
                        "get" + AISUtils.upperCaseFirst(name));

                Object invoke = method.invoke(config);
                result.put(name, invoke);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return result;
    }



    public static void printInput(Logger log, String sessionId, String wsName, Service cf, List<String> excludeFields, Object obj) {

        log.info(String.format(" - %s : ===== Calling: <%s> =====", sessionId, wsName));
        log.info(String.format(" - %s : === URL: %s", sessionId, cf.getEndpoint()));
        log.info(String.format(" - %s : === Timeout: %s milisecs", sessionId, cf.getTimeout()));
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        log.info(String.format(" - %s : === Input: <%s>  ===", sessionId, wsName));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (excludeFields != null && excludeFields.size() > 0) {
                for (String key : split) {
                    if (!excludeFields.contains(key)) {
                        log.info(String.format(" - %s : <-- %s", sessionId, key));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : <-- %s", sessionId, key));
                }
            }
        }



    }
    public static void printInputMask(Logger log, String sessionId, String wsName, Service cf, List<String> maskFields, Object obj) {

        log.info(String.format(" - %s : ===== Calling: <%s> =====", sessionId, wsName));
        log.info(String.format(" - %s : === URL: %s", sessionId, cf.getEndpoint()));
        log.info(String.format(" - %s : === Timeout: %s milisecs", sessionId, cf.getTimeout()));
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        log.info(String.format(" - %s : === Input: <%s>  ===", sessionId, wsName));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (maskFields != null && maskFields.size() > 0) {
                for (String key : split) {
                    if (!contains(maskFields, key)) {
                        log.info(String.format(" - %s : <-- %s", sessionId, key));
                    }  else {
                        log.info(String.format(" - %s : <-- %s", sessionId, replace(key)));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : <-- %s", sessionId, key));
                }
            }
        }

    }

    private static String replace(String key) {
        if (StringUtils.isNotEmpty(key)) {
            String[] str = key.split(":");
            if (str.length > 1) {
                return str[0] + " : " + "**********************";
            }
        }
        return key;
    }

    private static boolean contains(List<String> fields, String key) {
        if (StringUtils.isNotEmpty(key)) {
            String str = key.toLowerCase();
            for (String k : fields) {
                if (str.contains(k.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printOutput(Logger log, String sessionId, String wsName, Object obj, List<String> excludeFields) {
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        log.info(String.format(" - %s : === Output: <%s>  ===", sessionId, wsName));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (excludeFields != null && excludeFields.size() > 0) {
                for (String key : split) {
                    if (!excludeFields.contains(key)) {
                        log.info(String.format(" - %s : --> %s", sessionId, key));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : --> %s", sessionId, key));
                }
            }
        }
    }


}
