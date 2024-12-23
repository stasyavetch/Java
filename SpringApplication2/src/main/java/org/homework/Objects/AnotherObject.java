package org.homework.Objects;

import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
public class AnotherObject {
    public AnotherObject(SecondObject secondObject) {
        System.out.println("create another object");

        System.out.println("prototype object2: " + secondObject.toString());
    }
}
