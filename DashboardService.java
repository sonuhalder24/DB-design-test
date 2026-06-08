package com.fresco.codelab.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresco.codelab.model.CodeLabRepo;
import com.fresco.codelab.model.CodeLabRepoVersion;
import com.fresco.codelab.model.CodeLabUser;
import com.fresco.codelab.repo.CodeLabRepoRepository;
import com.fresco.codelab.repo.CodeLabRepoVersionRepository;
import com.fresco.codelab.repo.CodeLabUserRepository;

@Service
public class DashboardService {

    @Autowired
    private CodeLabUserRepository userRepo;

    @Autowired
    private CodeLabRepoRepository repoRepo;

    @Autowired
    private CodeLabRepoVersionRepository versionRepo;

    @Transactional
    public Long saveRepo(String repo_name, Long userId) {
        CodeLabRepo repo = new CodeLabRepo();
        repo.setRepoName(repo_name);
        repo.setRepoOwnerId(userId);
        CodeLabRepo saved = repoRepo.save(repo);
        return saved.getRepoAutoGenId();
    }

    @Transactional(readOnly = true)
    public List<CodeLabRepo> getUserOwnedRepos(Long userId) {
        return repoRepo.findByRepoOwnerId(userId);
    }

    @Transactional(readOnly = true)
    public CodeLabRepo getRepoWithRepoIdAndOwnerId(Long repoId, Long userId) {
        return repoRepo.findByRepoAutoGenIdAndRepoOwnerId(repoId, userId);
    }

    @Transactional
    public void addRepoToUserName(CodeLabRepo repo, String username, Long ownerId) {
        CodeLabUser developer = userRepo.findByUsername(username);
        if (developer != null) {
            CodeLabRepo managedRepo = repoRepo.findById(repo.getRepoAutoGenId()).orElse(null);
            if (managedRepo != null) {
                managedRepo.getRepoDevelopers().add(developer);
                repoRepo.save(managedRepo);
            }
        }
    }

    @Transactional(readOnly = true)
    public Set<CodeLabRepo> getUserDeveloperRepos(Long userId) {
        CodeLabUser user = userRepo.findById(userId).orElse(null);
        if (user == null) return new java.util.HashSet<>();
        return user.getRepos();
    }

    @Transactional(readOnly = true)
    public CodeLabRepo getRepoWithRepoIdAndDeveloperId(Long repoId, Long userId) {
        CodeLabUser user = userRepo.findById(userId).orElse(null);
        if (user == null) return null;
        for (CodeLabRepo repo : user.getRepos()) {
            if (repo.getRepoAutoGenId().equals(repoId)) {
                return repoRepo.findById(repoId).orElse(null);
            }
        }
        return null;
    }

    @Transactional
    public Integer uploadCode(Long userId, Long repoId) {
        CodeLabRepo repo = repoRepo.findById(repoId).orElse(null);
        if (repo == null) return null;
        int currentCount = versionRepo.findByRepo_RepoAutoGenId(repoId).size();
        CodeLabRepoVersion version = new CodeLabRepoVersion();
        version.setVersion(currentCount + 1);
        version.setVersionOwnerId(userId);
        version.setRepo(repo);
        version.setIsMaster(false);
        version.setIsMrPending(false);
        CodeLabRepoVersion saved = versionRepo.save(version);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public CodeLabRepo getRepoWithRepoIdAndUserIdAndVersionId(Long repoId, Long userId, Integer version) {
        return repoRepo.findById(repoId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CodeLabUser> getAllUsersExcept(Long userId) {
        return userRepo.findByUserAutoGenIdNot(userId);
    }
}
