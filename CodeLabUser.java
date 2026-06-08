package com.fresco.codelab.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CodeLabUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAutoGenId;
    private String fullname;
    private String username;
    private String password;

    @ManyToMany(mappedBy = "repoDevelopers", fetch = FetchType.LAZY)
    private Set<CodeLabRepo> repos = new HashSet<>();

    public CodeLabUser() { super(); }

    public Long getUserAutoGenId() { return userAutoGenId; }
    public void setUserAutoGenId(Long userAutoGenId) { this.userAutoGenId = userAutoGenId; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<CodeLabRepo> getRepos() { return repos; }
    public void setRepos(Set<CodeLabRepo> repos) { this.repos = repos; }
}
