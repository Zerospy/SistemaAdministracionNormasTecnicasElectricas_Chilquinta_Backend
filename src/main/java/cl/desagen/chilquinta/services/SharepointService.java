package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.sharepoint.DocumentoSCS;
import cl.desagen.chilquinta.sharepoint.SharePointSCS;
import cl.desagen.chilquinta.sharepoint.SharePointSCSSoap;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
public class SharepointService {

    private static final Logger log = LoggerFactory.getLogger(SharepointService.class);

    @Value("${sharepoint.url}")
    private String sharepointUrl;

    public void sendDocumentToSharePoint(String fileName, File file) {

        try {
            SharePointSCS sharePointSCS = new SharePointSCS(new URL(sharepointUrl));
            SharePointSCSSoap sharePointSCSSoap = sharePointSCS.getSharePointSCSSoap();

            DocumentoSCS documentoSCS = new DocumentoSCS();

            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));

            documentoSCS.setNmArchivo(fileName);
            documentoSCS.setVlArchivo(encoded);

            sharePointSCSSoap.setDocumento(documentoSCS);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
        }

    }

}
