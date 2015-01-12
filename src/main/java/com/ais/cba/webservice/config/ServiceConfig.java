package com.ais.cba.webservice.config;


import com.ais.cba.webservice.config.model.*;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.AbstractRulesModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.digester3.binder.DigesterLoader.newLoader;

public class ServiceConfig extends AbstractRulesModule {

    private static Environments  environments;

    @Override
    protected void configure()
    {
        forPattern( "resources/environments" ).createObject().ofType( Environments.class ).then()
                .setProperties();
        forPattern( "resources/environments/env").createObject().ofType(Env.class).then().setProperties().then().setNext("addEnv");
        forPattern("resources/environments/env/webService").createObject().ofType(WebServiceConfig.class).then().setNext("setWebServiceConfig");
        forPattern("resources/environments/env/webService/service").createObject().ofType(Service.class).then().setProperties().then().setNext("addService");
        forPattern("resources/environments/env/webService/service/endpoint").setBeanProperty();
        forPattern("resources/environments/env/webService/service/username").setBeanProperty();
        forPattern("resources/environments/env/webService/service/password").setBeanProperty();
        forPattern("resources/environments/env/webService/service/params/param").createObject().ofType(Param.class).then().setProperties().then().setNext("addParam");
    }

    public static Environments parse(InputStream inputStream) throws IOException, SAXException {
        DigesterLoader loader = newLoader( new ServiceConfig() )
                .setNamespaceAware( true )
                .setXIncludeAware( true );
        Digester digester = loader.newDigester();
        ServiceConfig.environments = digester.parse(inputStream);
        return  ServiceConfig.environments;
    }
}
