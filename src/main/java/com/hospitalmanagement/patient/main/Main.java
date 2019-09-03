/**
 * 
 */
package com.hospitalmanagement.patient.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hospitalmanagement.patient.dao.factory.FileType;
import com.hospitalmanagement.patient.driver.PatientDriver;
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
	
	public static FileType getFactoryType;
	
	public static void main(String[] args) throws NoUserExistsException {
		PatientDriver.initHospitalManagement();
	}

}
