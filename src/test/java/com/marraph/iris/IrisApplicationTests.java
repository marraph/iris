package com.marraph.iris;

import com.marraph.iris.service.UserServiceImplTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IrisApplicationTests {

	private final UserServiceImplTest userServiceImplTest;

	public IrisApplicationTests() {
		this.userServiceImplTest = new UserServiceImplTest();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void runAllTests() throws Exception {
		userServiceImplTest.setUp();
		userServiceImplTest.testCreate_UserDoesNotExist_ShouldCreateNewUser();
		userServiceImplTest.testDelete_UserExists_ShouldDeleteUser();
	}

}