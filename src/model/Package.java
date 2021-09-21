package model;

import java.util.ArrayList;

public class Package {
    private String packageID;
    private String name;
    private double price;
    private ArrayList<PackageDetail> packageDetails;

    public Package() {
    }

    public Package(String packageID, String name, double price, ArrayList<PackageDetail> packageDetails) {
        this.packageID = packageID;
        this.name = name;
        this.price = price;
        this.packageDetails = packageDetails;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<PackageDetail> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(ArrayList<PackageDetail> packageDetails) {
        this.packageDetails = packageDetails;
    }
}
