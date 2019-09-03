/**
 * 
 */
package com.hospitalmanagement.patient.dao.factory;

import com.hospitalmanagement.patient.dao.PatientDAO;
import com.hospitalmanagement.patient.dao.impl.PatientCSVDAOImpl;
import com.hospitalmanagement.patient.dao.impl.PatientJSONDAOImpl;

/**
 * @author VC
 *
 */
public class PatientDAOFactory {
	public PatientDAO patientDAO = null;
	
	public PatientDAO getPatientDataSource(FileType fileType) {
		switch (fileType) {
		case CSVFILE:
			patientDAO = new PatientCSVDAOImpl();
			break;
		case JSONFILE:
			patientDAO = new PatientJSONDAOImpl();
			break;
		}
		return patientDAO;
	}
	
}
