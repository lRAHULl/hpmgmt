/**
 * 
 */
package com.hospitalmanagement.patient.constants;

import java.util.Arrays;

import com.hospitalmanagement.patient.model.Patient;

/**
 * @author rahul
 *
 */
public class PatientTestConstants {
	public static String PATIENT_WITH_ID_NOT_FOUND_EXCEPTION = "PatientWithIdNotFoundException";
	public static String ID_ALREADY_EXISTS_EXCEPTION = "IdAlreadyExistsException";
	public static String PATIENT_DIRECTORY_FULL_EXCEPTION = "PatientDirectoryFullException";
	public static String NO_USER_EXISTS_EXCEPTION = "NoUserExistsException";
	public static String INPUT_CONSTRAINT_NOT_AS_EXPECTED_EXCEPTION = "InputConstraintNotAsExceptedException";
	public static Patient PATIENT_ONE = new Patient(1, "AAAA", 19, Arrays.asList("Coda Global", "Chennai", "India"));
	public static Patient PATIENT_TWO = new Patient(2, "BBBB", 20, Arrays.asList("Google", "Mountain view", "USA"));
	public static Patient PATIENT_THREE = new Patient(3, "CCCC", 21, Arrays.asList("Microsoft", "Seattle", "USA"));
	public static Patient PATIENT_FOUR = new Patient(4, "AAAA", 19, Arrays.asList("Coda Global", "Chennai", "India"));
	public static Patient PATIENT_FIVE = new Patient(5, "BBBB", 20, Arrays.asList("Google", "Mountain view", "USA"));
	public static Patient PATIENT_SIX = new Patient(6, "CCCC", 21, Arrays.asList("Microsoft", "Seattle", "USA"));
	public static Patient UPDATED_PATIENT_ONE = new Patient(1, "BBBB", 20, Arrays.asList("Google", "Mountain view", "USA"));
//
	public static String MESSAGES_SOURCE = "messages-test";
	
	public static String HPMT0000T = "HPMT0000T";
	public static String HPMT0001T = "HPMT0001T";
	public static String HPMT0002T = "HPMT0002T";
	public static String HPMT0003T = "HPMT0003T";
	public static String HPMT0004T = "HPMT0004T";
	public static String HPMT0005T = "HPMT0005T";
	public static String HPMT0006T = "HPMT0006T";
	public static String HPMT0007T = "HPMT0007T";
	
	public static String HPMT0000D = "HPMT0000D";
	
	public static String HPMT0000E = "HPMT0000E";
}
