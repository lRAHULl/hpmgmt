/**
 * 
 */
package com.rahul.hpmgmt.services;

import java.util.List;
import java.util.Map;

import com.rahul.hpmgmt.exceptions.IdAlreadyExistsException;
import com.rahul.hpmgmt.exceptions.NoUserExistsException;
import com.rahul.hpmgmt.exceptions.PatientDirectoryFullException;
import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;

/**
 * @author rahul
 *
 */
public interface PatientServices {
	public boolean createANewPatient(int id, String name, int age, List<String> address) throws IdAlreadyExistsException, PatientDirectoryFullException;
	public Map<Integer, Patient> readAllPatient() throws NoUserExistsException;
	public Patient updateAnExistingPatient(int id, String name, int age, List<String> address) throws PatientWithIdNotFoundException;
	public Patient deleteAPatient(int id) throws PatientWithIdNotFoundException;
	public int findNumberOfPatients();
}
