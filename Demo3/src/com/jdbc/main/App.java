package com.jdbc.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.jdbc.dto.OwnerDTO;
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
		try (Scanner sc = new Scanner(System.in)) {
			do {
				System.out.println("Welcome to Petistaan");
				int menuOption = InputUtil.acceptMenuOption(sc);
				switch (menuOption) {
				case 1:
					OwnerDTO ownerDTO = InputUtil.acceptOwnerDetailsToSave(sc);
					ownerService.saveOwner(ownerDTO);
					System.out.println("Owner has been saved successfully.");
					break;
				case 2:
					int ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					ownerDTO = ownerService.findOwner(ownerId);
					System.out.println("Owner has been fetched successfully.");
					System.out.println(ownerDTO);
					break;
				case 3:
					ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					String petName = InputUtil.acceptPetDetailsToUpdate(sc);
					ownerService.updatePetDetails(ownerId, petName);
					System.out.println("Pet details of owner have been updated successfully.");
					break;
				case 4:
					ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					ownerService.deleteOwner(ownerId);
					System.out.println("Owner has been deleted successfully.");
					break;
				case 5:
					List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
					System.out.println("There are " + ownerDTOList.size() + " owners.");
					ownerDTOList.forEach(System.out::println);
					break;
				case 6:
					String ownerEmailId = InputUtil.acceptOwnerEmailIdToOperate(sc);
					LocalDate petBirthDate = InputUtil.acceptPetBirthDateToOperate(sc);
					ownerDTOList = ownerService.findOwner(ownerEmailId, petBirthDate);
					ownerDTOList.forEach(System.out::println);
					break;
				default:
					System.out.println("Invalid option entered.");
				}

			} while (InputUtil.wantToContinue(sc));

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

}
