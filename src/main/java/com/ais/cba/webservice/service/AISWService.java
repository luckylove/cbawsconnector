package com.ais.cba.webservice.service;

import com.ais.cba.webservice.config.ServiceConfig;
import com.ais.cba.webservice.config.model.*;
import com.ais.cba.webservice.service.getcba.GetCBA;
import com.ais.cba.webservice.service.model.CallModel;
import com.ais.cba.webservice.util.AISUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;


/**
 * User: son.nguyen
 * Date: 10/24/13
 * Time: 9:36 PM
 */
public class AISWService {

    private static Logger logger = LoggerFactory.getLogger(AISWService.class);
    private static String DEFAULT_CONFIG_PATH = "cba_webservice_config.xml";

    private static Env activeEnv;

    private static boolean serviceStart = false;

    private static String path;

    static {
        try {
            initService();
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException("Can not start web service");
        }
    }

    public static void initService() throws Exception {

        if (serviceStart == false) {

            path = AISUtils.getConfigFile(null, DEFAULT_CONFIG_PATH, 0);
            InputStream inputStream = null;
            if (path != null) {
                logger.info("===== Init service ===== " + path);
                inputStream = new FileInputStream(new File(path));
            } else {
                //load config from classpath
                logger.info("===== Init service from Class path ===== ");
                ClassPathResource cpr = new ClassPathResource(DEFAULT_CONFIG_PATH);
                inputStream = cpr.getInputStream();
                path = cpr.getPath();
            }
            //map xml config to object
            Environments environments = ServiceConfig.parse(inputStream);
            //get active environment
            activeEnv = environments.getActiveEnv();
            logger.info("===== Init profile: " + activeEnv.getName());
            //create service instance
            serviceStart = true;
            logger.info("===== DONE: Init service =====");
        }
    }

    public static void reloadConfig() {
        if (path != null && activeEnv != null) {
            logger.info("===== Reload webservice config =====" );
            try {
                FileInputStream inputStream = new FileInputStream(new File(path));
                Environments environments = ServiceConfig.parse(inputStream);
                Env env = environments.getActiveEnv();
                WebServiceConfig webServiceConfig = env.getWebServiceConfig();
                Map<String,Service> serviceMap = webServiceConfig.getServiceMap();
                WebServiceConfig localWsConfig = activeEnv.getWebServiceConfig();
                for (String name : serviceMap.keySet()) {
                    Service newService = serviceMap.get(name);
                    Service service = localWsConfig.looupService(name);
                    if (service != null && newService != null) {
                        //assign new timeout
                        service.setTimeout(newService.getTimeout());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("===== DONE: Reload webservice config =====" );
        }
    }


    public static WSObject<Integer> makeCall(String _sessionId, CallModel model) {
        return GetCBA.makeCall(_sessionId, activeEnv.getWebServiceConfig(), model);
    }


    public static WSObject<Integer> getCBAStatus(String _sessionId) {
        return GetCBA.getCBAStatus(_sessionId, activeEnv.getWebServiceConfig());
    }



}
