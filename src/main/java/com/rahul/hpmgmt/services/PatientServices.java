/**
 * 
 */
package com.rahul.hpmgmt.services;

import com.rahul.hpmgmt.exceptions.IdAlreadyExistsException;
import com.rahul.hpmgmt.exceptions.PatientDirectoryFullException;
import com.rahul.hpmgmt.exceptions.PatientWithIdNotFoundException;
import com.rahul.hpmgmt.model.Patient;

/**
 * @author rahul
 *
 */
public interface PatientServices {
	public boolean createANewPatient(int id, String name, int age) throws IdAlreadyExistsException, PatientDirectoryFullException;
	public Patient[] readAllPatient();
	public Patient updateAnExistingPatient(int id, String name, int age) throws PatientWithIdNotFoundException;
	public Patient deleteAPatient(int id) throws PatientWithIdNotFoundException;
	public int findNumberOfPatients();
}
