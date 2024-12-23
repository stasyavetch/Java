package org.homework.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SecondObject {
    public SecondObject() {
        System.out.println("Create second bean: " + this.toString());
    }
}
