/**
 * 
 */
package com.hospitalmanagement.patient.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.model.Patient;
import com.hospitalmanagement.patient.services.impl.PatientServicesImpl;

/**
 * @author rahul
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws NoUserExistsException 
	 * @throws  
	 */
	public static void main(String[] args) throws NoUserExistsException {
		Scanner scanner = new Scanner(System.in);
		PatientServicesImpl patientServices;
		int choice;
		String patientId, patientAge;
		String patientName;
		while(true) {
			System.out.println("1. Create a patient");
			System.out.println("2. Read All Patients");
			System.out.println("3. Update an existing Patient");
			System.out.println("4. Delete a patient");
			System.out.println("5. Exit");
			System.out.print("Enter a number to do a operation: ");
			choice = Integer.valueOf(scanner.nextLine());
			switch(choice) {
			case 1:
				patientServices = new PatientServicesImpl();
				System.out.print("Enter the Id: ");
				patientId = scanner.nextLine();
				System.out.print("Enter the Name: ");
				patientName = scanner.nextLine();
				System.out.print("Enter the age: ");
				patientAge = scanner.nextLine();
				List<String> patientAddress = new ArrayList<String>();
				for (int i = 0; i < 3; i++) {
					int index = i + 1;
					System.out.print("Enter the address line " + index + ": ");
					String addr = scanner.nextLine();
					if (!addr.equals("")) {
						patientAddress.add(addr);
					}					
				}
				try {
					patientServices.createNewPatient(patientId, patientName, patientAge, patientAddress);
				} catch (PatientDirectoryFullException | IdAlreadyExistsException | InputConstraintNotAsExceptedException e) {
					if (e.getClass().getSimpleName().equals("PatientDirectoryFullException")) {
						System.out.println("The directory is full :(");
					} else if (e.getClass().getSimpleName().equals("IdAlreadyExistsException")) {
						System.out.println("The id already exists");
					} else if (e.getClass().getSimpleName().equals("InputConstraintNotAsExceptedException")) {
						System.out.println("Inputs not as expected");
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
				System.out.print("Enter the Id: ");
				patientId = scanner.nextLine();
				System.out.print("Enter the new Name: ");
				patientName = scanner.nextLine();
				System.out.print("Enter the new age: ");
				patientAge = scanner.nextLine();
				List<String> patientUpdateAddress = new ArrayList<String>();
				for (int i = 0; i < 3; i++) {
					int index = i + 1;
					System.out.print("Enter the new address line " + index + ": ");
					
					String addr = scanner.nextLine();
					if (!addr.equals(""))
						patientUpdateAddress.add(addr);	
					else 
						patientUpdateAddress.add(patientServices.readAllPatient().get(Integer.parseInt(patientId)).getPatientAddress().get(i));
				}
				try {
					Patient updatedPatient = patientServices.updateExistingPatient(patientId, patientName, patientAge, patientUpdateAddress);
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
				patientId = scanner.nextLine();
				try {
					Patient deletedPatient = patientServices.deletePatient(patientId);
					System.out.println("DELETED PATIENT\n-------------------- \n Id: " + deletedPatient.getPatientId() + " Name: " + deletedPatient.getPatientName() + " Age: " + deletedPatient.getPatientAge());
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

}
