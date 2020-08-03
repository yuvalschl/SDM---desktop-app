
package jaxb.JaxbClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;all>
 *         &lt;element ref="{}SDM-items"/>
 *         &lt;element ref="{}SDM-stores"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "super-duper-market-descriptor")
public class SuperDuperMarketDescriptor {

    @XmlElement(name = "SDM-items", required = true)
    protected SDMItems sdmItems;
    @XmlElement(name = "SDM-stores", required = true)
    protected SDMStores sdmStores;

    /**
     * Gets the value of the sdmItems property.
     * 
     * @return
     *     possible object is
     *     {@link SDMItems }
     *     
     */
    public SDMItems getSDMItems() {
        return sdmItems;
    }

    /**
     * Sets the value of the sdmItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMItems }
     *     
     */
    public void setSDMItems(SDMItems value) {
        this.sdmItems = value;
    }

    /**
     * Gets the value of the sdmStores property.
     * 
     * @return
     *     possible object is
     *     {@link SDMStores }
     *     
     */
    public SDMStores getSDMStores() {
        return sdmStores;
    }

    /**
     * Sets the value of the sdmStores property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMStores }
     *     
     */
    public void setSDMStores(SDMStores value) {
        this.sdmStores = value;
    }

}
