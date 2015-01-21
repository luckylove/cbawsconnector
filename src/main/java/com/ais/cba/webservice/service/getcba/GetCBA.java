package com.ais.cba.webservice.service.getcba;

import com.ais.cba.webservice.config.model.Param;
import com.ais.cba.webservice.config.model.Service;
import com.ais.cba.webservice.config.model.WSObject;
import com.ais.cba.webservice.config.model.WebServiceConfig;
import com.ais.cba.webservice.service.model.CallModel;
import com.ais.cba.webservice.util.AISLogUtil;
import com.ais.cba.webservice.util.AISUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Map;

/**
 * User: son.nguyen
 * Date: 1/4/15
 * Time: 8:54 PM
 */
public class GetCBA {

    private static Logger logger = LoggerFactory.getLogger(GetCBA.class);


    public static WSObject<Integer> makeCall(String _sessionId, WebServiceConfig configs, CallModel model) {

        Service cf = configs.looupService("makeCall");

        WSObject result = new WSObject();
        if (cf != null) {
            try {
                CBAManagerServiceLocator locator = new CBAManagerServiceLocator();
                CBAManagerSoapBindingStub svc = (CBAManagerSoapBindingStub)locator.getCBAManager(new URL(cf.getEndpoint()));

                if (StringUtils.isNotEmpty(cf.getUsername())) {
                    svc.setUsername(cf.getUsername());
                }
                if (StringUtils.isNotEmpty(cf.getPassword())) {
                    svc.setPassword(cf.getPassword());
                }
                svc.setTimeout(cf.getTimeout());
                //override by default param
                Map<String, Param> params = cf.getParams();
                if (model == null) {
                    model = new CallModel();
                }
                AISUtils.overrideFromParams(model, params);
                AISLogUtil.printInput(logger, _sessionId, "makeCall", cf, null, model);

                int i = svc.makeCall(model.getContact(), model.getAgent(), model.getAlert(), model.getQueuing(), model.getUui(), model.getCustseg());
                result.setResult(i);

            } catch (Exception e) {
                result.setErrorCode(1);
                result.setErrorMsg(e.getMessage());
                logger.error(_sessionId + " : " , e);
            }
        } else {
            logger.error(_sessionId + " : " + "Can not found the config for makeCall" );
            result.setErrorMsg(_sessionId + " : " + "Can not found the config for makeCall");
            result.setErrorCode(2);
        }
        AISLogUtil.printOutput(logger, _sessionId, "makeCall", result, null);
        return result;
    }


    public static WSObject<String> getCBAStatus(String _sessionId, WebServiceConfig configs, String uui) {

        Service cf = configs.looupService("getCBAStatus");

        WSObject<String> result = new WSObject<String>();
        if (cf != null) {
            try {
                CBAManagerServiceLocator locator = new CBAManagerServiceLocator();
                CBAManagerSoapBindingStub svc = (CBAManagerSoapBindingStub)locator.getCBAManager(new URL(cf.getEndpoint()));

                if (StringUtils.isNotEmpty(cf.getUsername())) {
                    svc.setUsername(cf.getUsername());
                }
                if (StringUtils.isNotEmpty(cf.getPassword())) {
                    svc.setPassword(cf.getPassword());
                }
                svc.setTimeout(cf.getTimeout());
                Map<String, Param> params = cf.getParams();
                if (uui == null && params.get("uui") != null) {
                    uui = params.get("uui").getValue();
                }
                //override by default param
                AISLogUtil.printInput(logger, _sessionId, "getCBAStatus", cf, null, uui);
                String i = svc.getCBAStatus(uui);
                result.setResult(i);

            } catch (Exception e) {
                result.setErrorCode(1);
                result.setErrorMsg(e.getMessage());
                logger.error(_sessionId + " : " , e);
            }
        } else {
            logger.error(_sessionId + " : " + "Can not found the config for getCBAStatus" );
            result.setErrorMsg(_sessionId + " : " + "Can not found the config for getCBAStatus");
            result.setErrorCode(2);
        }
        AISLogUtil.printOutput(logger, _sessionId, "getCBAStatus", result, null);
        return result;
    }
}
