//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.01 at 07:31:23 PM EDT 
//


package com.adoulfakkar.quizzApp.service.wsdl.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.adoulfakkar.quizzApp.service.wsdl.user package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Level4_QNAME = new QName("", "level4");
    private final static QName _Role_QNAME = new QName("", "role");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _Level1_QNAME = new QName("", "level1");
    private final static QName _Url_QNAME = new QName("", "url");
    private final static QName _Sso_QNAME = new QName("", "sso");
    private final static QName _Level3_QNAME = new QName("", "level3");
    private final static QName _Level2_QNAME = new QName("", "level2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.adoulfakkar.quizzApp.service.wsdl.user
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Attribut }
     * 
     */
    public Attribut createAttribut() {
        return new Attribut();
    }

    /**
     * Create an instance of {@link App }
     * 
     */
    public App createApp() {
        return new App();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link Grp }
     * 
     */
    public Grp createGrp() {
        return new Grp();
    }

    /**
     * Create an instance of {@link Attr }
     * 
     */
    public Attr createAttr() {
        return new Attr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "level4")
    public JAXBElement<String> createLevel4(String value) {
        return new JAXBElement<String>(_Level4_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "role")
    public JAXBElement<String> createRole(String value) {
        return new JAXBElement<String>(_Role_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "level1")
    public JAXBElement<String> createLevel1(String value) {
        return new JAXBElement<String>(_Level1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sso")
    public JAXBElement<String> createSso(String value) {
        return new JAXBElement<String>(_Sso_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "level3")
    public JAXBElement<String> createLevel3(String value) {
        return new JAXBElement<String>(_Level3_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "level2")
    public JAXBElement<String> createLevel2(String value) {
        return new JAXBElement<String>(_Level2_QNAME, String.class, null, value);
    }

}
