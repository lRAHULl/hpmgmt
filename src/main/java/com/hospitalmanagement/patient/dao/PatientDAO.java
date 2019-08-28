/**
 * 
 */
package com.hospitalmanagement.patient.dao;

import java.io.IOException;
import java.util.List;

import com.hospitalmanagement.patient.exceptions.IdAlreadyExistsException;
import com.hospitalmanagement.patient.exceptions.NoUserExistsException;
import com.hospitalmanagement.patient.exceptions.PatientWithIdNotFoundException;
import com.hospitalmanagement.patient.model.Patient;

/**
 * @author VC
 *
 */
public interface PatientDAO {
	public boolean createPatient(Patient patient) throws IOException, IdAlreadyExistsException;
	public List<Patient> readPatients() throws IOException, NoUserExistsException;
	public Patient updatePatient(int id, Patient patient) throws IOException, PatientWithIdNotFoundException, NoUserExistsException;
	public Patient deletePatient(int id) throws IOException, PatientWithIdNotFoundException, NoUserExistsException;
}
