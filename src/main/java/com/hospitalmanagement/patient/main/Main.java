/**
 * 
 */
package com.hospitalmanagement.patient.main;

import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.main.driver.PatientDriver;

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
		PatientDriver.initHospitalManagement();
	}

}