package fidanhykolli.gjejpuneapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fidanhykolli.gjejpuneapp.enums.UserType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String password) {
        this.password = password;
    }

    private boolean hasWorkVisa;
    private Date dateOfBirth;
    private String Address;
    private String phoneNumber;
    private String workExperience;
    private String spokenLanguage;

    public User(UserType role) {
        this.role = role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private UserType role;


    public UserType getRole() {
        return role;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHasWorkVisa() {
        return hasWorkVisa;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasWorkVisa(boolean hasWorkVisa) {
        this.hasWorkVisa = hasWorkVisa;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public User(String name, String surname, String email, String password, boolean hasWorkVisa, Date dateOfBirth, String address, String phoneNumber, String workExperience, String spokenLanguage, UserType role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.hasWorkVisa = hasWorkVisa;
        this.dateOfBirth = dateOfBirth;
        this.Address = address;
        this.phoneNumber = phoneNumber;
        this.workExperience = workExperience;
        this.spokenLanguage = spokenLanguage;
        this.role = role;
    }

    public User() {

    }

}
