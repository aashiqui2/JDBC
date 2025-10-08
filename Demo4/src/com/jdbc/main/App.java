package com.jdbc.main;


import java.util.List;
import java.util.Scanner;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.PetType;
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
					PetType petType = InputUtil.acceptPetTypeToOperate(scanner);
					List<OwnerDTO> ownerDTOList = ownerService.findOwners(petType);
					System.out.println("There are " + ownerDTOList.size() + " owners with pet type as " + petType);
					ownerDTOList.forEach(System.out::println);
					break;
				case 2:
					petType = InputUtil.acceptPetTypeToOperate(scanner);
					ownerDTOList = ownerService.updatePetDetails(petType, InputUtil.wantToUseCallable(scanner));
					System.out.println("Updated details of " + ownerDTOList.size() + " pets with pet type as " + petType);
					ownerDTOList.forEach(System.out::println);
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
