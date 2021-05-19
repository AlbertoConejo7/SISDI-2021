package com.sisdi.controller;

import com.sisdi.data.DepartmentData;
import com.sisdi.data.DocsData;
import com.sisdi.data.FileData;
import com.sisdi.data.FileLoanData;
import com.sisdi.data.OfficeData;
import com.sisdi.data.TransferData;
import com.sisdi.data.UserData;
import com.sisdi.model.Expediente;
import com.sisdi.model.Department;
import com.sisdi.model.FileLoan;
import com.sisdi.model.FileLoanSimple;
import com.sisdi.model.FileSimple;
import com.sisdi.model.Office;
import com.sisdi.model.OfficeSimple;
import com.sisdi.model.Transfer;
import com.sisdi.model.TransferSimple;
import com.sisdi.service.OfficeServiceImp;
import com.sisdi.model.Usuario;
import com.sisdi.model.searchOffice;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.FileLoanServiceImp;
import com.sisdi.service.TimeOutsServiceImp;
import com.sisdi.service.TransferServiceImp;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sisdi.data.FileActData;
import com.sisdi.model.FileAct;
import com.sisdi.model.FileActSimple;
import com.sisdi.model.OtherDocs;
import com.sisdi.model.Pdf;
import com.sisdi.service.FileActServiceImp;
import com.sisdi.service.OtherDocsServiceImp;
import com.sisdi.service.PdfServiceImp;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sisdi.service.ConservationTableServiceImpl;
import com.sisdi.model.TableSimple;
import com.sisdi.data.ConservationTableData;
import com.sisdi.model.ConservationTable;
import com.sisdi.model.TempUser;
import com.sisdi.model.UserEntity;
import com.sisdi.service.TempUserServiceImp;
import com.sisdi.service.UserServiceImp;

@Controller
@Slf4j
@RequestMapping(value = "/offices")
public class OfficeController {

    private Date fecha = new Date();

    @Autowired
    private OfficeServiceImp officeServiceImp;

    @Autowired
    private TimeOutsServiceImp timeOutsServiceImp;

    @Autowired
    private UserData userData;

    @Autowired
    private DepartmentData departmentData;

    @Autowired
    private OfficeData officeData;

    @Autowired
    private FileLoanData FileLoanData;

    @Autowired
    private FileData fileData;

    @Autowired
    private TransferData transferData;

    @Autowired
    private FileLoanData fileLoanData;

    @Autowired
    private DocsData docsData;

    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;

    @Autowired
    private TransferServiceImp transferServiceImp;

    @Autowired
    private FileLoanServiceImp fileLoanServiceImp;

    @Autowired
    private FileActData fileActData;

    @Autowired
    private FileActServiceImp fileActServiceImp;

    @Autowired
    private PdfServiceImp pdfServiceImp;

    @Autowired
    private OtherDocsServiceImp otherDocsServiceImp;
    
    @Autowired
    private ConservationTableServiceImpl conservationServiceImpl;
    
    @Autowired
    private  ConservationTableData  conservationTableData;
    
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private TempUserServiceImp tempUserServiceImp;

    public int addFile(String receptor, String emisor, String year, String ownerEmail, String receiverEmail) throws ParseException {
        Expediente e = expedienteServiceImp.searchFile(receptor, emisor, year);

        if (e.getFILENAME() == null) {
            log.info("1: " + e.toString());
            e.setDATE_CREATE(this.fecha);
            e.setFILENAME(year + " " + emisor + "-" + receptor);
            e.setOWNER_DEPARTMENT(emisor);
            e.setRECEIVER_DEPARTMENT(receptor);
            e.setOWNER_ID(ownerEmail);
            e.setRECEIVER_ID(receiverEmail);
            log.info("2: " + e.toString());
            expedienteServiceImp.addExpediente(e);
            //  officeAdd.setFile(e.getINDX());

        } else {
            int cantidad = e.getOFFICE_AMOUNT() + 1;
            e.setOFFICE_AMOUNT(cantidad);
            //  officeAdd.setFile(e.getINDX());
        }
        int exp = e.getINDX();
        log.info("Valor Exp" + exp);
        return exp;

    }

    @GetMapping("/listExpedient/{expedienteId}")
    public String listExpediente(@PathVariable int expedienteId, Model model, @AuthenticationPrincipal User user) {
        List<Office> offices = officeServiceImp.listarOficiosExp(expedienteId);
        Expediente e = expedienteServiceImp.getExpediente(expedienteId);
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("titleExp", e.getFILENAME());
        model.addAttribute("offices", offices);

        return "offices/listOfficesExpediente";
    }

