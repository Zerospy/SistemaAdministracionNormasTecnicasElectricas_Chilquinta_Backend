
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DocumentoSCS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DocumentoSCS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nm_archivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vl_archivo" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoSCS", propOrder = {
    "nmArchivo",
    "vlArchivo"
})
public class DocumentoSCS {

    @XmlElement(name = "nm_archivo")
    protected String nmArchivo;
    @XmlElement(name = "vl_archivo")
    protected byte[] vlArchivo;

    /**
     * Obtiene el valor de la propiedad nmArchivo.
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
     * Define el valor de la propiedad nmArchivo.
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
     * Obtiene el valor de la propiedad vlArchivo.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getVlArchivo() {
        return vlArchivo;
    }

    /**
     * Define el valor de la propiedad vlArchivo.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setVlArchivo(byte[] value) {
        this.vlArchivo = value;
    }

}
