package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Meal implements Item{
   private String mealID;
   private String description;
   private String portion;
   private double unitPrice;
   private boolean isAvailable;
   private File mealImage;

   public Meal() {
   }

   public Meal(String mealID, String description, String portion) {
      this.mealID = mealID;
      this.description = description;
      this.portion = portion;
   }

   public Meal(String mealID, String description, String portion, double unitPrice, boolean isAvailable, File mealImage) {

      this.mealID = mealID;
      this.description = description;
      this.portion = portion;
      this.unitPrice = unitPrice;
      this.isAvailable = isAvailable;
      this.mealImage = mealImage;
   }



   public String getMealID() {
      return mealID;
   }

   public void setMealID(String mealID) {
      this.mealID = mealID;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getPortion() {
      return portion;
   }

   public void setPortion(String portion) {
      this.portion = portion;
   }

   public double getUnitPrice() {
      return unitPrice;
   }

   public void setUnitPrice(double unitPrice) {
      this.unitPrice = unitPrice;
   }

   public boolean isAvailable() {
      return isAvailable;
   }

   public void setAvailable(boolean available) {
      isAvailable = available;
   }

   public File getMealImage() {
      return mealImage;
   }

   public void setMealImage(File mealImage) {
      this.mealImage = mealImage;
   }

   @Override
   public String toString() {
      return  mealID+" - "+description+" - "+portion;
   }

   @Override
   public String getID() {
     return mealID;
   }
}
