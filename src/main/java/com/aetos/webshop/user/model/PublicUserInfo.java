package com.aetos.webshop.user.model;

public class PublicUserInfo {

    public PublicUserInfo(WebshopUser webshopUser) {
        userId = webshopUser.getUserId();
        email = webshopUser.getEmail();
        firstName = webshopUser.getFirstName();
        lastName = webshopUser.getLastName();
    }

    Long userId;

    private String email;

    private String firstName;

    private String lastName;

}
