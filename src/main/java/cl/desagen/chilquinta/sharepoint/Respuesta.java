
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Respuesta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Respuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cd_error" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="gl_retorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Gets the value of the cdError property.
     * 
     */
    public int getCdError() {
        return cdError;
    }

    /**
     * Sets the value of the cdError property.
     * 
     */
    public void setCdError(int value) {
        this.cdError = value;
    }

    /**
     * Gets the value of the glRetorno property.
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
     * Sets the value of the glRetorno property.
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
