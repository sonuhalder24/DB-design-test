package com.fresco.codelab.CodeLab;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fresco.codelab.model.CodeLabRepo;
import com.fresco.codelab.model.CodeLabUser;
import com.fresco.codelab.repo.CodeLabRepoRepository;
import com.fresco.codelab.repo.CodeLabRepoVersionRepository;
import com.fresco.codelab.repo.CodeLabUserRepository;
import com.fresco.codelab.service.DashboardService;
import com.fresco.codelab.service.RegisterService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeLabApplicationTests {
	@Autowired
	CodeLabUserRepository userRepo;
	@Autowired
	CodeLabRepoRepository repoRepo;
	@Autowired
	CodeLabRepoVersionRepository versionRepo;
	@Autowired
	RegisterService registerService;
	@Autowired
	DashboardService dashboardService;
	
	public String generateString() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"; 
		StringBuilder sb = new StringBuilder(5); 
		for (int i = 0; i < 5; i++) { 
			int index = (int)(AlphaNumericString.length() * Math.random()); 
			sb.append(AlphaNumericString.charAt(index)); 
		}
		return sb.toString(); 
	}
	static String name, pass, repoName, user2, pass2;
	static Long userId, repoId, userId2;
	
	@Test
	public void test1_registerUser() {
		try{
			name = generateString();
			pass = generateString();
			registerService.registerUser(name, name + "@gmail.com", pass);
			List<CodeLabUser> users = userRepo.findAll();
			assertEquals(users.size(), 1);
			userId = users.get(0).getUserAutoGenId();
			assertEquals(users.get(0).getFullname(), name);
			assertEquals(users.get(0).getUsername(), name + "@gmail.com");
			assertEquals(users.get(0).getPassword(), pass);
		}catch(Error e){ assert(false);}catch(Exception e){ assert(false);}
	}
	@Test
	public void test2_saveRepo() {
		try{
			repoName = generateString();
			dashboardService.saveRepo(repoName, userId);
			List<CodeLabRepo> repos = repoRepo.findAll();
			assertEquals(repos.size(), 1);
			repoId = repos.get(0).getRepoAutoGenId();
			assertEquals(repos.get(0).getRepoName(), repoName);
			assertEquals(repos.get(0).getRepoAutoGenId(), repoId);
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test3_getUserOwnedRepos() {
		try{
			List<CodeLabRepo> repos = dashboardService.getUserOwnedRepos(userId);
			assertEquals(repos.size(), 1);
			assertEquals(repos.get(0).getRepoName(), repoName);
			assertEquals(repos.get(0).getRepoAutoGenId(), repoId);
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test4_getRepoWithRepoIdAndOwnerId() {
		try{
			CodeLabRepo repo = dashboardService.getRepoWithRepoIdAndOwnerId(repoId, userId);
			assertEquals(repoName, repo.getRepoName());
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test5_addRepoToUserName() {
		try{
			user2 = generateString();
			pass2 = generateString();
			userId2 = registerService.registerUser(user2, user2 + "@gmail.com", pass2);
			dashboardService.addRepoToUserName(repoRepo.findById(repoId).get(), user2 + "@gmail.com", userId);
			Set<CodeLabRepo> repos = dashboardService.getUserDeveloperRepos(userId2);
			assertEquals(repos.size(), 1);
			for(CodeLabRepo repo : repos) {
				assertEquals(repo.getRepoName(), repoName);
				assertEquals(repo.getRepoAutoGenId(), repoId);
			}
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test6_getRepoWithRepoIdAndDeveloperId() {
		try{
			CodeLabRepo repo = dashboardService.getRepoWithRepoIdAndDeveloperId(repoId, userId2);
			assertEquals(repo.getRepoName(), repoName);
			assertEquals(repo.getRepoAutoGenId(), repoId);
			List<CodeLabUser> users = repo.getRepoDevelopers();
			assertEquals(users.size(), 1);
			assertEquals(users.get(0).getUserAutoGenId(), userId2);
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test7_uploadCode() {
		try{
			Integer versionId = dashboardService.uploadCode(userId2, repoId);
			CodeLabRepo repo = dashboardService.getRepoWithRepoIdAndUserIdAndVersionId(repoId, userId2, 1);
			assertEquals(repo.getRepoAutoGenId(), repoId);
			assertEquals(repo.getVersions().get(0).getId(), versionId);
			assertEquals(repo.getVersions().get(0).getVersionOwnerId(), userId2);
			assertEquals(repo.getVersions().get(0).getRepo().getRepoAutoGenId(), repoId);
			assertEquals(repo.getVersions().get(0).getRepo().getRepoDevelopers().get(0).getUserAutoGenId(), userId2);
		}catch(Throwable e){ assert(false);}
	}
	@Test
	public void test8_getAllUsersExcept() {
		try{
			assertEquals(dashboardService.getAllUsersExcept(userId).get(0).getUserAutoGenId(), userId2);
			assertEquals(dashboardService.getAllUsersExcept(userId2).get(0).getUserAutoGenId(), userId);
		}catch(Throwable e){ assert(false);}
	}
}

