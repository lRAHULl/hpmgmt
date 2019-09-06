/**
 * 
 */
package com.hospitalmanagement.patient.main.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.exceptions.FileInputOutputException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

import static com.hospitalmanagement.patient.constants.LoggerConstants.*;
import static com.hospitalmanagement.patient.main.driver.InputChoice.*;

/**
 * @author rahul
 *
 */
public class PatientDriver {
	
	private Scanner patientDriverInputScanner = new Scanner(System.in);
	private PatientServicesImpl patientServices = new PatientServicesImpl();
	
	public void initHospitalManagement() {
		
		Patient patient;
		InputChoice inputChoice;
		FileType inputFileType = FileType.CSVFILE;
		try {
			inputFileType = getFileInputFromUser();
			if (inputFileType != null)
				patientServices.setFilePath(inputFileType);
			
		} catch (InputConstraintNotAsExceptedException e) {
			System.out.println(inputFileType);
			System.out.println(e.getMessage());
			System.out.println("");
		}
		
		
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
				System.out.println("\n Entered the unexpected input \n set the file to default file(CSV)");
			}
			
			
			switch(inputChoice) {
			case CREATE:
					
				try {
					patient = getPatientInput();
					patientServices.createNewPatient(patient);
				} catch (IdAlreadyExistsException e) {
					System.out.println(e.getMessage());
				} catch (InputConstraintNotAsExceptedException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} catch (FileInputOutputException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case READ_ALL:
				try {
					System.out.println(patientServices.readAllPatient().toString());
				} catch (NoUserExistsException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} catch (FileInputOutputException e) {
					System.out.println(e.getMessage());
				} catch (NullPointerException e) {
					System.out.println("No users exists");
				}
				
				break;
				
			case UPDATE:
				Patient updatedPatient = null;
				try {
					patient = getPatientInput();
					updatedPatient = patientServices.updateExistingPatient(patient);
					printResult(updatedPatient);
				} catch (PatientWithIdNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (InputConstraintNotAsExceptedException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} catch (NoUserExistsException e) {
					System.out.println(e.getMessage());
				} catch (FileInputOutputException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case DELETE:
				System.out.print("Enter a id to delete: ");
				int patientId = 0;
				
				Patient deletedPatient = null;
				try {
					patientId = stringToInteger(patientDriverInputScanner.nextLine());
					deletedPatient = patientServices.deletePatient(patientId);
					printResult(deletedPatient);
				} catch (PatientWithIdNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (InputConstraintNotAsExceptedException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				} catch (NoUserExistsException e) {
					System.out.println(e.getMessage());
				} catch (FileInputOutputException e) {
					System.out.println(e.getMessage());
				}
				
				break;
				
			case EXIT:
				patientDriverInputScanner.close();
				System.out.println("Program Terminated");
				return;
			}
		} while(!inputChoice.equals(EXIT));
	}
	
	private void printUserChoice() {
		System.out.println("1. Create a patient");
		System.out.println("2. Read All Patients");
		System.out.println("3. Update an existing Patient");
		System.out.println("4. Delete a patient");
		System.out.println("5. Exit");
		System.out.print("Enter a number to do a operation: ");
	}

	private int stringToInteger(String in) throws InputConstraintNotAsExceptedException {
		try {
			return Integer.parseInt(in);
		} catch (NumberFormatException e) {
			throw new InputConstraintNotAsExceptedException(INPUT_MUST_BE_NUMBER_WARN);
		}
	}
	
	
	public void printResult(Patient patient) {
		System.out.println("RETURN PATIENT\n-------------------- \n Id: " + patient.getPatientId() 
																	+ " Name: " + patient.getPatientName() 
																	+ " Age: " + patient.getPatientAge() 
																	+ " Address: " + patient.getPatientAddress().toString());
	}
	
	
	public Patient getPatientInput() throws InputConstraintNotAsExceptedException {
		int patientId = 0, patientAge = 0;
		String patientName;
		try {
			patientDriverInputScanner = new Scanner(System.in);
			System.out.print("Enter the Id: ");
			patientId = stringToInteger(patientDriverInputScanner.nextLine());
			System.out.print("Enter the Name: ");
			patientName = patientDriverInputScanner.nextLine();
			if (patientName.equals("")) throw new InputConstraintNotAsExceptedException("name cannot be empty");
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
		} catch (NullPointerException e) {
			System.out.println("Null pointer occured");
		}
		return null;
	}
	
	public FileType getFileInputFromUser() throws InputConstraintNotAsExceptedException {
		System.out.println("1. CSV file\n2. JSON file");
		System.out.print("Enter the type of file you should write to: ");
		
		int fileChoice = 1;
		fileChoice = stringToInteger(patientDriverInputScanner.nextLine());
		
		
		while(true) {
			if (fileChoice == 1) {
				return FileType.CSVFILE;
			} else if (fileChoice == 2) {
				return FileType.JSONFILE;
			} else {
				System.out.println("\n Entered the unexpected input \n set the file to default file(CSV) \n");
				return null;
			}
		}
	}
}
	
