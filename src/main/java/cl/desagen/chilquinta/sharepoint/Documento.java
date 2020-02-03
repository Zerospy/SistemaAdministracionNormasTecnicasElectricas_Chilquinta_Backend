
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Documento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Documento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nm_archivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm_directorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vl_archivo" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Documento", propOrder = {
    "nmArchivo",
    "nmDirectorio",
    "vlArchivo"
})
public class Documento {

    @XmlElement(name = "nm_archivo")
    protected String nmArchivo;
    @XmlElement(name = "nm_directorio")
    protected String nmDirectorio;
    @XmlElement(name = "vl_archivo")
    protected byte[] vlArchivo;

    /**
     * Gets the value of the nmArchivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNmArchivo() {
        return nmArchivo;
    }

    /**
     * Sets the value of the nmArchivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNmArchivo(String value) {
        this.nmArchivo = value;
    }

    /**
     * Gets the value of the nmDirectorio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNmDirectorio() {
        return nmDirectorio;
    }

    /**
     * Sets the value of the nmDirectorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNmDirectorio(String value) {
        this.nmDirectorio = value;
    }

    /**
     * Gets the value of the vlArchivo property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getVlArchivo() {
        return vlArchivo;
    }

    /**
     * Sets the value of the vlArchivo property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setVlArchivo(byte[] value) {
        this.vlArchivo = ((byte[]) value);
    }

}
