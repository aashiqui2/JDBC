package com.jdbc.main;


import java.util.Scanner;

import com.jdbc.service.OwnerService;
import com.jdbc.service.impl.OwnerServiceImpl;
import com.jdbc.util.InputUtil;


public class App {
	private OwnerService ownerService;

	public static void main(String[] args) {
		App app = new App();
		app.run();
	}

	public void run() {
		ownerService = new OwnerServiceImpl();
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				System.out.println("Welcome to Petistaan");
				int menuOption = InputUtil.acceptMenuOption(scanner);
				switch (menuOption) {
				case 1:
					ownerService.saveOwnersAutomatically(InputUtil.generateOwners());
					System.out.println("Owners have been saved automatically successfully.");
					break;
				case 2:
					ownerService.saveOwnersManually(InputUtil.generateOwners());
					System.out.println("Owners have been saved manually successfully.");
					break;
				case 3:
					ownerService.saveOwnersManuallyWithSavepoint(InputUtil.generateOwners());
					System.out.println("Owners have been saved manually with savepoint successfully.");
					break;
				default:
					System.out.println("Invalid option entered.");
				}
			} while (InputUtil.wantToContinue(scanner));
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

}
