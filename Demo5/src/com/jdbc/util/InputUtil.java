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
	
	private InputUtil() {

	}

	public static int acceptMenuOption(Scanner scanner) {
		System.out.println("Press 1 to add owners using automatic transaction.");
		System.out.println("Press 2 to add owners using manual transaction.");
		System.out.println("Press 3 to add owners using manual transaction and savepoint.");
		int menuOption = scanner.nextInt();
		if (menuOption == 1 || menuOption == 2 || menuOption == 3) {
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
            scanner.close();
        }

        return ownerDTOList;
    }
	
	public static LocalDate convertStringToDate(String stringDate) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(stringDate, format);
	}


}
