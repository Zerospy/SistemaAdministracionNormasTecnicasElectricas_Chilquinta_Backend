package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.commons.FileUtil;
import cl.desagen.chilquinta.entities.DetallePerfilEntity;
import cl.desagen.chilquinta.entities.UsuarioEntity;
import cl.desagen.chilquinta.entities.UsuarioSaveRequest;
import cl.desagen.chilquinta.repositories.DetallePerfilRepository;
import cl.desagen.chilquinta.repositories.UsuarioRepository;
import cl.desagen.chilquinta.security.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private DetallePerfilRepository detallePerfilRepository;

	@Autowired
	private LdapService ldapService;

	@Value("${ldap.enabled}")
	private Boolean ldapEnabled;

	public Iterable<UsuarioEntity> findAll() {
		return usuarioRepository.findAllByOrderByNombresAsc();
	}

	public Iterable<UsuarioEntity> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	public UsuarioEntity save(UsuarioSaveRequest usuario) throws Exception {

		Optional<UsuarioEntity> usuarioEntityToSaveOptional = null;

		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setId(usuario.getId());
		usuarioEntity.setNombres(usuario.getNombres());
		usuarioEntity.setApellidos(usuario.getApellidos());
		usuarioEntity.setUsuario(usuario.getUsuario());
		usuarioEntity.setClave(usuario.getClave());
		usuarioEntity.setEstado(usuario.getEstado());
		usuarioEntity.setTimestamp(usuario.getTimestamp());
		usuarioEntity.setEmail(usuario.getEmail());
		usuarioEntity.setClaveTextoPlano(usuario.getClaveTextoPlano());
		usuarioEntity.setAvatarBase64(usuario.getAvatarBase64());
		usuarioEntity.setAdministrador(usuario.getAdministrador());

		if (usuarioEntity.getId() != null && usuarioEntity.getId() > 0) {
			usuarioEntityToSaveOptional = usuarioRepository.findById(usuarioEntity.getId());
		} else {
			usuarioEntityToSaveOptional = Optional.of(new UsuarioEntity());
		}

		UsuarioEntity usuarioEntityToSave;

		if (usuarioEntityToSaveOptional.isPresent()) {
			usuarioEntityToSave = usuarioEntityToSaveOptional.get();

			if (ldapEnabled) {
				log.info("--auth ldap service {}", usuarioEntityToSave.getUsuario());
				Boolean userExists = ldapService.userExists(usuarioEntityToSave.getUsuario());

				if (!userExists) {
					throw new Exception("El usuario no existe en ldap.");
				}
			}

			usuarioEntityToSave.setNombres(usuarioEntity.getNombres());
			usuarioEntityToSave.setApellidos(usuarioEntity.getApellidos());
			usuarioEntityToSave.setUsuario(usuarioEntity.getUsuario());
			usuarioEntityToSave.setEmail(usuarioEntity.getEmail());

			if (usuarioEntity.getClave() != null && !usuarioEntity.getClave().isEmpty()) {

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(usuarioEntity.getClave().getBytes());
				byte[] digest = md.digest();
				String passwordMD5 = DatatypeConverter.printHexBinary(digest).toUpperCase();

				usuarioEntityToSave.setClave(passwordMD5);
				usuarioEntityToSave.setClaveTextoPlano(usuarioEntity.getClave());
			}

			usuarioEntityToSave.setEstado(usuarioEntity.getEstado());
			usuarioEntityToSave.setAdministrador(usuarioEntity.getAdministrador());

			UsuarioEntity nuevoUsuario = usuarioRepository.save(usuarioEntityToSave);

			DetallePerfilEntity nuevaRelacion = new DetallePerfilEntity();

			if (nuevoUsuario.getId() != null && !nuevoUsuario.getAdministrador() && usuario.getPerfil() != null
					&& usuario.getIdPerfil() == null) {
				nuevaRelacion.setId(null);
				nuevaRelacion.setUsuarioId(nuevoUsuario.getId());
				nuevaRelacion.setPerfilId(usuario.getPerfil());
				detallePerfilRepository.save(nuevaRelacion);
				log.info("Relacion guardada correctamente {}", nuevaRelacion.getId());

			} else if (usuario.getIdPerfil() != null) {
				nuevaRelacion.setId(usuario.getIdPerfil());
				nuevaRelacion.setUsuarioId(usuario.getId());
				nuevaRelacion.setPerfilId(usuario.getPerfil());
				detallePerfilRepository.save(nuevaRelacion);
				log.info("Relacion Actualizada correctamente {}", nuevaRelacion.getId());
			}
			return usuarioEntityToSave;
		}

		return null;

	}

	public UsuarioEntity saveAvatar(Integer userId, MultipartFile multipartFile) throws IOException {

		Optional<UsuarioEntity> usuarioEntityToSaveOptional = usuarioRepository.findById(userId);

		UsuarioEntity usuarioEntityToSave;

		if (usuarioEntityToSaveOptional.isPresent()) {
			usuarioEntityToSave = usuarioEntityToSaveOptional.get();

			File avatarImage = FileUtil.convert(multipartFile);
			String base64 = FileUtil.encodeFileToBase64(avatarImage);

			usuarioEntityToSave.setAvatarBase64(base64);

			return usuarioRepository.save(usuarioEntityToSave);
		}

		return null;

	}

	public Iterable<UsuarioEntity> saveAll(Iterable<UsuarioEntity> usuarioEntities) {
		return usuarioRepository.saveAll(usuarioEntities);
	}

	public Optional<UsuarioEntity> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	public Optional<UsuarioEntity> findByUsuarioAndClave(String username, String clave) {
		return usuarioRepository.findByUsuarioAndClave(username, clave);
	}

	public Optional<UsuarioEntity> findByUsuario(String username) {
		return usuarioRepository.findByUsuario(username);
	}

	public boolean existsById(Integer id) {
		return usuarioRepository.existsById(id);
	}

	public Iterable<UsuarioEntity> findAllById(Iterable<Integer> ids) {
		return usuarioRepository.findAllById(ids);
	}

	public long count() {
		return usuarioRepository.count();
	}

	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	public void delete(UsuarioEntity usuarioEntity) {
		usuarioRepository.delete(usuarioEntity);
	}

	public void deleteAll(Iterable<UsuarioEntity> usuarioEntities) {
		usuarioRepository.deleteAll(usuarioEntities);
	}

	public void deleteAll() {
		usuarioRepository.deleteAll();
	}

	public Iterable<UsuarioEntity> findAll(Sort sort) {
		return usuarioRepository.findAll(sort);
	}

}
