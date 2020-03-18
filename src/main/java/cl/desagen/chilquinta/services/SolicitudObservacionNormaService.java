package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.dto.CommentRequestDto;
import cl.desagen.chilquinta.dto.SolicitudObservacionNormaDto;
import cl.desagen.chilquinta.entities.EstadosEntity;
import cl.desagen.chilquinta.entities.NormaEntity;
import cl.desagen.chilquinta.entities.SolicitudObservacionNormaEntity;
import cl.desagen.chilquinta.entities.UsuarioEntity;
import cl.desagen.chilquinta.enums.EstadoNorma;
import cl.desagen.chilquinta.exceptions.BusinessException;
import cl.desagen.chilquinta.repositories.EstadosRepository;
import cl.desagen.chilquinta.repositories.NormaRepository;
import cl.desagen.chilquinta.repositories.SolicitudObservacionNormaRepository;
import cl.desagen.chilquinta.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class SolicitudObservacionNormaService {

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private EstadosRepository estadosRepository;

    @Autowired
    private SolicitudObservacionNormaRepository solicitudObservacionNormaRepository;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.to}")
    private String[] mailTo;

    @Value("${spring.mail.comment.subject}")
    private String mailCommentSubject;

    @Value("${spring.mail.comment.body}")
    private String mailCommentBody;

    @Value("${spring.last-comments}")
    private Integer lastCommentsQty;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Iterable<SolicitudObservacionNormaEntity> findAll() {
        return solicitudObservacionNormaRepository.findAll();
    }

    public Iterable<SolicitudObservacionNormaEntity> findAll(Pageable pageable) {
        return solicitudObservacionNormaRepository.findAll(pageable);
    }

    public Iterable<SolicitudObservacionNormaEntity> findAllByNormaId(Integer normaId) {
        return solicitudObservacionNormaRepository.findAllByNormaEntityId(normaId);
    }

    public void saveRequestComment(SolicitudObservacionNormaDto solicitudObservacionNormaDto, String username) {

        SolicitudObservacionNormaEntity solicitudObservacionNormaEntity = new SolicitudObservacionNormaEntity();

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        solicitudObservacionNormaEntity.setCreatedAt(timestamp);
        solicitudObservacionNormaEntity.setEnabled(true);

        Optional<NormaEntity> normaEntity = normaRepository.findById(solicitudObservacionNormaDto.getNormaID());
        Optional<UsuarioEntity> usuarioSolicitaEntity = usuarioRepository.findByUsuario(username);

        solicitudObservacionNormaEntity.setNormaEntity(normaEntity.get());
        solicitudObservacionNormaEntity.setUsuarioSolicitaEntity(usuarioSolicitaEntity.get());

        solicitudObservacionNormaEntity.setComments(solicitudObservacionNormaDto.getComments());

        solicitudObservacionNormaDto.getUsuarioRecibeID().forEach(usuarioID -> {
            Optional<UsuarioEntity> usuarioRecibeEntity = usuarioRepository.findById(usuarioID);
            solicitudObservacionNormaEntity.setUsuarioRecibeEntity(usuarioRecibeEntity.get());
            solicitudObservacionNormaRepository.save(solicitudObservacionNormaEntity);
        });

    }

    public SolicitudObservacionNormaEntity save(SolicitudObservacionNormaEntity SolicitudObservacionNormaEntity) {
        return solicitudObservacionNormaRepository.save(SolicitudObservacionNormaEntity);
    }

    public Iterable<SolicitudObservacionNormaEntity> saveAll(Iterable<SolicitudObservacionNormaEntity> observacionnormaEntities) {
        return solicitudObservacionNormaRepository.saveAll(observacionnormaEntities);
    }


    public Optional<SolicitudObservacionNormaEntity> findById(Integer id) {
        return solicitudObservacionNormaRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return solicitudObservacionNormaRepository.existsById(id);
    }

    public Iterable<SolicitudObservacionNormaEntity> findAllById(Iterable<Integer> ids) {
        return solicitudObservacionNormaRepository.findAllById(ids);
    }

    public long count() {
        return solicitudObservacionNormaRepository.count();
    }

    public void deleteById(Integer id) {
        solicitudObservacionNormaRepository.deleteById(id);
    }

    public void delete(SolicitudObservacionNormaEntity SolicitudObservacionNormaEntity) {
        solicitudObservacionNormaRepository.delete(SolicitudObservacionNormaEntity);
    }

    public void deleteAll(Iterable<SolicitudObservacionNormaEntity> observacionnormaEntities) {
        solicitudObservacionNormaRepository.deleteAll(observacionnormaEntities);
    }

    public void deleteAll() {
        solicitudObservacionNormaRepository.deleteAll();
    }

    public Iterable<SolicitudObservacionNormaEntity> findAll(Sort sort) {
        return solicitudObservacionNormaRepository.findAll(sort);
    }

    public SolicitudObservacionNormaEntity saveComment(Integer id, CommentRequestDto comment, String username) throws BusinessException {

        SolicitudObservacionNormaEntity SolicitudObservacionNormaEntity = new SolicitudObservacionNormaEntity();

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        NormaEntity normaEntity = normaEntityOptional.isPresent() ? normaEntityOptional.get() : null;

        if (normaEntity == null) {
            throw new BusinessException("Norma entity not found");
        }

        Optional<EstadosEntity> normaEstado = estadosRepository.findById(EstadoNorma.CON_COMENTARIOS.value);
        normaEntity.setEstado(normaEstado.orElse(null));
        normaRepository.save(normaEntity);

        SolicitudObservacionNormaEntity.setNormaEntity(normaEntity);

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);
        UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);

        if (usuarioEntity == null) {
            throw new BusinessException("User not found");
        }

        SolicitudObservacionNormaEntity.setComments(comment.getComment());
        SolicitudObservacionNormaEntity.setUsuarioSolicitaEntity(usuarioEntity);
        SolicitudObservacionNormaEntity.setCreatedAt(timestamp);

        solicitudObservacionNormaRepository.save(SolicitudObservacionNormaEntity);

        emailService.sendEmail(mailTo, String.format(mailCommentSubject, normaEntity.getCodNorma()), String.format(mailCommentBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));

        return SolicitudObservacionNormaEntity;
    }

    public Iterable<SolicitudObservacionNormaEntity> getLastComment() {
        Pageable pageable = PageRequest.of(0, lastCommentsQty);
        return solicitudObservacionNormaRepository.findAllByNormaEntityId(pageable);
    }
}
