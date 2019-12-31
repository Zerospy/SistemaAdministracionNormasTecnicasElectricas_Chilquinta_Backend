package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.dto.DashboardDto;
import cl.desagen.chilquinta.dto.NormaDto;
import cl.desagen.chilquinta.entities.EstadosEntity;
import cl.desagen.chilquinta.entities.FileNormaEntity;
import cl.desagen.chilquinta.entities.NormaEntity;
import cl.desagen.chilquinta.entities.UsuarioEntity;
import cl.desagen.chilquinta.enums.EstadoNorma;
import cl.desagen.chilquinta.enums.FileExtension;
import cl.desagen.chilquinta.enums.TipoNorma;
import cl.desagen.chilquinta.exceptions.BusinessException;
import cl.desagen.chilquinta.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NormaService {

    @Autowired
    private NormaRepository normaRepository;

    @Autowired
    private ObservacionNormaRepository observacionNormaRepository;

    @Autowired
    private FileNormaRepository fileNormaRepository;

    @Autowired
    private EstadosRepository estadosRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudObservacionNormaRepository solicitudObservacionNormaRepository;

    @Autowired
    private SharepointService sharepointService;

    @Value("${spring.mail.to}")
    private String[] mailTo;

    @Value("${spring.mail.publish.subject}")
    private String mailPublishSubject;

    @Value("${spring.mail.publish.body}")
    private String mailPublishBody;

    @Value("${spring.mail.comment.request.subject}")
    private String mailCommentRequestSubject;

    @Value("${spring.mail.comment.request.body}")
    private String mailCommentRequestBody;

    @Value("${spring.mail.dardebaja.subject}")
    private String maildardeBajaSubject;

    @Value("${spring.mail.dardebaja.body}")
    private String maildardeBajaBody;

    @Value("${spring.mail.toworkflow.subject}")
    private String mailtoWorkflowSubject;

    @Value("${spring.mail.toworkflow.body}")
    private String mailtoWorkflowBody;

    @Value("${spring.mail.normaeditada.subject}")
    private String mailNormaEditadaSubject;

    @Value("${spring.mail.normaeditada.body}")
    private String mailNormaEditadaBody;

    @Value("${sharepoint.enabled}")
    private Boolean sharepointEnabled;

    public Iterable<NormaEntity> findAll() {
        return normaRepository.findByTipoNorma(TipoNorma.NACIONAL);
    }

    public Iterable<NormaEntity> findAllAssigned(String username) {

        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);

        if (usuarioEntityOptional.get().getAdministrador()) {
            return normaRepository.findAll(Sort.by(Sort.Direction.ASC, "codNorma"));
        } else {
            List<Integer> usersIds = solicitudObservacionNormaRepository.getUsersIds(usuarioEntityOptional.get().getId());
            return normaRepository.findAllById(usersIds);
        }

    }

    public Iterable<NormaEntity> findAllIntenational() {
        return normaRepository.findByTipoNorma(TipoNorma.INTERNACIONAL);
    }

    public Iterable<NormaEntity> findAll(Pageable pageable) {
        return normaRepository.findAll(pageable);
    }

    public NormaEntity save(NormaEntity normaEntity, String username) {

        Timestamp tsFromInstant = Timestamp.from(Instant.now());
        normaEntity.setFecha(tsFromInstant);
        normaEntity.setDownloadCounter(0);

        if(normaEntity.getTipoNorma() != TipoNorma.DOCUMENTO){
            normaEntity.setTipoNorma(TipoNorma.NACIONAL);}
        else{

            normaEntity.setTipoNorma(TipoNorma.DOCUMENTO);
        }


        Optional<EstadosEntity> normaEstado = estadosRepository.findById(Long.valueOf(EstadoNorma.EN_REVISION.value));
        normaEntity.setEstado(normaEstado.get());

        if (normaEntity.getUsersToComment() != null && normaEntity.getUsersToComment().size() > 0 && username != null) {

            solicitudObservacionNormaRepository.deleteNormasById(normaEntity.getId());

            normaEntity.getUsersToComment().forEach(solicitudObservacionNormaEntity -> {
                Optional<UsuarioEntity> usuarioRecibeEntity = usuarioRepository.findById(solicitudObservacionNormaEntity.getUsuarioRecibeEntity().getId());
                Optional<UsuarioEntity> usuarioSolicitaEntity = usuarioRepository.findByUsuario(username);

                solicitudObservacionNormaEntity.setUsuarioSolicitaEntity(usuarioSolicitaEntity.get());
                solicitudObservacionNormaEntity.setUsuarioRecibeEntity(usuarioRecibeEntity.get());
                solicitudObservacionNormaEntity.setNormaEntity(normaEntity);
                solicitudObservacionNormaEntity.setCreatedAt(tsFromInstant);
                solicitudObservacionNormaEntity.setEnabled(true);

                emailService.sendEmail(usuarioRecibeEntity.get().getEmail().split(""), String.format(mailCommentRequestSubject, normaEntity.getCodNorma()), String.format(mailCommentRequestBody, usuarioRecibeEntity.get().getFullName(), normaEntity.getCodNorma()));
            });
        }

        return normaRepository.save(normaEntity);

    }

    public NormaEntity saveInternacional(NormaEntity normaEntity) {

        Timestamp tsFromInstant = Timestamp.from(Instant.now());
        normaEntity.setFecha(tsFromInstant);
        normaEntity.setDownloadCounter(0);
        normaEntity.setTipoNorma(TipoNorma.INTERNACIONAL);
        Optional<EstadosEntity> normaEstado = estadosRepository.findById(Long.valueOf(EstadoNorma.EN_REVISION.value));
        normaEntity.setEstado(normaEstado.get());

        return normaRepository.save(normaEntity);

    }

    public Iterable<NormaEntity> saveAll(Iterable<NormaEntity> normaEntities) {
        return normaRepository.saveAll(normaEntities);
    }

    public NormaDto findNormaDtoById(Integer id) {
        Optional<NormaEntity> normaEntity = normaRepository.findById(id);
        return normaEntity.get().toDto();
    }

    public Optional<NormaEntity> findById(Integer id) {
        return normaRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return normaRepository.existsById(id);
    }

    public Iterable<NormaEntity> findAllById(Iterable<Integer> ids) {
        return normaRepository.findAllById(ids);
    }

    public long count() {
        return normaRepository.count();
    }

    public void deleteById(Integer id) {
        normaRepository.deleteById(id);
    }

    public void delete(NormaEntity normaEntity) {
        normaRepository.delete(normaEntity);
    }

    public void deleteAll(Iterable<NormaEntity> normaEntities) {
        normaRepository.deleteAll(normaEntities);
    }

    public void deleteAll() {
        normaRepository.deleteAll();
    }

    public Iterable<NormaEntity> findAll(Sort sort) {
        return normaRepository.findAll(sort);
    }

    public void publishNorma(Integer id, String username) throws BusinessException {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        if (normaEntityOptional.isPresent()) {
            Optional<EstadosEntity> normaEstado = estadosRepository.findById(Long.valueOf(EstadoNorma.PUBLICADA.value));

            NormaEntity normaEntity = normaEntityOptional.get();
            normaEntity.setEstado(normaEstado.orElse(null));

            Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);

            UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);

            if (usuarioEntity == null) {
                throw new BusinessException("User not found");
            }

            //sharepoint
            if (sharepointEnabled) {
                Optional<FileNormaEntity> fileNormaPDF = fileNormaRepository.findByNormaEntityIdAndFileExtension(normaEntity.getId(), FileExtension.pdf);
                Optional<FileNormaEntity> fileNormaCad = fileNormaRepository.findByNormaEntityIdAndFileExtension(normaEntity.getId(), FileExtension.cad);

                if (fileNormaPDF.isPresent()) {
                    FileNormaEntity fileNormaEntity = fileNormaPDF.get();
                    sharepointService.sendDocumentToSharePoint(fileNormaEntity.getOriginalFileName(), new File(fileNormaEntity.getUrlFileLocation()));
                }

                if (fileNormaCad.isPresent()) {
                    FileNormaEntity fileNormaEntity = fileNormaCad.get();
                    sharepointService.sendDocumentToSharePoint(fileNormaEntity.getOriginalFileName(), new File(fileNormaEntity.getUrlFileLocation()));
                }
            }
            //sharepoint

            emailService.sendEmail(mailTo, String.format(mailPublishSubject, normaEntity.getCodNorma()), String.format(mailPublishBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));

            normaRepository.save(normaEntity);
        }

    }

    public void dardeBajaNorma(Integer id, String username) throws BusinessException {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        if (normaEntityOptional.isPresent()) {
            Optional<EstadosEntity> normaEstado = estadosRepository.findById(Long.valueOf(EstadoNorma.DADA_DE_BAJA.value));

            NormaEntity normaEntity = normaEntityOptional.get();
            normaEntity.setEstado(normaEstado.orElse(null));

            Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);

            UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);

            if (usuarioEntity == null) {
                throw new BusinessException("User not found");
            }

            emailService.sendEmail(mailTo, String.format(maildardeBajaSubject, normaEntity.getCodNorma()), String.format(maildardeBajaBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));

            normaRepository.save(normaEntity);

        }

    }

    public NormaEntity updateNorma(Integer id, NormaEntity normaEntity, String username) throws BusinessException {
        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);
        Timestamp tsFromInstant = Timestamp.from(Instant.now());

        if (normaEntityOptional.isPresent()) {

            NormaEntity newEntity = normaEntityOptional.get();
            newEntity.setCodNorma(normaEntity.getCodNorma());
            newEntity.setNombre(normaEntity.getNombre());
            newEntity.setDescripcion(normaEntity.getDescripcion());

            Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);
            UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);

            if (usuarioEntity == null) {
                throw new BusinessException("User not found");
            }
            emailService.sendEmail(mailTo, String.format(mailNormaEditadaSubject, normaEntity.getCodNorma()), String.format(mailNormaEditadaBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));

            if (normaEntity.getUsersToComment() != null && normaEntity.getUsersToComment().size() > 0 && username != null) {
                normaEntity.getUsersToComment().forEach(solicitudObservacionNormaEntity -> {
                    Optional<UsuarioEntity> usuarioRecibeEntity = usuarioRepository.findById(solicitudObservacionNormaEntity.getUsuarioRecibeEntity().getId());
                    Optional<UsuarioEntity> usuarioSolicitaEntity = usuarioRepository.findByUsuario(username);

                    solicitudObservacionNormaEntity.setUsuarioSolicitaEntity(usuarioSolicitaEntity.get());
                    solicitudObservacionNormaEntity.setUsuarioRecibeEntity(usuarioRecibeEntity.get());
                    solicitudObservacionNormaEntity.setNormaEntity(newEntity);
                    solicitudObservacionNormaEntity.setCreatedAt(tsFromInstant);
                    solicitudObservacionNormaEntity.setEnabled(true);

                    emailService.sendEmail(usuarioRecibeEntity.get().getEmail().split(""), String.format(mailCommentRequestSubject, newEntity.getCodNorma()), String.format(mailCommentRequestBody, usuarioRecibeEntity.get().getFullName(), newEntity.getCodNorma()));
                });
            }

            return normaRepository.save(newEntity);

        } else {
            normaEntity = normaRepository.save(normaEntity);

            return normaEntity;
        }

    }

    public void downloadCount(Integer normaId) {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(normaId);

        if (normaEntityOptional.isPresent()) {
            normaRepository.increaseCounterNormaEntity(normaId);
        }

    }

    public List<NormaEntity> findByStatus(EstadoNorma estadoNorma) {
        return normaRepository.findByStatus(Long.valueOf(estadoNorma.value));
    }

    public DashboardDto getDashboardInformation() {

        Integer normasQuantity = normaRepository.getNormasQuantity();
        Integer normasPublished = normaRepository.getNormasPublished(Long.valueOf(EstadoNorma.PUBLICADA.value));

        List<Integer> idsNormasWithFiles = fileNormaRepository.getIdsNormasWithFiles();
        Integer fileNormasQuantity = idsNormasWithFiles != null && !idsNormasWithFiles.isEmpty() ? normaRepository.getFileNormasQuantity(idsNormasWithFiles) : 0;

        Integer normasDownloaded = normaRepository.getCountNormasDownloaded();

        List<Integer> idsNormasWithComments = observacionNormaRepository.getIdsNormasWithComments();
        Integer normasCommentsQuantity = idsNormasWithComments != null && !idsNormasWithComments.isEmpty() ? normaRepository.getNormasCommentsQuantity(idsNormasWithComments) : 0;

        Integer cantidadNormasEnWorkflow = normaRepository.getCantidadNormasEnWorkflow(Long.valueOf(EstadoNorma.PUBLICADA.value));

        return new DashboardDto(normasQuantity, normasPublished, fileNormasQuantity, normasDownloaded, normasCommentsQuantity, cantidadNormasEnWorkflow);

    }

    public Iterable<NormaEntity> getAllWithFiles() {
        return normaRepository.findAllById(fileNormaRepository.getIdsNormasWithFiles());
    }

    public Iterable<NormaEntity> getNormasDownloaded() {
        return normaRepository.getNormasDownloaded();
    }

    public Iterable<NormaEntity> getNormasWithComment() {
        return normaRepository.getNormasWithComment(observacionNormaRepository.getIdsNormasWithComments());
    }

    public Iterable<NormaEntity> getNormasEnWorkflow() {
        return normaRepository.getNormasEnWorkflow(Long.valueOf(EstadoNorma.PUBLICADA.value));
    }

}
