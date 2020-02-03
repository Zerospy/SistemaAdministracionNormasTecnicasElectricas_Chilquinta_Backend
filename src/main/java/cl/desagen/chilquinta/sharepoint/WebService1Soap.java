package cl.desagen.chilquinta.sharepoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2020-02-03T14:53:47.104-03:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "WebService1Soap")
@XmlSeeAlso({ObjectFactory.class})
public interface WebService1Soap {

    @WebMethod(operationName = "SetDocumento", action = "http://tempuri.org/SetDocumento")
    @RequestWrapper(localName = "SetDocumento", targetNamespace = "http://tempuri.org/", className = "cl.desagen.chilquinta.sharepoint.SetDocumento")
    @ResponseWrapper(localName = "SetDocumentoResponse", targetNamespace = "http://tempuri.org/", className = "cl.desagen.chilquinta.sharepoint.SetDocumentoResponse")
    @WebResult(name = "SetDocumentoResult", targetNamespace = "http://tempuri.org/")
    public cl.desagen.chilquinta.sharepoint.Respuesta setDocumento(
        @WebParam(name = "objDocumento", targetNamespace = "http://tempuri.org/")
        cl.desagen.chilquinta.sharepoint.Documento objDocumento
    );
}
