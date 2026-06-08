package com.fresco.codelab.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class CodeLabRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repoAutoGenId;
    private String repoName;
    private Long repoOwnerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
        name = "repo_developers",
        joinColumns = @JoinColumn(name = "repo_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<CodeLabUser> repoDevelopers = new ArrayList<>();

    @OneToMany(mappedBy = "repo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<CodeLabRepoVersion> versions = new ArrayList<>();

    public CodeLabRepo() { super(); }

    public Long getRepoAutoGenId() { return repoAutoGenId; }
    public void setRepoAutoGenId(Long repoAutoGenId) { this.repoAutoGenId = repoAutoGenId; }

    public String getRepoName() { return repoName; }
    public void setRepoName(String repoName) { this.repoName = repoName; }

    public Long getRepoOwnerId() { return repoOwnerId; }
    public void setRepoOwnerId(Long repoOwnerId) { this.repoOwnerId = repoOwnerId; }

    public List<CodeLabUser> getRepoDevelopers() { return repoDevelopers; }
    public void setRepoDevelopers(List<CodeLabUser> repoDevelopers) { this.repoDevelopers = repoDevelopers; }

    public List<CodeLabRepoVersion> getVersions() { return versions; }
    public void setVersions(List<CodeLabRepoVersion> versions) { this.versions = versions; }
}
