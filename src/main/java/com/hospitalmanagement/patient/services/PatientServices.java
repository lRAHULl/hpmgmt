/**
 * 
 */
package com.hospitalmanagement.patient.services;

import java.util.List;

import com.hospitalmanagement.patient.exceptions.FileReadException;
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
	public boolean createNewPatient(Patient patient) throws IdAlreadyExistsException, PatientDirectoryFullException, InputConstraintNotAsExceptedException;
	public List<Patient> readAllPatient() throws NoUserExistsException;
	public Patient updateExistingPatient(Patient patient) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException;
	public Patient deletePatient(int id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException;
	public int findNumberOfPatients() throws FileReadException, NoUserExistsException;
}