    @GetMapping("/addOffice")
    public String addOffice(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user, HttpSession session) {
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);

        model.addAttribute("date", fecha);
        List<Department> departments = departmentData.listDepartments();
        Usuario u = userData.getUser(user.getUsername());
        officeAdd.setEmisor(u.getTempUser().getName());
        officeAdd.setEmisorDep(u.getDepartment().getName());
        officeAdd.setDateCreate(fechaS);
        model.addAttribute("departamentos", departments);
        model.addAttribute("officeAdd", officeAdd);
        session.setAttribute("user", user);

        return "offices/addOffice";
    }

    @GetMapping("/sendFile")
    public String sendFile(Model model, FileSimple fileSend, @AuthenticationPrincipal User user) {
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        model.addAttribute("date", fecha);
        List<Expediente> expedientes = fileData.listExpVencidos(expedienteServiceImp.listarExpedientesByUserState(0, user.getUsername()));

        List<Usuario> usuarios = userData.listUsers();
        Usuario u = userData.getUser(user.getUsername());
        fileSend.setOwner(u.getTempUser().getName());
        fileSend.setDepartment(u.getDepartment().getName());
        fileSend.setDateReturn(fechaS);

        model.addAttribute("expedientesPending", expedientes);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("fileSend", fileSend);

        return "offices/sendFile";
    }

    @GetMapping("/requestFile")
    public String requestFile(Model model, FileLoanSimple requestFile, @AuthenticationPrincipal User user) {
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        model.addAttribute("date", fecha);
        List<Usuario> usuarios = userData.listUsers();
        Usuario u = userData.getUser(user.getUsername());
        List<Department> departments = departmentData.listDepartments();

        requestFile.setNameRequest(u.getTempUser().getName());
        requestFile.setDepartmentRequest(u.getDepartment().getName());
        requestFile.setDateCreate(fechaS);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("departments", departments);
        model.addAttribute("requestFile", requestFile);

        return "offices/requestFile";
    }

    @PostMapping("/sendRequestFile")
    public String sendRequestFile(Model model, @ModelAttribute("requestFile") FileLoanSimple fileLoan,
            RedirectAttributes redirectAttrs, @AuthenticationPrincipal User user) throws ParseException {
        try {
            fileLoan.setFileName(fileLoan.getYear() + " " + fileLoan.getDepartmentRequest() + "-" + fileLoan.getDepartmentOther());
            FileLoan file = FileLoanData.fileLoanSimpleToFileLoan(fileLoan);

            Expediente e = expedienteServiceImp.searchFileRequest(fileLoan.getDepartmentRequest(),
                    fileLoan.getDepartmentOther(), fileLoan.getYear());

            Usuario u = userData.getUserByName(fileLoan.getNameRequest());
            file.setFILE_ID(e.getINDX());
            file.setNAME_REQUEST(u.getTempUser().getEmail());
            file.setSTATE(0);

            if (e.getFILENAME() != null) {
                fileLoanServiceImp.addFileLoan(file);

                redirectAttrs
                        .addFlashAttribute("mensaje", "Expediente encontrado, solicitud enviada")
                        .addFlashAttribute("clase", "success");
            } else {
                log.info("NO HAY EXPEDIENTE");
                redirectAttrs
                        .addFlashAttribute("mensaje", "Error, expediente no encontrado")
                        .addFlashAttribute("clase", "alert alert-danger");
            }

        } catch (Exception e) {

            redirectAttrs
                    .addFlashAttribute("mensaje", "Error la solicitud no fue enviada")
                    .addFlashAttribute("clase", "alert alert-danger");
            log.info(e.toString());

        }
        return "redirect:/offices/requestFile";
    }

    @PostMapping("/saveFile")
    public String saveFile(Model model, @ModelAttribute("fileSend") FileSimple fileSend, RedirectAttributes redirectAttrs, @AuthenticationPrincipal User user) throws ParseException {
        try {
            Expediente e1 = expedienteServiceImp.getExpediente(fileSend.getFileName());
            String send = userData.getUserByName(fileSend.getOwner()).getTempUser().getEmail();
            fileSend.setOwner("archivocentral@sanpablo.go.cr");
            fileSend.setReceiver("archivocentral@sanpablo.go.cr");
            Expediente exp = fileData.getFile(fileSend);
            exp.setOWNER_DEPARTMENT(fileSend.getDepartment());
            exp.setRECEIVER_DEPARTMENT(e1.getRECEIVER_DEPARTMENT());
            log.info("El expediente" + exp.toString());
            Transfer t = transferData.getTransfer(fileSend, send);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Expediente trasladado correctamente")
                    .addFlashAttribute("clase", "success");
            expedienteServiceImp.addExpediente(exp);
            transferServiceImp.addTransfer(t);
        } catch (Exception e) {
            String send = userData.getUserByName(fileSend.getOwner()).getTempUser().getEmail();
            Transfer t = transferData.getTransfer(fileSend, send);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al trasladar oficio")
                    .addFlashAttribute("clase", "alert alert-danger");
            log.info(e.toString());
            log.info(send);
            log.info(t.toString());
        }
        return "redirect:/offices/sendFile";
    }

    @PostMapping("/saveOffice")
    public String saveOffice(Model model, @ModelAttribute("officeAdd") OfficeSimple office, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            log.info(office.toString());
            byte[] bytes = (byte[]) session.getAttribute("bytes");
            Pdf pdf = new Pdf();
            pdf.setOFFICE(office.getOffnumber());
            pdf.setURL(bytes);

            Office o = officeData.getOffice(office, 0);
            String year = new SimpleDateFormat("yyyy").format(this.fecha);
            String ownerEmail = o.getUSER_ID();
            String receiverEmail = o.getRECEIVER_ID();
            int exp = addFile(office.getReceptorDep(), office.getEmisorDep(), year, ownerEmail, receiverEmail);
            log.info("Valor Expediente :" + exp);
            o.setEXPEDIENTE(exp);

            log.info(o.toString());
            officeServiceImp.addOffice(o);
            pdfServiceImp.addPdf(pdf);

            if (session.getAttribute("FilesOther") != null && session.getAttribute("FilesOther") != "") {
                List<OtherDocs> others = (List<OtherDocs>) session.getAttribute("FilesOther");
                for (OtherDocs od : others) {
                    od.setOFFICE(o.getOFFNUMBER());
                }
                otherDocsServiceImp.addOtherDocs(others);
            }

            redirectAttrs
                    .addFlashAttribute("mensaje", "Oficio agregado correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al agregar oficio")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/addOffice";

    }

    @PostMapping("/saveResponseOffice")
    public String saveResponseOffice(Model model, @ModelAttribute("officeActual") OfficeSimple office, HttpSession session, RedirectAttributes redirectAttrs) throws ParseException {
        Office o = officeData.getOffice(office, 1);
        log.info(o.toString());
        byte[] bytes = (byte[]) session.getAttribute("bytes");
        Pdf pdf = new Pdf();
        pdf.setOFFICE(office.getOffnumber());
        pdf.setURL(bytes);
        try {

            officeServiceImp.addOffice(o);
            String officeId = (String) session.getAttribute("officeResponse");
            log.info(officeId);
            Office of = officeServiceImp.searchOffice(officeId);
            of.setSTATE(2);
            log.info(of.toString());
            officeServiceImp.addOffice(of);
            pdfServiceImp.addPdf(pdf);
            if (session.getAttribute("FilesOther") != null && session.getAttribute("FilesOther") != "") {
                List<OtherDocs> others = (List<OtherDocs>) session.getAttribute("FilesOther");
                for (OtherDocs od : others) {
                    od.setOFFICE(o.getOFFNUMBER());
                }
                otherDocsServiceImp.addOtherDocs(others);
            }
            redirectAttrs
                    .addFlashAttribute("mensaje", "Respuesta enviada correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al responder oficio")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/pendingOffice";
    }

    @GetMapping("/responseOffice/{officeId}")
    public String responseOffice(@PathVariable String officeId, Model model, HttpSession session, @AuthenticationPrincipal User user) {
        Office officeAct = officeServiceImp.searchOffice(officeId);
        OfficeSimple office_aux = new OfficeSimple();
        String fecha_ = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        OfficeSimple office = officeData.getOfficeSimple(officeAct);

        String year = new SimpleDateFormat("yyyy").format(this.fecha);
        List<Office> offices = officeServiceImp.listarOficios();
        int INDX = offices.size();
        Department depR = departmentData.getDepartment(office.getEmisorDep());
        Department depE = departmentData.getDepartment(office.getReceptorDep());
        Usuario u = userData.getUser(user.getUsername());
        String offNumber = "OFICIO" + "-" + "MSPH" + "-" + depE.getCod() + "-"
                + depR.getCod()
                + "-" + (INDX + 1) + "-" + year;

        office_aux.setOffnumber(offNumber);
        office_aux.setEmisor(office.getReceptor());
        office_aux.setEmisorDep(office.getReceptorDep());
        office_aux.setReceptor(office.getEmisor());
        office_aux.setReceptorDep(office.getEmisorDep());
        office_aux.setReason("Responder a " + office.getOffnumber());
        office_aux.setDateCreate(fecha_);
        office_aux.setType_id(office.getType_id());
        session.setAttribute("officeResponse", officeAct.getOFFNUMBER());
        model.addAttribute("officeActual", office_aux);
        model.addAttribute("date", fecha);
        return "offices/responseOffice";
    }

    @GetMapping("/editOffice/{officeId}")
    public String editOffice(@PathVariable String officeId, Model model) throws IOException {
        Office officeAct = officeServiceImp.searchOffice(officeId);
        OfficeSimple os = officeData.getOfficeSimple(officeAct);
        List<OtherDocs> others = otherDocsServiceImp.getOtherDocs(officeId);
        log.info(others.toString());
        model.addAttribute("date", fecha);
        model.addAttribute("officeActual", os);
        model.addAttribute("title", "Ver Oficio");
        model.addAttribute("othersDocs", others);
        return "offices/editOffice";
    }

    @GetMapping(value = "/showPdf/{officeId}", produces = "application/pdf")
    public ResponseEntity<byte[]> showPdf(@PathVariable String officeId, Model model) {
        Pdf pdf = pdfServiceImp.getPdf(officeId);
        byte[] pdfContents = pdf.getURL();
        String filename = pdf.getOFFICE() + ".pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + filename);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
                pdfContents, headers, HttpStatus.OK);

        return response;
    }

    @GetMapping(value = "/showDoc/{office}/{name}")
    public ResponseEntity<byte[]> showDoc(@PathVariable String office, @PathVariable String name, Model model) {
        OtherDocs other = otherDocsServiceImp.getOtherDocs(office, name);
        log.info(office);
        log.info(name);
        byte[] content = other.getURL();
        String filename = name;
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + filename);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
                content, headers, HttpStatus.OK);

        return response;
    }

    @GetMapping("/listOffices")
    public String listOffice(Model model, @AuthenticationPrincipal User user) {
        List<Office> offices = officeServiceImp.listOfficeByEmisor(user.getUsername());
        searchOffice search = new searchOffice();
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("offices", offices);
        model.addAttribute("search", search);
        return "offices/listOffices";
    }

    @GetMapping("/listRequests")
    public String listRequest(Model model, @AuthenticationPrincipal User user) {
        List<FileLoan> request = fileLoanServiceImp.listarFileRequests();
        log.info("entrando bien");
        model.addAttribute("requests", request);
        return "offices/listRequests";
    }

    @GetMapping("/searchedOffices")
    public String searchedOffices(Model model, @AuthenticationPrincipal User user) {
        searchOffice search = new searchOffice();
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("search", search);
        return "offices/listOffices";
    }

    @PostMapping("/searchOffice")
    public String searchOffice(Model model, @ModelAttribute("search") searchOffice search, @AuthenticationPrincipal User user) throws ParseException {
        List<Office> offices = officeData.searchOffice(search, user.getUsername());
        log.info(search.toString());
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("offices", offices);
        return "offices/searchOffices";
    }

    @GetMapping("/pendingOffice")
    public String pendingOffice(Model model, @AuthenticationPrincipal User user) {
        List<Office> offices = officeServiceImp.listOfficeByReceptor(user.getUsername());
        model.addAttribute("date", fecha);
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("officesPending", offices);
        return "offices/pendingOffice";
    }

    @GetMapping("/pendingExpediente")
    public String pendingExpediente(Model model, @AuthenticationPrincipal User user) {
        List<Expediente> expedientes = expedienteServiceImp.listarExpedientesByUserState(0,user.getUsername());
        log.info(expedientes.toString());
        model.addAttribute("expedientesPending", expedientes);
        return "offices/pendingExpediente";
    }

    @PostMapping("/anular")
    public String anularOficio(Model model, @AuthenticationPrincipal User user, @ModelAttribute("officeActual") OfficeSimple office) throws ParseException {
        List<Office> offices = officeServiceImp.listOfficeByEmisor(user.getUsername());
        log.info("ejecutando el controlador Oficios");
        Office o = officeData.getOffice(office, 3);
        o.setINDX(office.getId());
        model.addAttribute("offices", offices);
        officeServiceImp.addOffice(o);
        model.addAttribute("search", new searchOffice());
        return "redirect:/offices/listOffices";
    }

    @GetMapping("/acceptOffice/{officeId}")
    public String acceptOffice(@PathVariable String officeId, Model model, @AuthenticationPrincipal User user) {
        Office officeAct = officeServiceImp.searchOffice(officeId);
        officeAct.setSTATE(1);// cambiar estado 

        officeAct = officeServiceImp.addOffice(officeAct);
        List<Office> offices = officeServiceImp.listOfficeByReceptor(user.getUsername());
        // List<TimeOuts> time = timeOutsServiceImp.listTimeOuts();

        model.addAttribute("date", fecha);
        log.info("ejecutando el controlador Oficios");
        model.addAttribute("officesPending", offices);
        // model.addAttribute("timeOuts", time);
        return "offices/pendingOffice";
    }

    @GetMapping("/acceptExpediente/{expedienteId}")
    public String acceptExpediente(@PathVariable String expedienteId, Model model, @AuthenticationPrincipal User user) {
        Expediente expedienteAct = expedienteServiceImp.searchExpediente(expedienteId);
        expedienteAct.setSTATE(1);// cambiar estado 

        expedienteAct = expedienteServiceImp.addExpediente(expedienteAct);
        List<Expediente> expedientes = expedienteServiceImp.listExpedienteByReceptor(user.getUsername());
        // List<TimeOuts> time = timeOutsServiceImp.listTimeOuts();
        //String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        //  model.addAttribute("date", fecha);
        expedienteAct.setDATE_RETURN(fecha);

        log.info("ejecutando el controlador expedientes");
        model.addAttribute("expedientesPending", expedientes);
        // expedienteAct = expedienteServiceImp.addExpediente(expedienteAct); arreglar guardar date
        // model.addAttribute("timeOuts", time);
        return "offices/pendingExpediente";
    }

    @GetMapping("/viewRequest/{requestId}")
    public String viewRequest(@PathVariable int requestId, Model model) {
        FileLoan fl = fileLoanServiceImp.searchFileLoan(requestId);
        FileLoanSimple fls = fileLoanData.fileLoanToFileLoanSimple(fl);
        model.addAttribute("date", fecha);
        model.addAttribute("requestActual", fls);
        return "offices/viewRequest";
    }

    @GetMapping("/viewOffice/{officeId}")
    public String viewOffice(@PathVariable String officeId, Model model) {
        Office officeAct = officeServiceImp.searchOffice(officeId);
        OfficeSimple os = officeData.getOfficeSimple(officeAct);
        List<OtherDocs> others = otherDocsServiceImp.getOtherDocs(officeId);
        model.addAttribute("date", fecha);
        model.addAttribute("officeActual", os);
        model.addAttribute("title", "no");
       model.addAttribute("othersDocs", others);
        return "offices/editOffice";
    }

    @GetMapping("/viewOfficeS/{officeId}")
    public String viewOfficeS(@PathVariable String officeId, Model model) {
        Office officeAct = officeServiceImp.searchOffice(officeId);
        OfficeSimple os = officeData.getOfficeSimple(officeAct);
        model.addAttribute("date", fecha);
        model.addAttribute("officeActual", os);
        model.addAttribute("title", "no-r");
        return "offices/editOffice";
    }

    @GetMapping("/authSignature")
    public String authSignature(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user) {
        return "offices/authSignature";
    }
    @GetMapping("/transfersExpediente")
    public String transfersExpediente(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user) {
        List<Expediente> list = expedienteServiceImp.listarExpedientesByState(1);
        log.info(list.toString());
        model.addAttribute("transfersFiles", list);
        return "offices/transfersExpediente";
    }
    
    @GetMapping("/transfersFiles")
    public String transfersFiles(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user) {
        List<TransferSimple> list = transferData.listTransfers();
        log.info(list.toString());
        model.addAttribute("transfers", list);
        return "offices/transfersFiles";
    }

    @GetMapping("/acceptTransfer/{transferId}")
    public String acceptTransfer(@PathVariable int transferId, Model model, @AuthenticationPrincipal User user) {
        Transfer tr = transferServiceImp.searchTransfer(transferId);
        tr.setSTATE(1);
        transferServiceImp.addTransfer(tr);
        List<TransferSimple> transfers = transferData.listTransfers();
        model.addAttribute("transfers", transfers);
        return "offices/transfersFiles";
    }

    @GetMapping("/borrowedFiles")
    public String borrowedFiles(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user) {
        List<FileLoanSimple> list = fileLoanData.listFileLoanSimples();
        //log.info(list.toString());
        model.addAttribute("fileLoans", list);
        return "offices/borrowedFiles";
    }

    @GetMapping("/listActs")
    public String listActs(Model model, OfficeSimple officeAdd, @AuthenticationPrincipal User user) {
        List<FileActSimple> list = fileActData.listFileActSimples();
        model.addAttribute("fileActs", list);
        return "offices/listActs";
    }

    @GetMapping("/deleteFile/{deleteId}")
    public String deleteFile(@PathVariable String deleteId, Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("deleteId", deleteId);

        try {

            Expediente deleteFile = expedienteServiceImp.getExpediente(deleteId);
            FileActSimple newActSimple = fileActData.SaveAct(deleteFile);
            model.addAttribute("deleteId", deleteId);
            FileAct newAct = fileActData.fileActSimpleToFileAct(newActSimple);
            fileActServiceImp.addFileAct(newAct);
        } catch (Exception excepcion) {

            excepcion.printStackTrace();
        }

        return "redirect:/offices/addAct";
    }

    @GetMapping("/addAct/{deleteId}")
    public String addAct(Model model, @AuthenticationPrincipal User user, @PathVariable String deleteId) {
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        String year = new SimpleDateFormat("yyyy").format(this.fecha);
        model.addAttribute("date", fecha);
        List<Usuario> usuarios = userData.listUsers();
        Usuario u = userData.getUser(user.getUsername());
        List<FileAct> actas = fileActServiceImp.listarFileActs();
        // FileAct ac = actas.get(actas.size() - 1);
        // int INDX = ac.getId();
        Expediente deleteFile = expedienteServiceImp.getExpediente(deleteId);
        FileActSimple actAdd = fileActData.SaveAct(deleteFile);
        // String actNumber = "Acta" + "-" + "MSPH" + "-"+ (INDX + 1) + "-" + year;
        // actAdd.setFileName(actNumber);
        actAdd.setDateCreate(fechaS);
        actAdd.setDateFile(fechaS);
        actAdd.setState(0);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("actAdd", actAdd);
        return "offices/addAct";
    }

    @PostMapping("/saveAct/{expedienteId}")
    public String saveAct(Model model, @ModelAttribute("actAdd") FileActSimple actAdd, @ModelAttribute("expedienteId") String id, RedirectAttributes redirectAttrs) throws ParseException {

        try {
            Expediente deleteFile = expedienteServiceImp.getExpediente(id);
            FileAct newAct = fileActData.fileActSimpleToFileAct(actAdd);
            int deleteIndx = deleteFile.getINDX();
            fileActServiceImp.addFileAct(newAct);
            officeServiceImp.deleteOfficesExp(deleteIndx);
            expedienteServiceImp.deleteExpediente(deleteIndx);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }

        return "offices/pendingExpediente";

    }
    
    @PostMapping("/saveExitAct/{expedienteId}")
    public String saveExitAct(Model model, @ModelAttribute("exitAct") FileActSimple exitAct, @ModelAttribute("expedienteId") String id, RedirectAttributes redirectAttrs) throws ParseException {

        try {
            log.info(exitAct.toString());
            Expediente deleteFile = expedienteServiceImp.getExpediente(id);
            FileAct newAct = fileActData.fileActSimpleToFileAct(exitAct);
            int deleteIndx = deleteFile.getINDX();
            fileActServiceImp.addFileAct(newAct);
            officeServiceImp.deleteOfficesExp(deleteIndx);
            expedienteServiceImp.deleteExpediente(deleteIndx);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }

        return "offices/listActs";

    }

    @GetMapping("/exitAct/{deleteId}")
    public String exitAct(Model model, @AuthenticationPrincipal User user, @PathVariable String deleteId) {
        log.info("problema aqui");
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        String year = new SimpleDateFormat("yyyy").format(this.fecha);
        model.addAttribute("date", fecha);
        List<Usuario> usuarios = userData.listUsers();
        Usuario u = userData.getUser(user.getUsername());
        List<FileAct> actas = fileActServiceImp.listarFileActs();

        Expediente deleteFile = expedienteServiceImp.getExpediente(deleteId);
        FileActSimple exitAct = fileActData.SaveAct(deleteFile);        
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(deleteFile.getDATE_CREATE());
        exitAct.setDateCreate(fechaS);
        exitAct.setDateFile(fecha);
        exitAct.setState(1);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("exitAct", exitAct);
        return "offices/exitAct";
    }

    @GetMapping("/reportes")
    public String reportes(Model model, @AuthenticationPrincipal User user) {
        List<Expediente> filesC = expedienteServiceImp.listarExpedientes();
        List<FileLoan> filesLoanC = fileLoanServiceImp.listarFileLoans();
        List<Office> officesC = officeServiceImp.listarOficios();
        model.addAttribute("filesC", filesC.size());
        model.addAttribute("filesLoanC", filesLoanC.size());
        model.addAttribute("officesC", officesC.size());
        return "/offices/report";
    }
  
    @GetMapping("/addConservationTable")
    public String addTable(Model model, TableSimple tableAdd, @AuthenticationPrincipal User user, HttpSession session) {
        String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        
        model.addAttribute("date", fecha);
        List<Department> departments = departmentData.listDepartments();
        Usuario u = userData.getUser(user.getUsername());
        tableAdd.setAutor(u.getTempUser().getName());
        tableAdd.setFondo(" Municipalidad de San Pablo de Heredia.");
        tableAdd.setDate_create(fechaS);
        tableAdd.setFirst_date(fechaS);
        tableAdd.setLast_date(fechaS);
        model.addAttribute("departamentos", departments);
        model.addAttribute("tableAdd", tableAdd);
        session.setAttribute("user", user); 
        model.addAttribute("date", fecha);

        return "offices/addConservationTable";
    }
    
    
     @PostMapping("/saveTable")
    public String saveTable(Model model, @ModelAttribute("tableAdd") TableSimple table, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
         
            table.setDepartment_request("Archivo Central");
           ConservationTable table2 = conservationTableData.getTable(table);
          conservationServiceImpl.addConservationTable(table2);

            redirectAttrs
                    .addFlashAttribute("mensaje", "Tabla de Conservacion agregada correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al agregar Tabla de Conservacion ")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/addConservationTable";

    }
    
    @GetMapping("/listTables")
    public String listTables(Model model, @AuthenticationPrincipal User user) {
        
        List<ConservationTable> tables = conservationServiceImpl.listConservationTable();
        model.addAttribute("tables", tables);
      
        return "offices/listTables";
    }
    
       @GetMapping("/editTable/{tableId}")
    public String editTable(@PathVariable int tableId, Model model, @AuthenticationPrincipal User user, HttpSession session) throws IOException {
         List<Department> departments = departmentData.listDepartments();
         model.addAttribute("departamentos", departments);
         ConservationTable tableAdd = conservationServiceImpl.searchTable(tableId);
         TableSimple ts = conservationTableData.getTableSimple(tableAdd);
         log.info(ts.toString());
         model.addAttribute("tableActual", ts);
         session.setAttribute("user", user); 
        
  
          return "offices/editTable";
  
        
    }
    
      @GetMapping("/deleteTable/{tableId}")
    public String deleteTable(@PathVariable int tableId, Model model, @AuthenticationPrincipal User user, HttpSession session) throws IOException {

        ConservationTable tableAdd = conservationServiceImpl.searchTable(tableId); 
        conservationServiceImpl.deleteTable(tableAdd);

          return "redirect:/offices/listTables";
           
    }
    
     @GetMapping("/viewTable/{tableId}")
    public String viewTable(@PathVariable int tableId, Model model,@AuthenticationPrincipal User user, HttpSession session) throws IOException {
  
            ConservationTable tableActual = conservationServiceImpl.searchTable(tableId);
            model.addAttribute("tableActual", tableActual);
        
        return "offices/viewTable";
    }
    

     @PostMapping("/saveEditTable")
    public String saveEditTable(Model model, @ModelAttribute("tableActual") TableSimple table, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
             String fechaS = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);    
            log.info(table.toString());
            table.setDate_create(fechaS); 
            table.setLast_date(fechaS);    
            ConservationTable ts =conservationTableData.getTable(table);
            conservationServiceImpl.addConservationTable(ts);
 
            redirectAttrs
                    .addFlashAttribute("mensaje", "Tabla de Conservacion editado correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al editar Tabla de Conservacion ")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/addConservationTable";

    }
    
    @GetMapping("/fileLoans")
    public String fileLoans(Model model, @AuthenticationPrincipal User user) {
        List<Expediente> expedientes = expedienteServiceImp.listarExpedientesByUserReceiverState(2,user.getUsername());
        log.info(expedientes.toString());
        model.addAttribute("loans", expedientes);
        return "offices/fileLoans";
    }
    
    @GetMapping("/listUsers")
    public String listUsers(Model model, @AuthenticationPrincipal User user) {

        List<Usuario> users = userData.listUsersStatus();
        model.addAttribute("Usuarios", users);

        return "offices/listUsers";
    }

    @GetMapping("/addUser")
    public String addUser(Model model, Usuario userAdd, @AuthenticationPrincipal User user, HttpSession session) {
        List<Department> departments = departmentData.listDepartments();
        model.addAttribute("departamentos", departments);
        model.addAttribute("userAdd", userAdd);
        model.addAttribute("date", fecha);

        return "offices/addUser";
    }

    @GetMapping("/editUser/{userEmail}")
    public String addUser(@PathVariable String userEmail, Model model, Usuario userAdd, @AuthenticationPrincipal User user, HttpSession session) {
        List<Department> departments = departmentData.listDepartments();
        Usuario u = userData.getUser(userEmail);
        model.addAttribute("departamentos", departments);
        model.addAttribute("userAdd", u);
        model.addAttribute("date", fecha);

        return "offices/editUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(Model model, @ModelAttribute("userAdd") Usuario userAdd, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            log.info(userAdd.toString());
            Department d = departmentData.getDepartment(userAdd.getDepartment().getName());
            userAdd.setDepartment(d);
            userAdd.setIsBoss(false);
            userAdd.setStatus(true);

            TempUser tm = userAdd.getTempUser();

            UserEntity us = new UserEntity();
            us.setTempuser(tm.getEmail());
            us.setDepartment(d.getId());
            us.setIsboss(0);
            us.setStatus(1);
            us.setPassword(userAdd.getPassword());

            tempUserServiceImp.addTempUser(tm);
            userServiceImp.addUser(us);
            userData.init();

            redirectAttrs
                    .addFlashAttribute("mensaje", "Usuario agregado correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al agregar usuario")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/addUser";

    }

    @PostMapping("/updateUser")
    public String updateUser(Model model, @ModelAttribute("userAdd") Usuario userAdd, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            Usuario u = userData.getUser(userAdd.getTempUser().getEmail());
            log.info(userAdd.toString());
            Department d = departmentData.getDepartment(userAdd.getDepartment().getName());

            TempUser tm = u.getTempUser();
            tm.setName(userAdd.getTempUser().getName());

            UserEntity us = userServiceImp.getUser(tm.getEmail());
            us.setPassword(userAdd.getPassword());
            us.setDepartment(d.getId());

            tempUserServiceImp.addTempUser(tm);
            userServiceImp.addUser(us);
            userData.init();

            redirectAttrs
                    .addFlashAttribute("mensaje", "Usuario actualizado correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al actualizar usuario")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/listUsers";

    }

    @GetMapping("/signUserEnable/{userEmail}")
    public String signUserEnable(@PathVariable String userEmail, Model model, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            UserEntity us = userServiceImp.getUser(userEmail);
            us.setIsboss(1);
            userServiceImp.addUser(us);
            userData.init();

            redirectAttrs
                    .addFlashAttribute("mensaje", "Usuario ahora necesita firma")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al actualizar usuario")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/listUsers";

    }
    @GetMapping("/signUserDisabled/{userEmail}")
    public String signUserDisabled(@PathVariable String userEmail, Model model, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            UserEntity us = userServiceImp.getUser(userEmail);
            us.setIsboss(0);
            userServiceImp.addUser(us);
            userData.init();

            redirectAttrs
                    .addFlashAttribute("mensaje", "Usuario ya no necesita firma")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al actualizar usuario")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/listUsers";

    }

    @GetMapping("/deleteUser/{userEmail}")
    public String deleteUser(@PathVariable String userEmail, Model model, RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
        try {
            Usuario u = userData.getUser(userEmail);
            UserEntity us = userServiceImp.getUser(userEmail);
            us.setStatus(0);
            userServiceImp.addUser(us);
            userData.init();

            redirectAttrs
                    .addFlashAttribute("mensaje", "Usuario eliminado correctamente")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al eliminar usuario")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/listUsers";

    }
    
}
