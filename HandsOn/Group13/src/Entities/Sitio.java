package Entities;

public class Sitio {

	public String type;

	public String name;

	public String address;

	public boolean accesibility;

	public Double latitude;

	public Double longitude;

	public String postalCode;

	public String telephone;

	public String email;

	public double distance;

	public Sitio(String type, String name, String address, boolean accesibility,
			double latitude, double longitude, String postalCode,
			String telephone, double distance) {
		this.name = name;
		this.address = address;
		this.accesibility = accesibility;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postalCode = postalCode;
		this.telephone = telephone;
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAccesibility() {
		return accesibility;
	}

	public void setAccesibility(boolean accesibility) {
		this.accesibility = accesibility;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}