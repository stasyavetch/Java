package org.homework.Objects;

import org.springframework.stereotype.Component;

@Component
public class FirstObject {

    public FirstObject(SecondObject secondObject) {
        System.out.println("Create first bean");

        System.out.println("prototype object: " + secondObject.toString());
        System.out.println("prototype object: " + secondObject.toString());
    }
}
