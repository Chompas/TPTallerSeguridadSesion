package com.seguridadsesion.webservice;

import java.util.HashMap;
import java.util.Map;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/xml/car")
public class CarWebService {
 
  private Map<String, Car> cars;
   
  public CarWebService(){
    cars = new HashMap<String, Car>();
    cars.put("nano", new Car(1,"Nano","TATA",165000));
    cars.put("tavera", new Car(2, "Tavera", "Chervolet", 865000));
    cars.put("scorpio", new Car(2, "Scorpio", "Mahindra", 870000));
  }
   
  @GET
  @Path("/{name}")
  @Produces (MediaType.APPLICATION_XML)
  public Car getCarDetailsByName(@PathParam("name") String name) {
    if(cars.containsKey(name)) return cars.get(name);
    else throw new CarNotFoundException(
      "Car with name = "+name+" not found.");
  }
   
  @GET
  @Path("/{name}/{make}")
  @Produces (MediaType.APPLICATION_XML)
  public Car getCarDetailsByNameAndMake(@PathParam("name") String name,
      @PathParam("make") String make)
  {
    if(cars.containsKey(name)){
      Car car = cars.get(name);
      if(car.getMake().equals(make)) {
        return car;
      }
      else throw new CarNotFoundException(
          "Car with name = "+name+" doesn't belong to "+make);
    }
    else throw new CarNotFoundException(
      "Car with name = "+name+" not found.");
  }
   
}
