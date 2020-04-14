package cl.desagen.chilquinta.services;

import cl.desagen.chilquinta.dto.DashboardDto;
import cl.desagen.chilquinta.dto.NormaDto;
import cl.desagen.chilquinta.entities.*;
import cl.desagen.chilquinta.enums.EstadoNorma;
import cl.desagen.chilquinta.enums.FileExtension;
import cl.desagen.chilquinta.enums.TipoNorma;
import cl.desagen.chilquinta.exceptions.BusinessException;
import cl.desagen.chilquinta.repositories.*;
import cl.desagen.chilquinta.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CollectionTable;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

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

    @Value("${spring.mail.to.publicar}")
    private String[] mailToPublicar;

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

    @Autowired
    private StorageService storageService;

    @CollectionTable
    private String [] emails;

    public List<NormaEntity> findAll() {
        return normaRepository.findByTipoNorma(TipoNorma.NACIONAL);
    }

    @Autowired
    private UsuarioService usuarioService;


    public Iterable<NormaEntity> findAllAssigned(String username) {

        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);

        if (usuarioEntityOptional.get().getAdministrador()) {
            return normaRepository.findAll(Sort.by(Sort.Direction.ASC, "codNorma"));
        } else {
            List<Integer> usersIds = solicitudObservacionNormaRepository.getUsersIds(usuarioEntityOptional.get().getId());
            return normaRepository.findAllById(usersIds);
        }

    }

    public Iterable<NormaEntity> findAllDocumentos() {
        return normaRepository.findByTipoNorma(TipoNorma.DOCUMENTO);
    }

    public Iterable<NormaEntity> findAllIntenational() {
        return normaRepository.findByTipoNorma(TipoNorma.INTERNACIONAL);
    }

    public Iterable<NormaEntity> findAll(Pageable pageable) {
        return normaRepository.findAll(pageable);
    }

    @Transactional
    public NormaEntity save(NormaEntity normaEntity, String username) {

        Timestamp tsFromInstant = Timestamp.from(Instant.now());
        normaEntity.setFecha(tsFromInstant);
        normaEntity.setDownloadCounter(0);

        if (normaEntity.getTipoNorma() != TipoNorma.DOCUMENTO) {
            normaEntity.setTipoNorma(TipoNorma.NACIONAL);
        } else {

            normaEntity.setTipoNorma(TipoNorma.DOCUMENTO);
        }


        Optional<EstadosEntity> normaEstado = estadosRepository.findById(EstadoNorma.EN_REVISION.value);
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

                emailService.sendEmail(usuarioRecibeEntity.get().getEmail().split(";"), String.format(mailCommentRequestSubject, normaEntity.getCodNorma()), String.format(mailCommentRequestBody, usuarioRecibeEntity.get().getFullName(), normaEntity.getCodNorma()));
            });
        }

        return normaRepository.save(normaEntity);

    }

    public NormaEntity saveInternacional(NormaEntity normaEntity) {

        Timestamp tsFromInstant = Timestamp.from(Instant.now());
        normaEntity.setFecha(tsFromInstant);
        normaEntity.setDownloadCounter(0);
        normaEntity.setTipoNorma(TipoNorma.INTERNACIONAL);
        Optional<EstadosEntity> normaEstado = estadosRepository.findById(EstadoNorma.EN_REVISION.value);
        normaEntity.setEstado(normaEstado.get());

        return normaRepository.save(normaEntity);

    }

    public Iterable<NormaEntity> saveAll(Iterable<NormaEntity> normaEntities) {
        return normaRepository.saveAll(normaEntities);
    }

    public NormaDto findNormaDtoById(Integer id) {
        Optional<NormaEntity> normaEntity = normaRepository.findById(id);

        NormaDto normaDto = normaEntity.get().toDto();

        Optional<FileNormaEntity> fileNormaPDF = fileNormaRepository.findByNormaEntityIdAndFileExtension(id, FileExtension.pdf);
        Optional<FileNormaEntity> fileNormaCad = fileNormaRepository.findByNormaEntityIdAndFileExtension(id, FileExtension.cad);

        if (fileNormaPDF.isPresent()) {
            normaDto.setPdfFileName(fileNormaPDF.get().getOriginalFileName());
        }

        if (fileNormaCad.isPresent()) {
            normaDto.setCadFileName(fileNormaCad.get().getOriginalFileName());
        }

        return normaDto;
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
    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    public void publishNorma(Integer id, String username) throws BusinessException, IOException {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        if (normaEntityOptional.isPresent()) {
            Optional<EstadosEntity> normaEstado = estadosRepository.findById(EstadoNorma.PUBLICADA.value);

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
                    Resource resourcePdfFile = storageService.loadAsResource(normaEntity.getId(), FileExtension.pdf);
                    if (resourcePdfFile.exists()) {
                        String sharePointUrl = sharepointService.sendDocumentToSharePoint(normaEntity.getCodNorma(), fileNormaPDF.get().getOriginalFileName(), resourcePdfFile.getFile(), FileExtension.pdf);
                        normaEntity.setUrlPdf(sharePointUrl);
                    }
                }

                if (fileNormaCad.isPresent()) {
                    Resource resourceCadFile = storageService.loadAsResource(normaEntity.getId(), FileExtension.cad);
                    String sharePointUrl = sharepointService.sendDocumentToSharePoint(normaEntity.getCodNorma(), fileNormaCad.get().getOriginalFileName(), resourceCadFile.getFile(), FileExtension.cad);
                    normaEntity.setUrlCad(sharePointUrl);
                }
            }
            ArrayList <String> emailsUsuarios = new ArrayList<String>();
            usuarioRepository.findAll().forEach(ObtenerUsuariosEmail -> {





                emailsUsuarios.add(ObtenerUsuariosEmail.getEmail());
         //       String[] correos = ObtenerUsuariosEmail.getEmail();
                String mailToPublicar [] = GetStringArray(emailsUsuarios);

                emailService.sendEmail(mailToPublicar, String.format(mailPublishSubject, normaEntity.getCodNorma()), String.format(mailPublishBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));
                    emailsUsuarios.clear();

                System.out.println(emailsUsuarios);
            });


            normaRepository.save(normaEntity);
        }

    }
        @Transactional
    public  NormaEntity modificarCampos(Integer id,NormaEntity normaEntity, String username) throws BusinessException {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        if (normaEntityOptional.isPresent()) {


            NormaEntity newEntity = normaEntityOptional.get();

            newEntity.setCodNorma(normaEntity.getCodNorma());
            newEntity.setNombre(normaEntity.getNombre());
            newEntity.setDescripcion(normaEntity.getDescripcion());



            Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByUsuario(username);

            UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);

            emailService.sendEmail(mailTo, String.format(mailNormaEditadaSubject, normaEntity.getCodNorma()), String.format(mailNormaEditadaBody, normaEntity.getCodNorma(), usuarioEntity.getFullName()));
            return normaRepository.save(newEntity);
        }
            return normaRepository.save(normaEntity);
    }


    public void dardeBajaNorma(Integer id, String username) throws BusinessException {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(id);

        if (normaEntityOptional.isPresent()) {
            Optional<EstadosEntity> normaEstado = estadosRepository.findById(EstadoNorma.DADA_DE_BAJA.value);

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

    @Transactional
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

                solicitudObservacionNormaRepository.deleteNormasById(newEntity.getId());
                newEntity.getUsersToComment().clear();

                    normaEntity.getUsersToComment().forEach(solicitudObservacionNormaEntity -> {
                    Optional<UsuarioEntity> usuarioRecibeEntity = usuarioRepository.findById(solicitudObservacionNormaEntity.getUsuarioRecibeEntity().getId());

                    SolicitudObservacionNormaEntity solObsEntity = new SolicitudObservacionNormaEntity();
                    solObsEntity.setUsuarioSolicitaEntity(usuarioEntity);
                    solObsEntity.setUsuarioRecibeEntity(usuarioRecibeEntity.get());
                    solObsEntity.setNormaEntity(newEntity);
                    solObsEntity.setCreatedAt(tsFromInstant);
                    solObsEntity.setEnabled(true);

                    solicitudObservacionNormaRepository.save(solObsEntity);

                    emailService.sendEmail(usuarioRecibeEntity.get().getEmail().split(";"), String.format(mailCommentRequestSubject, newEntity.getCodNorma()), String.format(mailCommentRequestBody, usuarioRecibeEntity.get().getFullName(), newEntity.getCodNorma()));
                });
            }

            return normaRepository.save(newEntity);

        } else {
            return normaRepository.save(normaEntity);
        }

    }

    public void downloadCount(Integer normaId) {

        Optional<NormaEntity> normaEntityOptional = normaRepository.findById(normaId);

        if (normaEntityOptional.isPresent()) {
            normaRepository.increaseCounterNormaEntity(normaId);
        }

    }

    public List<NormaEntity> findByStatus(EstadoNorma estadoNorma) {
        return normaRepository.findByStatus(estadoNorma.value);
    }

    public DashboardDto getDashboardInformation() {

        Integer normasQuantity = normaRepository.getNormasQuantity();
        Integer normasPublished = normaRepository.getNormasPublished(EstadoNorma.PUBLICADA.value);

        List<Integer> idsNormasWithFiles = fileNormaRepository.getIdsNormasWithFiles();
        Integer fileNormasQuantity = idsNormasWithFiles != null && !idsNormasWithFiles.isEmpty() ? normaRepository.getFileNormasQuantity(idsNormasWithFiles) : 0;

        Integer normasDownloaded = normaRepository.getCountNormasDownloaded();

        List<Integer> idsNormasWithComments = observacionNormaRepository.getIdsNormasWithComments();
        Integer normasCommentsQuantity = idsNormasWithComments != null && !idsNormasWithComments.isEmpty() ? normaRepository.getNormasCommentsQuantity(idsNormasWithComments) : 0;

        Integer cantidadNormasEnWorkflow = normaRepository.getCantidadNormasEnWorkflow(EstadoNorma.PUBLICADA.value);

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
        return normaRepository.getNormasEnWorkflow(EstadoNorma.PUBLICADA.value);
    }
    
    public boolean existsByCodNorma(String codNorma) {
    	boolean existe = false;
    	NormaEntity norma = normaRepository.existsByCodNorma(codNorma);
    	
    	if(norma != null) {
    		existe = true;
    	}
    	
        return existe;
    } 

}
