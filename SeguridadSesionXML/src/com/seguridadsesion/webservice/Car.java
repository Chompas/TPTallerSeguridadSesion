package com.seguridadsesion.webservice;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="car-details")
public class Car {
 
  private int carId;
  private String carName, make;
  private double price;
   
  public Car(){}
   
  public Car(int carId, String carName,
    String make, double price) {
    super();
    this.carId = carId;
    this.carName = carName;
    this.make = make;
    this.price = price;
  }
 
  @XmlAttribute(name = "car-id")
  public int getCarId() {return carId;}
  public void setCarId(int carId)
  {this.carId = carId;}
   
  @XmlElement (name="car-name")
  public String getCarName() {return carName;}
  public void setCarName(String carName)
  {this.carName = carName;}
   
  @XmlElement
  public String getMake() {return make;}
  public void setMake(String make)
  {this.make = make;}
   
  @XmlElement
  public double getPrice() {return price;}
  public void setPrice(double price)
  {this.price = price;}
   
  @Override
  public String toString() {
    return String.format("Car Details " +
      "[Name=%s, Make=%s, Price=%f]",
      carName, make, price);
  }
   
}
