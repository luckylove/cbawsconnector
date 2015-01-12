package com.ais.cba.webservice.service;

/**
 * Created with IntelliJ IDEA.
 * User: Nguyen
 * Date: 10/23/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockServiceTest {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    /*static StockQuoteServiceStub stub = null;

    @Before
    public void setUp() throws Exception {
       *//* try {
            StockServiceTest.stub =
                    new StockQuoteServiceStub
                            ("http://localhost:8181/axis2/services/StockQuoteService");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\n\n\n");
        }*//*
    }

    @org.junit.Test
    public void test() {
        *//*getPrice(StockServiceTest.stub);
        update(StockServiceTest.stub);
        getPrice(StockServiceTest.stub);*//*
        try {
            ClassPathResource cpr = new ClassPathResource("webservice_config.xml");
            InputStream is = cpr.getInputStream();
            //map xml config to object
            Environments environments = ServiceConfig.parse(is);
            Assert.assertEquals("UAT",environments.getActive());
            //Assert.assertNotNull(environments.getEnvs().get(0));
            //Assert.assertEquals(2,environments.getEnvs().size());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    *//* fire and forget *//*
    public static void update(StockQuoteServiceStub stub) {
        try {
            StockQuoteServiceStub.Update req = new StockQuoteServiceStub.Update();
            req.setSymbol("ABC");
            req.setPrice(42.35);

            stub.update(req);
            System.err.println("price updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\n\n\n");
        }
    }

    *//* two way call/receive *//*
    public void getPrice(StockQuoteServiceStub stub) {
        try {
            StockQuoteServiceStub.GetPrice req = new StockQuoteServiceStub.GetPrice();

            req.setSymbol("ABC");

            StockQuoteServiceStub.GetPriceResponse res =
                    stub.getPrice(req);

            System.err.println(res.get_return());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\n\n\n");
        }
    }*/
}
