package com.example.main.entity;

import com.example.main.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String email;
    private String password;
    private String name;
    private UserRole role;
    
    @Lob
    @Column(columnDefinition = "longblob")
    private Byte[] img;

    // Add explicit getters (only if needed)
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}


}
