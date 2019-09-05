/**
 * 
 */
package com.hospitalmanagement.patient.main.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.exceptions.FileReadException;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

import static com.hospitalmanagement.patient.constants.PatientConstants.*;
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
		do {
			try {
				inputFileType = getFileInputFromUser();
				patientServices.setFilePath(inputFileType);
			} catch (InputConstraintNotAsExceptedException e3) {
				
				e3.printStackTrace();
			}
		} while(!inputFileType.equals(FileType.CSVFILE) || !inputFileType.equals(FileType.JSONFILE));
		
		
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
				} catch (PatientDirectoryFullException e2) {
					
				} catch (IdAlreadyExistsException e2) {
					
				} catch (InputConstraintNotAsExceptedException e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
				
			case READ_ALL:
				try {
					System.out.println(patientServices.readAllPatient().toString());
				} catch (NoUserExistsException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (FileReadException e1) {
					e1.printStackTrace();
				}
				
				break;
				
			case UPDATE:
				Patient updatedPatient = null;
				try {
					patient = getPatientInput();
					updatedPatient = patientServices.updateExistingPatient(patient);
				} catch (PatientWithIdNotFoundException e1) {
					
					e1.printStackTrace();
				} catch (InputConstraintNotAsExceptedException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (NoUserExistsException e1) {

					e1.printStackTrace();
				} catch (FileReadException e1) {
					e1.printStackTrace();
				}
				printResult(updatedPatient);
				break;
				
			case DELETE:
				System.out.print("Enter a id to delete: ");
				int patientId = 0;
				
				Patient deletedPatient = null;
				try {
					patientId = stringToInteger(patientDriverInputScanner.nextLine());
					deletedPatient = patientServices.deletePatient(patientId);
				} catch (PatientWithIdNotFoundException e) {
					
					e.printStackTrace();
				} catch (InputConstraintNotAsExceptedException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (NoUserExistsException e) {
					
					e.printStackTrace();
				} catch (FileReadException e) {
					
					e.printStackTrace();
				}
				
				printResult(deletedPatient);
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
			throw new InputConstraintNotAsExceptedException("Input must be a number");
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
		System.out.println("1. CSV file\n2. JSON file\n3.exit");
		System.out.print("Enter the type of file you should write to: ");
		
		int fileChoice = 1;
		fileChoice = stringToInteger(patientDriverInputScanner.nextLine());
		
		
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
	
