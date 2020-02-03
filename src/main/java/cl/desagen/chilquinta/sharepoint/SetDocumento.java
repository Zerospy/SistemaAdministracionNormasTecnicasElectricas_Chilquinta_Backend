
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objDocumento" type="{http://tempuri.org/}Documento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "objDocumento"
})
@XmlRootElement(name = "SetDocumento")
public class SetDocumento {

    protected Documento objDocumento;

    /**
     * Gets the value of the objDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link Documento }
     *     
     */
    public Documento getObjDocumento() {
        return objDocumento;
    }

    /**
     * Sets the value of the objDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Documento }
     *     
     */
    public void setObjDocumento(Documento value) {
        this.objDocumento = value;
    }

}
