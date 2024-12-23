package org.homework;

import org.homework.Object.FirstObject;
import org.homework.Object.SecondObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new GenericGroovyApplicationContext("classpath:beansConfig.groovy");

        FirstObject firstBean = (FirstObject) context.getBean("firstBean");
        System.out.println(firstBean.toString());

        SecondObject secondBean = (SecondObject) context.getBean("secondBean");
        System.out.println(secondBean.toString());
    }
}
