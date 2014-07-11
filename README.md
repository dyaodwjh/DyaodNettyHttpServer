DyaodNettyHttpServer
====================

使用netty实现的轻量 httpServer

配置文件
  <bean name="testController" class="com.dyaod.demo.controller.TestController" scope="prototype" init-method="init"/>
    <bean name="test2Controller" class="com.dyaod.demo.controller.Test2Controller" scope="prototype" init-method="init"/>

    <bean class="com.dyaod.system.netty.url.UrlMap" scope="singleton" init-method="init">
        <property name="urlMap" >
            <map>
                <entry key="/index.do" value="testController#index#post"/>
                <entry key="/hello.do" value="testController#hello#post" />
                <entry key="/test2/hello.do" value="test2Controller#hello#get" />
            </map>
        </property>
    </bean>


对应代码
    public class TestController {

        private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TestController.class);

        public void init(){
            logger.info("TestController init");
        }

        public String index(FullHttpRequest request, Map<String, String> parameterMap,String content ){

            logger.info("index:"+content);
            return content;
        }

        public String hello(FullHttpRequest request, Map<String, String> parameterMap,String content){


            return content;
        }
    }