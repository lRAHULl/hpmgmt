/**
 * 
 */
package com.hospitalmanagement.patient.services;

import java.io.IOException;
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
	public boolean createNewPatient(Patient patient) throws IdAlreadyExistsException, PatientDirectoryFullException, InputConstraintNotAsExceptedException, IOException;
	public List<Patient> readAllPatient() throws NoUserExistsException, IOException, FileReadException;
	public Patient updateExistingPatient(Patient patient) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException, IOException, NoUserExistsException, FileReadException;
	public Patient deletePatient(int id) throws PatientWithIdNotFoundException, InputConstraintNotAsExceptedException, IOException, NoUserExistsException, FileReadException;
	public int findNumberOfPatients() throws FileReadException, NoUserExistsException, IOException;
}
