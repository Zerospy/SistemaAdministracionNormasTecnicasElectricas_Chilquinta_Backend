
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Respuesta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Respuesta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cd_error" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="gl_retorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Respuesta", propOrder = {
    "cdError",
    "glRetorno"
})
public class Respuesta {

    @XmlElement(name = "cd_error")
    protected int cdError;
    @XmlElement(name = "gl_retorno")
    protected String glRetorno;

    /**
     * Obtiene el valor de la propiedad cdError.
     * 
     */
    public int getCdError() {
        return cdError;
    }

    /**
     * Define el valor de la propiedad cdError.
     * 
     */
    public void setCdError(int value) {
        this.cdError = value;
    }

    /**
     * Obtiene el valor de la propiedad glRetorno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlRetorno() {
        return glRetorno;
    }

    /**
     * Define el valor de la propiedad glRetorno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlRetorno(String value) {
        this.glRetorno = value;
    }

}
