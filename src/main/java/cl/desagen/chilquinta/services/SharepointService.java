package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.enums.FileExtension;
import cl.desagen.chilquinta.sharepoint.Documento;
import cl.desagen.chilquinta.sharepoint.Respuesta;
import cl.desagen.chilquinta.sharepoint.WebService1;
import cl.desagen.chilquinta.sharepoint.WebService1Soap;
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

    public String sendDocumentToSharePoint(String codNorma, String fileName, File file, FileExtension fileExtension) {

        try {
            WebService1 sharePointSCS = new WebService1(new URL(sharepointUrl));
            WebService1Soap sharePointSCSSoap = sharePointSCS.getWebService1Soap();

            Documento documentoSCS = new Documento();

            //byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));

            documentoSCS.setNmArchivo(fileName);
            documentoSCS.setNmDirectorio(String.format("Directorio/%s/%s", codNorma, fileExtension.name()));
            documentoSCS.setVlArchivo(FileUtils.readFileToByteArray(file));

            Respuesta respuesta = sharePointSCSSoap.setDocumento(documentoSCS);

            if (respuesta != null && respuesta.getCdError() != 0) {
                log.error("Error: " + respuesta.getCdError());
                throw new Exception("Ocurrió un error al comunicarse con sharepoint ");
            } else {
                log.info("Url Archivo: " + respuesta.getGlRetorno());

                return respuesta.getGlRetorno();
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
        }

        return null;
    }

}
