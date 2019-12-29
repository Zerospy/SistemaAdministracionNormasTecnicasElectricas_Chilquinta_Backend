
package cl.desagen.chilquinta.sharepoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="objDocumento" type="{http://tempuri.org/}DocumentoSCS" minOccurs="0"/&gt;
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
    "objDocumento"
})
@XmlRootElement(name = "SetDocumento")
public class SetDocumento {

    protected DocumentoSCS objDocumento;

    /**
     * Obtiene el valor de la propiedad objDocumento.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoSCS }
     *     
     */
    public DocumentoSCS getObjDocumento() {
        return objDocumento;
    }

    /**
     * Define el valor de la propiedad objDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoSCS }
     *     
     */
    public void setObjDocumento(DocumentoSCS value) {
        this.objDocumento = value;
    }

}
