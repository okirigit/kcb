package com.okiri_george.kcb.config;

import jakarta.jms.ConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

  
  @EnableJms
  @Configuration
  public class ActiveMqConfig {
  	
  	@Value("${spring.activemq.broker-url}")
  	private String BROKER_URL;
  	
  	@Value("${spring.activemq.user}")
  	private String BROKER_USERNAME;
  	
  	@Value("${spring.activemq.password}")
  	private String BROKER_PASSWORD;
  	
  	@Bean
  	public ConnectionFactory connectionFactoryAMQP() {
  	        org.apache.qpid.jms.JmsConnectionFactory connectionFactory = new JmsConnectionFactory();
  	        connectionFactory.setRemoteURI(BROKER_URL);
  	        connectionFactory.setUsername(BROKER_USERNAME);
  	        connectionFactory.setPassword(BROKER_PASSWORD);
  	    return (ConnectionFactory) connectionFactory;
  	}
  	
  	
  	/*@Bean
	public ConnectionFactory connectionFactoryTCP(){
	    ActiveMQConnectionFactory activeMQConnectionFactory  = new ActiveMQConnectionFactory();
	    activeMQConnectionFactory.setBrokerURL(BROKER_URL);
	    activeMQConnectionFactory.setUserName(BROKER_USERNAME); 
	    activeMQConnectionFactory.setPassword(BROKER_PASSWORD);
	    activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.kcb.notify"));
	    return  activeMQConnectionFactory;
	}*/

      @Bean
      public JmsListenerContainerFactory<?> queueListenerFactory() {
          DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
          factory.setMessageConverter(messageConverter());
          return factory;
      }

      @Bean
      public DefaultJmsListenerContainerFactory myJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactoryAMQP());
        //factory.setConnectionFactory(connectionFactoryTCP());
        //factory.setDestinationResolver(destinationResolver());
        factory.setMessageConverter(messageConverter());
        factory.setSessionTransacted(true);
        factory.setConcurrency("5");
        return factory;
      }
      
      
      @Bean
      public MessageConverter messageConverter() {
          MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
          converter.setTargetType(MessageType.TEXT);
          //converter.setTargetType(MessageType.BYTES);
          converter.setTypeIdPropertyName("_type");
          return converter;
      }
  	
  }


