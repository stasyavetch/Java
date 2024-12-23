package org.homework.Objects;

public class FirstObject {
    public FirstObject() {
    }
    public FirstObject(SecondObject secondObject) {
        System.out.println("create first object: ");
        System.out.println("second: " + secondObject.toString());

    }
}
