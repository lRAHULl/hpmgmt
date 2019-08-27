/**
 * 
 */
package com.hospitalmanagement.patient.services;

import java.util.List;
import java.util.Map;

import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.InputConstraintNotAsExceptedException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientDirectoryFullException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;

/**
 * @author rahul
 *
 */
public interface PatientServices {
	public boolean createNewPatient(String id, String name, String age, List<String> address) throws IdAlreadyExistsException, PatientDirectoryFullException, InputConstraintNotAsExceptedException;
	public Map<Integer, Patient> readAllPatient() throws NoUserExistsException;
	public Patient updateExistingPatient(String id, String name, String age, List<String> address) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException;
	public Patient deletePatient(String id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException;
	public int findNumberOfPatients();
}
