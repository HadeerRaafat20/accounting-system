package entity;

import java.util.HashSet;
import java.util.Set;

import helper.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
//User Entity
@Entity
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String username;
 private String password;



 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 private Set<UserRole> userRoles = new HashSet<>();
 
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Set<UserRole> getUserRoles() {
	return userRoles;
}
public void setUserRoles(Set<UserRole> userRoles) {
	this.userRoles = userRoles;
}
public Set<RoleEntity> getRoles() {
    Set<RoleEntity> roles = new HashSet<>();
    for (UserRole userRole : userRoles) {
        roles.add(userRole.getRole()); // Extract the role from the UserRole
    }
    return roles;
}

}
