package com.example.gaming.model;

import java.util.Set;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Role role;
    private Long roleId;
    private Set<BundleDto> bundles;

    public UserDto() {

    }

    public UserDto(Long id, String firstName, String lastName, String userName, String email, Role role, Long roleId, Set<BundleDto> bundles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.roleId = roleId;
        this.bundles = bundles;
    }

    public UserDto(Long id, String firstName, String lastName, String userName, String email, Long roleId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.roleId = roleId;
    }

    public UserDto(Long id, String firstName, String lastName, String userName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Set<BundleDto> getBundles() {
        return bundles;
    }

    public void setBundles(Set<BundleDto> bundles) {
        this.bundles = bundles;
    }
}
