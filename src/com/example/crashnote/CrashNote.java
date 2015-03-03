package com.example.crashnote;

import android.net.Uri;

public class CrashNote {

	// declaration of variables
	double longitude, latitude;
	String address, contact, policeIncNo, accidentNo, licenceNo, date, fName, regNo, carMake, carModel, insuranceCo;
	String photopath1, photopath2, photopath3, photopath4;

	// 12 variables to form our object CrashNote
	// Empty constructor
	public CrashNote() {
	}

	public CrashNote(String _date, Double _longitude, Double _latitude,
			String _fName, String _licenceNo, String _address, String _contact,
			String _regNo, String _carMake, String _carModel,
			String _insuranceCo, String _policeIncNo, String _accidentNo, String strnphoto1,String 
			strnphoto2,String strnphoto3,String strnphoto4) {
		this.date = _date;
		this.longitude = _longitude;
		this.latitude = _latitude;
		this.licenceNo = _licenceNo;
		this.fName = _fName;
		this.address = _address;
		this.contact = _contact;
		this.regNo = _regNo;
		this.carMake = _carMake;
		this.carModel = _carModel;
		this.insuranceCo = _insuranceCo;
		this.policeIncNo = _policeIncNo;
		this.photopath1 = strnphoto1;
		this.photopath2 = strnphoto2;
		this.photopath3 = strnphoto3;
		this.photopath4 = strnphoto4;
		this.accidentNo = _accidentNo;
	
	}
	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the policeIncNo
	 */
	public String getPoliceIncNo() {
		return policeIncNo;
	}

	/**
	 * @param policeIncNo the policeIncNo to set
	 */
	public void setPoliceIncNo(String policeIncNo) {
		this.policeIncNo = policeIncNo;
	}

	/**
	 * @return the accidentNo
	 */
	public String getAccidentNo() {
		return accidentNo;
	}

	/**
	 * @param accidentNo the accidentNo to set
	 */
	public void setAccidentNo(String accidentNo) {
		this.accidentNo = accidentNo;
	}

	/**
	 * @return the licenceNo
	 */
	public String getLicenceNo() {
		return licenceNo;
	}

	/**
	 * @param licenceNo the licenceNo to set
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @param regNo the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the carMake
	 */
	public String getCarMake() {
		return carMake;
	}

	/**
	 * @param carMake the carMake to set
	 */
	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	/**
	 * @return the carModel
	 */
	public String getCarModel() {
		return carModel;
	}

	/**
	 * @param carModel the carModel to set
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * @return the insuranceCo
	 */
	public String getInsuranceCo() {
		return insuranceCo;
	}

	/**
	 * @param insuranceCo the insuranceCo to set
	 */
	public void setInsuranceCo(String insuranceCo) {
		this.insuranceCo = insuranceCo;
	}

	/**
	 * @return the photopath1
	 */
	public String getPhotopath1() {
		return photopath1;
	}

	/**
	 * @param photopath1 the photopath1 to set
	 */
	public void setPhotopath1(String photopath1) {
		this.photopath1 = photopath1;
	}

	/**
	 * @return the photopath2
	 */
	public String getPhotopath2() {
		return photopath2;
	}

	/**
	 * @param photopath2 the photopath2 to set
	 */
	public void setPhotopath2(String photopath2) {
		this.photopath2 = photopath2;
	}

	/**
	 * @return the photopath3
	 */
	public String getPhotopath3() {
		return photopath3;
	}

	/**
	 * @param photopath3 the photopath3 to set
	 */
	public void setPhotopath3(String photopath3) {
		this.photopath3 = photopath3;
	}

	/**
	 * @return the photopath4
	 */
	public String getPhotopath4() {
		return photopath4;
	}

	/**
	 * @param photopath4 the photopath4 to set
	 */
	public void setPhotopath4(String photopath4) {
		this.photopath4 = photopath4;
	}

	

}
