/**
 * 
 */
package com.rahul.hpmgmt.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rahul.hpmgmt.exceptions.IdAlreadyExistsException;
import com.rahul.hpmgmt.exceptions.NoUserExistsException;
import com.rahul.hpmgmt.exceptions.PatientDirectoryFullException;
import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;
import com.rahul.hpmgmt.services.impl.PatientServicesImpl;

/**
 * @author rahul
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws NoUserExistsException 
	 */
	public static void main(String[] args) throws NoUserExistsException {
		Scanner scanner = new Scanner(System.in);
		PatientServicesImpl patientServices;
		int choice;
		int patientId, patientAge;
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
				patientId = Integer.valueOf(scanner.nextLine());
				System.out.print("Enter the Name: ");
				patientName = scanner.nextLine();
				System.out.print("Enter the age: ");
				patientAge = Integer.valueOf(scanner.nextLine());
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
					patientServices.createANewPatient(patientId, patientName, patientAge, patientAddress);
				} catch (PatientDirectoryFullException | IdAlreadyExistsException e) {
					if (e.getClass().getSimpleName().equals("PatientDirectoryFullException")) {
						System.out.println("The directory is full :(");
					} else if (e.getClass().getSimpleName().equals("IdAlreadyExistsException")) {
						System.out.println("The id already exists");
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
				patientId = Integer.valueOf(scanner.nextLine());
				System.out.print("Enter the new Name: ");
				patientName = scanner.nextLine();
				System.out.print("Enter the new age: ");
				patientAge = Integer.valueOf(scanner.nextLine());
				List<String> patientUpdateAddress = new ArrayList<String>();
				for (int i = 0; i < 3; i++) {
					int index = i + 1;
					System.out.print("Enter the new address line " + index + ": ");
					
					String addr = scanner.nextLine();
					if (!addr.equals(""))
						patientUpdateAddress.add(addr);	
					else 
						patientUpdateAddress.add(patientServices.readAllPatient().get(patientId).getPatientAddress().get(i));
				}
				try {
					Patient updatedPatient = patientServices.updateAnExistingPatient(patientId, patientName, patientAge, patientUpdateAddress);
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
				patientId = Integer.valueOf(scanner.nextLine());
				try {
					Patient deletedPatient = patientServices.deleteAPatient(patientId);
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
