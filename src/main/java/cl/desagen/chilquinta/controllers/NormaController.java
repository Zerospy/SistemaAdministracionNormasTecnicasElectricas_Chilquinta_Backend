package cl.desagen.chilquinta.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cl.desagen.chilquinta.dto.NormaDto;
import cl.desagen.chilquinta.entities.NormaEntity;
import cl.desagen.chilquinta.entities.ResponseDownload;
import cl.desagen.chilquinta.enums.EstadoNorma;
import cl.desagen.chilquinta.enums.TipoNorma;
import cl.desagen.chilquinta.repositories.NormaRepository;
import cl.desagen.chilquinta.security.JwtTokenUtil;
import cl.desagen.chilquinta.services.NormaService;

@RestController
@RequestMapping("norma")
public class NormaController {

	private static final Logger log = LoggerFactory.getLogger(NormaController.class);

	@Autowired
	private NormaService normaService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private NormaRepository normaRepository;

	@GetMapping(value = "/", produces = APPLICATION_JSON_UTF8_VALUE)
	public Iterable<NormaEntity> findAll(HttpServletRequest httpServletRequest) throws Exception {
		String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);
		return normaService.findAllAssigned(username);
	}

	@GetMapping(value = "/internacional/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Iterable<NormaEntity> findAllInternacional() {
		return normaService.findAllIntenational();
	}

	@GetMapping(value = "/documentos/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Iterable<NormaEntity> findAllDocumentos() {
		return normaService.findAllDocumentos();
	}

	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public NormaDto findById(@PathVariable Integer id) {
		return normaService.findNormaDtoById(id);
	}

	@GetMapping(value = "findByStatus/{estadoNorma}", produces = APPLICATION_JSON_UTF8_VALUE)
	public List<NormaEntity> findByStatus(@PathVariable EstadoNorma estadoNorma) {
		return normaService.findByStatus(estadoNorma);
	}

	@PostMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity save(@RequestBody NormaDto normaEntity, HttpServletRequest httpServletRequest) {

		try {
			String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);
			NormaEntity normaEntityResult = normaService.save(normaEntity.toEntity(), username);
			return new ResponseEntity(normaEntityResult, HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/norma-internacional", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity save(@RequestBody List<NormaEntity> normaEntities) {

		try {
			List<NormaEntity> normaEntitiesResult = new ArrayList<>();

			if (normaEntities != null && !normaEntities.isEmpty()) {
				normaEntities.forEach(norma -> {
					NormaEntity normaEntityResult = normaService.saveInternacional(norma);
					normaEntitiesResult.add(normaEntityResult);
				});
			}
			return new ResponseEntity(normaEntitiesResult, HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping(value = "/", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity delete(@RequestBody NormaEntity normaEntity) {

		try {
			normaService.delete(normaEntity);
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
			normaService.deleteById(id);
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/publish/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity publishNorma(HttpServletRequest httpServletRequest, @PathVariable Integer id) {

		try {
			String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);

			normaService.publishNorma(id, username);
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/modificarCampos/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity modificarCampos(HttpServletRequest httpServletRequest, @PathVariable Integer id,
			@RequestBody NormaDto normaEntity) {

		try {
			String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);

			NormaEntity normaModificada = normaService.modificarCampos(id, normaEntity.toEntity(), username);

			return new ResponseEntity(normaModificada, HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/dardebaja/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity dardeBajaNorma(HttpServletRequest httpServletRequest, @PathVariable Integer id) {

		try {
			String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);

			normaService.dardeBajaNorma(id, username);
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/updateNorma/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<NormaEntity> updateNorma(HttpServletRequest httpServletRequest, @PathVariable Integer id,
			@RequestBody NormaDto normaEntity) {

		try {
			String username = jwtTokenUtil.getUsernameFromRequest(httpServletRequest);

			NormaEntity normaUpdated = normaService.updateNorma(id, normaEntity.toEntity(), username);

			return new ResponseEntity<NormaEntity>(normaUpdated, HttpStatus.OK);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(Constants.BAD_REQUEST_MESSAGE, e.getMessage(), e);
			}
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(value = "existsByCodNorma/{codNorma}", produces = APPLICATION_JSON_UTF8_VALUE)
	public boolean existsByCodNorma(@PathVariable String codNorma) {
		return normaService.existsByCodNorma(codNorma);
	}

	@GetMapping(value = "/nacional/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public List<ResponseDownload> findAllNacional() throws ParseException {

		List<ResponseDownload> response = new ArrayList<>();

		List<NormaEntity> responseEntity = normaRepository.findByTipoNorma(TipoNorma.NACIONAL);

		if (responseEntity != null) {
			for (NormaEntity norma : responseEntity) {
				ResponseDownload normaDownload = new ResponseDownload();
				normaDownload.setCodNorma(norma != null ? norma.getCodNorma() : "");
				normaDownload.setNombre(norma != null ? norma.getNombre() : "");
				normaDownload.setDescripcion(norma != null ? norma.getDescripcion() : "");
				normaDownload.setEstado(
						norma != null && norma.getEstado() != null ? norma.getEstado().getDescripcion() : "");
				normaDownload.setFecha(norma != null ? norma.getFecha() : null);
				normaDownload.setTipoNorma("Nacional");
				response.add(normaDownload);

			}
		}

		return response;
	}

}
