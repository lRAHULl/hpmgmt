/**
 * 
 */
package com.hospitalmanagement.patient.main.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

import static com.hospitalmanagement.patient.constants.PatientConstants.*;
import static com.hospitalmanagement.patient.main.driver.InputChoice.*;

/**
 * @author rahul
 *
 */
public class PatientDriver {
	
	static Scanner patientDriverInputScanner = new Scanner(System.in);
	static PatientServicesImpl patientServices = new PatientServicesImpl();
	
	public static void initHospitalManagement() {
		
		Patient patient;
		InputChoice inputChoice;
		FileType inputFileType = getFileInputFromUser();
		patientServices.setFilePath(inputFileType);
		
		int choice;
		do {
			printUserChoice();
			
			choice = 2;
			inputChoice = READ_ALL;
			try {
				choice = stringToInteger(patientDriverInputScanner.nextLine());
				if (choice < 6)
					inputChoice = InputChoice.values()[choice - 1];
				else 
					inputChoice = EXIT;
			} catch (InputConstraintNotAsExceptedException e) {
				System.out.println("Enter the valid input");
			}
			
			switch(inputChoice) {
			case CREATE:
				try {
					patient = getPatientInput();
					patientServices.createNewPatient(patient);
				} catch (PatientDirectoryFullException | IdAlreadyExistsException | InputConstraintNotAsExceptedException | NullPointerException e) {
					if (e.getClass().getSimpleName().equals(PATIENT_DIRECTORY_FULL_EXCEPTION)) {
						System.out.println("The directory is full :(");
					} else if (e.getClass().getSimpleName().equals(ID_ALREADY_EXISTS_EXCEPTION)) {
						System.out.println("The id already exists");
					} else if (e.getClass().getSimpleName().equals(INPUT_CONSTRAINT_NOT_AS_EXPECTED_EXCEPTION)) {
						System.out.println("You entered wrong inputs in id or age fields");
					} else if (e.getClass().getSimpleName().equals(NULL_POINTER_EXCEPTION)) {
						System.out.println("Inputs are not as expected");
					}
				}
				break;
				
			case READ_ALL:
				try {
					System.out.println(patientServices.readAllPatient().toString());
				} catch (NoUserExistsException e1) {
					System.out.println("No Users found!!");
				}
				break;
				
			case UPDATE:
				try {
					patient = getPatientInput();
					Patient updatedPatient = patientServices.updateExistingPatient(patient);
					printResult(updatedPatient);
				} catch (Exception e) {
					if (e.getClass().getSimpleName().equals(PATIENT_WITH_ID_NOT_FOUND_EXCEPTION)) {
						System.out.println("Patient with that id is not found");
					} else {
						e.printStackTrace();
					}
				}
				break;
				
			case DELETE:
				System.out.print("Enter a id to delete: ");
				int patientId = 0;
				try {
					patientId = stringToInteger(patientDriverInputScanner.nextLine());
					Patient deletedPatient = patientServices.deletePatient(patientId);
					printResult(deletedPatient);
				} catch (InputConstraintNotAsExceptedException e) {
					System.out.println("Inputs not as expected");
				} catch (Exception e) {
					if (e.getClass().getSimpleName().equals(PATIENT_WITH_ID_NOT_FOUND_EXCEPTION)) {
						System.out.println("Patient with that id is not found");
					} else {
						e.printStackTrace();
					}
				}
				break;
				
			case EXIT:
				patientDriverInputScanner.close();
				System.out.println("Program Terminated");
				return;
			}
		} while(!inputChoice.equals(EXIT));
	}
	
	private static void printUserChoice() {
		System.out.println("1. Create a patient");
		System.out.println("2. Read All Patients");
		System.out.println("3. Update an existing Patient");
		System.out.println("4. Delete a patient");
		System.out.println("5. Exit");
		System.out.print("Enter a number to do a operation: ");
	}

	private static int stringToInteger(String in) throws InputConstraintNotAsExceptedException {
		try {
			return Integer.parseInt(in);
		} catch (Exception e) {
			throw new InputConstraintNotAsExceptedException("Input must be a number");
		}
	}
	
	
	public static void printResult(Patient patient) {
		System.out.println("RETURN PATIENT\n-------------------- \n Id: " + patient.getPatientId() 
																	+ " Name: " + patient.getPatientName() 
																	+ " Age: " + patient.getPatientAge() 
																	+ " Address: " + patient.getPatientAddress().toString());
	}
	
	
	public static Patient getPatientInput() throws NullPointerException {
		int patientId = 0, patientAge = 0;
		String patientName;
		try {
			patientDriverInputScanner = new Scanner(System.in);
			System.out.print("Enter the Id: ");
			patientId = stringToInteger(patientDriverInputScanner.nextLine());
			System.out.print("Enter the Name: ");
			patientName = patientDriverInputScanner.nextLine();
			System.out.print("Enter the age: ");
			patientAge = stringToInteger(patientDriverInputScanner.nextLine());
			
			List<String> patientAddress = new ArrayList<String>();
			for (int i = 0; i < 3; i++) {
				int index = i + 1;
				System.out.print("Enter the address line " + index + ": ");
				String addr = patientDriverInputScanner.nextLine();
				if (!addr.equals("")) {
					patientAddress.add(addr);
				} else {
					patientAddress.add("no info");
				}
			}
			Patient patient = new Patient();
			patient.setPatientId(patientId);
			patient.setPatientName(patientName);
			patient.setPatientAge(patientAge);
			patient.setPatientAddress(patientAddress);
			return patient;
		} catch (InputConstraintNotAsExceptedException e) {
			if (e.getClass().getSimpleName().equals("InputConstraintNotAsExceptedException")) {
				System.out.println("Inputs not as expected.");
			}
		}
		return null;
	}
	
	public static FileType getFileInputFromUser() {
		System.out.println("1. CSV file\n2. JSON file\n3.exit");
		System.out.print("Enter the type of file you should write to: ");
		
		int fileChoice = 1;
		try {
			fileChoice = stringToInteger(patientDriverInputScanner.nextLine());
		} catch (InputConstraintNotAsExceptedException e) {
			System.out.println("Enter a valid input");
		}
		
		while(true) {
			if (fileChoice == 1) {
				return FileType.CSVFILE;
			} else if (fileChoice == 2) {
				return FileType.JSONFILE;
			} else {
				System.out.println("Enter the correct choice..");
			}
		}
	}
}
	
