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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.desagen.chilquinta.commons.Constants;
import cl.desagen.chilquinta.entities.UsuarioEntity;
import cl.desagen.chilquinta.entities.UsuarioSaveRequest;
import cl.desagen.chilquinta.services.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<UsuarioEntity> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Optional<UsuarioEntity> findById(@PathVariable Integer id) {
        return usuarioService.findById(id);
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity save(@RequestBody UsuarioSaveRequest usuarioEntity) {

        try {
            UsuarioEntity usuarioResult = usuarioService.save(usuarioEntity);
            return new ResponseEntity(usuarioResult, HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@RequestBody UsuarioEntity usuarioEntity) {

        try {
            usuarioService.delete(usuarioEntity);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable Integer id) {

        try {
            usuarioService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("uploadAvatar/{userId}")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer userId) {

        try {

            usuarioService.saveAvatar(userId, file);
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
            }
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

    }

}
