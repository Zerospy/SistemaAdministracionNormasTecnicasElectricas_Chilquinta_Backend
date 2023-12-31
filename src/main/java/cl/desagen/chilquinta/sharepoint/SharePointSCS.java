package cl.desagen.chilquinta.sharepoint;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2019-12-29T19:03:51.696-03:00
 * Generated source version: 3.2.4
 *
 */
@WebServiceClient(name = "SharePointSCS",
                  wsdlLocation = "C:/SharePointSCS.wsdl",
                  targetNamespace = "http://tempuri.org/")
public class SharePointSCS extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "SharePointSCS");
    public final static QName SharePointSCSSoap = new QName("http://tempuri.org/", "SharePointSCSSoap");
    public final static QName SharePointSCSSoap12 = new QName("http://tempuri.org/", "SharePointSCSSoap12");
    static {
        URL url = SharePointSCS.class.getResource("C:/SharePointSCS.wsdl");
        if (url == null) {
            url = SharePointSCS.class.getClassLoader().getResource("C:/SharePointSCS.wsdl");
        }
        if (url == null) {
            java.util.logging.Logger.getLogger(SharePointSCS.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "C:/SharePointSCS.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SharePointSCS(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SharePointSCS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SharePointSCS() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SharePointSCS(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SharePointSCS(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SharePointSCS(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns SharePointSCSSoap
     */
    @WebEndpoint(name = "SharePointSCSSoap")
    public SharePointSCSSoap getSharePointSCSSoap() {
        return super.getPort(SharePointSCSSoap, SharePointSCSSoap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SharePointSCSSoap
     */
    @WebEndpoint(name = "SharePointSCSSoap")
    public SharePointSCSSoap getSharePointSCSSoap(WebServiceFeature... features) {
        return super.getPort(SharePointSCSSoap, SharePointSCSSoap.class, features);
    }


    /**
     *
     * @return
     *     returns SharePointSCSSoap
     */
    @WebEndpoint(name = "SharePointSCSSoap12")
    public SharePointSCSSoap getSharePointSCSSoap12() {
        return super.getPort(SharePointSCSSoap12, SharePointSCSSoap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SharePointSCSSoap
     */
    @WebEndpoint(name = "SharePointSCSSoap12")
    public SharePointSCSSoap getSharePointSCSSoap12(WebServiceFeature... features) {
        return super.getPort(SharePointSCSSoap12, SharePointSCSSoap.class, features);
    }

}
