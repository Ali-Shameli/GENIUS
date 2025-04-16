package org.example;

public class Role {
    private String name ;

    public Role (String name) {
        this.name = name ;
    }

    public String getName () {
        return name ;
    }

    public static Role valueOf(String roleName) {
        return new Role(roleName);
    }
}
