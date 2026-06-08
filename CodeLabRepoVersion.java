package com.fresco.codelab.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CodeLabRepoVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer version;
    private Long versionOwnerId;
    private Boolean isMaster;
    private Boolean isMrPending;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repo_id")
    private CodeLabRepo repo;

    public CodeLabRepoVersion() { super(); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getVersionOwnerId() { return versionOwnerId; }
    public void setVersionOwnerId(Long versionOwnerId) { this.versionOwnerId = versionOwnerId; }

    public Boolean getIsMaster() { return isMaster; }
    public void setIsMaster(Boolean isMaster) { this.isMaster = isMaster; }

    public Boolean getIsMrPending() { return isMrPending; }
    public void setIsMrPending(Boolean isMrPending) { this.isMrPending = isMrPending; }

    public CodeLabRepo getRepo() { return repo; }
    public void setRepo(CodeLabRepo repo) { this.repo = repo; }
}
