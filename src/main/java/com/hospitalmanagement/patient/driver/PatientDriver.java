/**
 * 
 */
package com.hospitalmanagement.patient.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.main.Main;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

/**
 * @author VC
 *
 */
public class PatientDriver {
	
	public static void initHospitalManagement() {
		Scanner scanner = new Scanner(System.in);
		PatientServicesImpl patientServices;
		int choice;
		int patientId = 0, patientAge = 0;
		String patientName;
		
		System.out.println("1. CSV file\n2. JSON file\n3.exit");
		System.out.print("Enter the type of file you should write to: ");
		
		int fileChoice = scanner.nextInt();
		
		
		while(true) {
			if (fileChoice == 1) {
				Main.getFactoryType = FileType.CSVFILE;
				break;
			} else if (fileChoice == 2) {
				Main.getFactoryType = FileType.JSONFILE;
				break;
			} else if (fileChoice == 3) {
				return;
			} else {
				System.out.println("Enter the correct choice..");
			}
		}
		
		while(true) {
			System.out.println("1. Create a patient");
			System.out.println("2. Read All Patients");
			System.out.println("3. Update an existing Patient");
			System.out.println("4. Delete a patient");
			System.out.println("5. Exit");
			System.out.print("Enter a number to do a operation: ");
			choice = 2;
			try {
				choice = stringToInteger(scanner.nextLine());
			} catch (Exception e) {
				System.out.println("Enter the valid input");
			}
			switch(choice) {
			case 1:
				patientServices = new PatientServicesImpl();
				try {
					System.out.print("Enter the Id: ");
					patientId = stringToInteger(scanner.nextLine());
					System.out.print("Enter the Name: ");
					patientName = scanner.nextLine();
					System.out.print("Enter the age: ");
					patientAge = stringToInteger(scanner.nextLine());
					
					List<String> patientAddress = new ArrayList<String>();
					for (int i = 0; i < 3; i++) {
						int index = i + 1;
						System.out.print("Enter the address line " + index + ": ");
						String addr = scanner.nextLine();
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
					patientServices.createNewPatient(patient);
				} catch (PatientDirectoryFullException | IdAlreadyExistsException | InputConstraintNotAsExceptedException e) {
					if (e.getClass().getSimpleName().equals("PatientDirectoryFullException")) {
						System.out.println("The directory is full :(");
					} else if (e.getClass().getSimpleName().equals("IdAlreadyExistsException")) {
						System.out.println("The id already exists");
					} else if (e.getClass().getSimpleName().equals("InputConstraintNotAsExceptedException")) {
						System.out.println("You entered wrong inputs in id or age fields");
					}
				}
				break;
			case 2:
				patientServices = new PatientServicesImpl();
				try {
					System.out.println(patientServices.readAllPatient().toString());
				} catch (NoUserExistsException e1) {
					System.out.println("No Users found!!");
				}
				break;
			case 3:
				patientServices = new PatientServicesImpl();
				try {
					System.out.print("Enter the Id: ");
					patientId = stringToInteger(scanner.nextLine());
					System.out.print("Enter the new Name: ");
					patientName = scanner.nextLine();
					System.out.print("Enter the new age: ");
					patientAge = stringToInteger(scanner.nextLine());
					
					List<String> patientUpdateAddress = new ArrayList<String>();
					for (int i = 0; i < 3; i++) {
						int index = i + 1;
						System.out.print("Enter the new address line " + index + ": ");
						
						String addr = scanner.nextLine();
						if (!addr.equals("")) {
							patientUpdateAddress.add(addr);
						} else {
							patientUpdateAddress.add("no info");
						}
					}
					Patient patient = new Patient();
					patient.setPatientId(patientId);
					patient.setPatientName(patientName);
					patient.setPatientAge(patientAge);
					patient.setPatientAddress(patientUpdateAddress);
					Patient updatedPatient = patientServices.updateExistingPatient(patient);
					System.out.println("UPDATED PATIENT\n-------------------- \n Id: " + updatedPatient.getPatientId() + " Name: " + updatedPatient.getPatientName() + " Age: " + updatedPatient.getPatientAge());
				} catch (Exception e) {
					if (e.getClass().getSimpleName().equals("PatientWithIdNotFoundException")) {
						System.out.println("Patient with that id is not found");
					} else {
						e.printStackTrace();
					}
				}
				break;
			case 4:
				patientServices = new PatientServicesImpl();
				System.out.print("Enter a id to delete: ");
				try {
					patientId = stringToInteger(scanner.nextLine());

					Patient deletedPatient = patientServices.deletePatient(patientId);
					System.out.println("DELETED PATIENT\n-------------------- \n Id: " + deletedPatient.getPatientId() + " Name: " + deletedPatient.getPatientName() + " Age: " + deletedPatient.getPatientAge());
				
				} catch (InputConstraintNotAsExceptedException e) {
					
				} catch (Exception e) {
					if (e.getClass().getSimpleName().equals("PatientWithIdNotFoundException")) {
						System.out.println("Patient with that id is not found");
					} else {
						e.printStackTrace();
					}
				}
				break;
			default:
				scanner.close();
				System.out.println("Program Terminated");
				return;
			}
		}

	}
	
	private static int stringToInteger(String in) throws InputConstraintNotAsExceptedException {
		try {
			return Integer.parseInt(in);
		} catch (Exception e) {
			throw new InputConstraintNotAsExceptedException("Input must be a number");
		}
	}
}
	
