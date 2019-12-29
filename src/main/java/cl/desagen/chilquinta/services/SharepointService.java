package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.sharepoint.DocumentoSCS;
import cl.desagen.chilquinta.sharepoint.SharePointSCS;
import cl.desagen.chilquinta.sharepoint.SharePointSCSSoap;
import org.springframework.stereotype.Service;

@Service
public class SharepointService {

    public void sendDocumentToSharePoint() {

        SharePointSCS sharePointSCS = new SharePointSCS();
        SharePointSCSSoap sharePointSCSSoap = sharePointSCS.getSharePointSCSSoap();

        DocumentoSCS documentoSCS = new DocumentoSCS();

        documentoSCS.setNmArchivo("test");
        documentoSCS.setVlArchivo("".getBytes());

        sharePointSCSSoap.setDocumento(documentoSCS);

    }

}
