
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SetDocumentoResult" type="{http://tempuri.org/}Respuesta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "setDocumentoResult"
})
@XmlRootElement(name = "SetDocumentoResponse")
public class SetDocumentoResponse {

    @XmlElement(name = "SetDocumentoResult")
    protected Respuesta setDocumentoResult;

    /**
     * Obtiene el valor de la propiedad setDocumentoResult.
     * 
     * @return
     *     possible object is
     *     {@link Respuesta }
     *     
     */
    public Respuesta getSetDocumentoResult() {
        return setDocumentoResult;
    }

    /**
     * Define el valor de la propiedad setDocumentoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Respuesta }
     *     
     */
    public void setSetDocumentoResult(Respuesta value) {
        this.setDocumentoResult = value;
    }

}
