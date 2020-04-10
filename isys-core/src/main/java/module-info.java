module isys.server {

    requires java.base;
    requires java.validation;

    requires spring.core;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.messaging;
    requires spring.beans;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.security.config;
    requires spring.web;
    requires spring.webmvc;
    requires spring.websocket;
    requires spring.data.mongodb;
    requires spring.data.commons;

    requires org.apache.tomcat.embed.core;
    requires com.fasterxml.jackson.annotation;
    requires jjwt;
    requires org.slf4j;
}
