
package com.seguridadsesion.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ApiServiceHelper", targetNamespace = "http://webservice.seguridadsesion.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ApiServiceHelper {


    /**
     * 
     * @param username
     * @param password
     * @return
     *     returns com.seguridadsesion.webservice.Session
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://webservice.seguridadsesion.com/", className = "com.seguridadsesion.webservice.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://webservice.seguridadsesion.com/", className = "com.seguridadsesion.webservice.LoginResponse")
    public Session login(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param apellido
     * @param nombre
     * @param username
     * @param email
     * @param fechaNac
     * @param padron
     * @param rol
     * @param password
     * @return
     *     returns com.seguridadsesion.webservice.Session
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registerUser", targetNamespace = "http://webservice.seguridadsesion.com/", className = "com.seguridadsesion.webservice.RegisterUser")
    @ResponseWrapper(localName = "registerUserResponse", targetNamespace = "http://webservice.seguridadsesion.com/", className = "com.seguridadsesion.webservice.RegisterUserResponse")
    public Session registerUser(
        @WebParam(name = "username", targetNamespace = "")
        String username,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "nombre", targetNamespace = "")
        String nombre,
        @WebParam(name = "apellido", targetNamespace = "")
        String apellido,
        @WebParam(name = "padron", targetNamespace = "")
        String padron,
        @WebParam(name = "fechaNac", targetNamespace = "")
        String fechaNac,
        @WebParam(name = "email", targetNamespace = "")
        String email,
        @WebParam(name = "rol", targetNamespace = "")
        int rol);

}
