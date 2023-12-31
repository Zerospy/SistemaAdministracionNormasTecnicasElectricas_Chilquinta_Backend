package cl.desagen.chilquinta.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.desagen.chilquinta.commons.Constants;
import cl.desagen.chilquinta.entities.DetallePerfilEntity;
import cl.desagen.chilquinta.services.DetallePerfilService;

@RestController
@RequestMapping("detalleperfil")
public class DetallePerfilController {

    private static final Logger log = LoggerFactory.getLogger(DetallePerfilController.class);

    @Autowired
    private DetallePerfilService detalleperfilService;

    @GetMapping(value = "/", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<DetallePerfilEntity> findAll() {
        return detalleperfilService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Optional<DetallePerfilEntity> findById(@PathVariable Long id) {
        return detalleperfilService.findById(id);
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity save(@RequestBody DetallePerfilEntity detalleperfilEntity) {

        try {
            DetallePerfilEntity detalleperfilResult = detalleperfilService.save(detalleperfilEntity);
            return new ResponseEntity(detalleperfilResult, HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody DetallePerfilEntity detalleperfilEntity) {

        try {
            detalleperfilService.delete(detalleperfilEntity);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable Long id) {

        try {
            detalleperfilService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
    
    @GetMapping(value = "/getByIdUsuario/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Optional<DetallePerfilEntity> findByUsuarioId(@PathVariable Integer id) {
        return detalleperfilService.findByUsuarioId(id);
    }
    

}
