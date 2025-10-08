package com.jdbc.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.Gender;
import com.jdbc.enums.PetType;

public class InputUtil {
	private InputUtil()
	{
		
	}
	
	public static int acceptMenuOption(Scanner scanner) {
		System.out.println("Press 1 to add new owner.");
		System.out.println("Press 2 to fetch owner details.");
		System.out.println("Press 3 to update pet details of owner.");
		System.out.println("Press 4 to delete owner details.");
		System.out.println("Press 5 to fetch all owners.");
		System.out.println("Press 6 to update pet details as per pet type.");
		System.out.println("Press 7 to fetch owner details by pet Type");
		System.out.println("Press 8 to add owners using automatic transaction.");
		System.out.println("Press 9 to add owners using manual transaction.");
		System.out.println("Press 10 to add owners using manual transaction and savepoint.");
		int menuOption = scanner.nextInt();
		if (menuOption == 1 || menuOption == 2 || menuOption == 3 || menuOption == 4 
				|| menuOption == 5 || menuOption == 6 || menuOption == 7|| menuOption == 8 
				|| menuOption == 9 || menuOption == 10) {
			return menuOption;
		} else {
			return acceptMenuOption(scanner);
		}
	}

	public static boolean wantToContinue(Scanner scanner) {
		System.out.println("Press Y to continue and N to exit.");
		char choice = scanner.next().toUpperCase().charAt(0);
		return 'Y' == choice;
	}

	public static OwnerDTO acceptOwnerDetailsToSave(Scanner scanner) {
		System.out.println("Enter id of owner:");
		int id = scanner.nextInt();
		System.out.println("Enter first name of owner:");
		String firstName = scanner.next();
		System.out.println("Enter last name of owner:");
		String lastName = scanner.next();
		System.out.println("Enter gender of owner:" + Arrays.asList(Gender.values()).toString());
		String gender = scanner.next().toUpperCase();
		System.out.println("Enter city of owner:");
		String city = scanner.next();
		System.out.println("Enter state of owner:");
		String state = scanner.next();
		System.out.println("Enter mobile number of owner:");
		String mobileNumber = scanner.next();
		System.out.println("Enter email id of owner:");
		String emailId = scanner.next();
		System.out.println("Enter id of pet:");
		int petId = scanner.nextInt();
		System.out.println("Enter name of pet:");
		String petName = scanner.next();
		System.out.println("Enter date of birth of pet (dd-MM-yyyy):");
		String petDateOfBirth = scanner.next();
		System.out.println("Enter gender of pet:" + Arrays.asList(Gender.values()).toString());
		String petGender = scanner.next().toUpperCase();
		System.out.println("Enter pet type:" + Arrays.asList(PetType.values()).toString());
		String petType = scanner.next().toUpperCase();
		try {
			OwnerDTO ownerDTO = new OwnerDTO();
			ownerDTO.setId(id);
			ownerDTO.setFirstName(firstName);
			ownerDTO.setLastName(lastName);
			ownerDTO.setGender(Gender.valueOf(gender));
			ownerDTO.setCity(city);
			ownerDTO.setState(state);
			ownerDTO.setMobileNumber(mobileNumber);
			ownerDTO.setEmailId(emailId);
			ownerDTO.setPetId(petId);
			ownerDTO.setPetName(petName);
			ownerDTO.setPetBirthDate(convertStringToDate(petDateOfBirth));
			ownerDTO.setPetGender(Gender.valueOf(petGender));
			ownerDTO.setPetType(PetType.valueOf(petType));
			return ownerDTO;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return acceptOwnerDetailsToSave(scanner);
		}
	}

	public static String acceptPetDetailsToUpdate(Scanner scanner) {
		System.out.println("Enter updated name of pet:");
		return scanner.next();
	}

	public static int acceptOwnerIdToOperate(Scanner scanner) {
		System.out.println("Enter id of owner:");
		return scanner.nextInt();
	}
	
	public static PetType acceptPetTypeToOperate(Scanner scanner) {
		System.out.println("Enter pet type:" + Arrays.asList(PetType.values()).toString());
		String petType = scanner.next().toUpperCase();
		return PetType.valueOf(petType);
	}
	public static boolean wantToUseCallable(Scanner scanner) {
		System.out.println("Press Y to use callable and N to use preparedStatement.");
		char choice = scanner.next().toUpperCase().charAt(0);
		return 'Y' == choice;
	}

	public static LocalDate convertStringToDate(String stringDate) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(stringDate, format);
	}
	
	public static List<OwnerDTO> generateOwners() {
        Scanner scanner = new Scanner(System.in);
        List<OwnerDTO> ownerDTOList = new ArrayList<>();

        System.out.print("How many owners do you want to insert? ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= n; i++) {
            OwnerDTO ownerDTO = new OwnerDTO();

            System.out.println("\n--- Enter details for Owner " + i + " ---");

            System.out.println("ID: ");
            ownerDTO.setId(Integer.parseInt(scanner.nextLine()));

            System.out.println("First Name: ");
            ownerDTO.setFirstName(scanner.nextLine());

            System.out.println("Last Name: ");
            ownerDTO.setLastName(scanner.nextLine());

            System.out.println("Gender (M/F): ");
            ownerDTO.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));

            System.out.println("City: ");
            ownerDTO.setCity(scanner.nextLine());

            System.out.println("State: ");
            ownerDTO.setState(scanner.nextLine());

            System.out.println("Mobile Number: ");
            ownerDTO.setMobileNumber(scanner.nextLine());

            System.out.println("Email ID: ");
            ownerDTO.setEmailId(scanner.nextLine());

            System.out.println("Pet ID: ");
            ownerDTO.setPetId(Integer.parseInt(scanner.nextLine()));

            System.out.println("Pet Name: ");
            ownerDTO.setPetName(scanner.nextLine());

            System.out.println("Pet Date of Birth (dd-MM-yyyy): ");
            ownerDTO.setPetBirthDate(convertStringToDate(scanner.nextLine()));

            System.out.println("Pet Gender (M/F): ");
            ownerDTO.setPetGender(Gender.valueOf(scanner.nextLine().toUpperCase()));

            System.out.println("Enter pet type: "+ Arrays.asList(PetType.values()).toString());
            ownerDTO.setPetType(PetType.valueOf(scanner.nextLine().toUpperCase()));

            ownerDTOList.add(ownerDTO);
        }

        return ownerDTOList;
    }
	

}
