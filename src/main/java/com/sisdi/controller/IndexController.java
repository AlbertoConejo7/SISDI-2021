package com.sisdi.controller;

import com.sisdi.data.DepartmentData;
import com.sisdi.data.DocsData;
import com.sisdi.data.FileData;
import com.sisdi.data.FileLoanData;
import com.sisdi.data.OfficeData;
import com.sisdi.data.SignatureData;
import com.sisdi.data.TransferData;
import com.sisdi.data.UserData;
import com.sisdi.model.Department;
import com.sisdi.model.Expediente;
import com.sisdi.model.Office;
import com.sisdi.model.OtherDocs;
import com.sisdi.model.Signature;
import com.sisdi.model.TempUser;
import com.sisdi.model.UserEntity;
import com.sisdi.model.Usuario;
import com.sisdi.model.UsuarioSimple;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.FileActServiceImp;
import com.sisdi.service.OfficeServiceImp;
import com.sisdi.service.SignatureServiceImp;
import com.sisdi.service.TempUserServiceImp;
import com.sisdi.service.UserServiceImp;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sisdi.signature.PDFSignatureInfo;
import com.sisdi.signature.PDFSignatureInfoParser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
    El controlador de la pagina principal de SISDI
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private UserData userData;
    
    @Autowired
    private OfficeData officeData;

    @Autowired
    private DepartmentData departmentData;

    @Autowired
    private SignatureData signatureData;

    @Autowired
    private FileData fileData;

    @Autowired
    private DocsData docsData;

    @Autowired
    private TransferData transferData;

    @Autowired
    private FileLoanData fileLoanData;

    @Autowired
    private OfficeServiceImp officeServiceImp;

    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private TempUserServiceImp tempUserServiceImp;

    @Autowired
    private SignatureServiceImp signatureServiceImp;

    @Autowired
    private FileActServiceImp fileActServiceImp;

    private Date fecha = new Date();

    @GetMapping("/login")
    public String login() {
        userData.init();
        Usuario u = userData.getUser("gestiondecobros@sanpablo.go.cr");
        log.info("Usuario: " + u.toString());
        return "login";
    }

    @GetMapping("/")
    public String showIndex(Model model, @AuthenticationPrincipal User user, HttpSession session) {

        log.info("ejecutando el controlador Oficios");
        session.setAttribute("user", user);
        session.setAttribute("usuarioCompleto", userData.getUser(user.getUsername()));
        model.addAttribute("user", user);
        return "/index";
    }

    @GetMapping("/error")
    public String showError(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        session.setAttribute("user", user);
        return "error";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model, RedirectAttributes redirectAttrs) {
        String errorMessage = "Usuario o contraseña incorrectos";
        log.info(errorMessage);
        redirectAttrs
                .addFlashAttribute("mensaje", errorMessage)
                .addFlashAttribute("clase", "alert alert-danger");
        return "redirect:/login";
    }

    @PostMapping("/singleFileUpload")
    public ResponseEntity<?> singleFileUpload(Model model, @RequestParam("adjunto") MultipartFile file, HttpServletResponse response, HttpSession session) {
        JSONObject obj = new JSONObject();
        boolean signed = false;
        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            byte[] bytes = file.getBytes();
            session.setAttribute("bytes", bytes);
            List<PDFSignatureInfo> info = PDFSignatureInfoParser.getPDFSignatureInfo(bytes);
            if (info.size() > 0) {
                signed = true;
            } else {
                signed = false;
            }
        } catch (IOException | InvalidNameException | CertificateException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchProviderException e) {
            model.addAttribute("message", "Cannot open file: " + e.getMessage());
            e.printStackTrace();
        }
        obj.put("name", file.getOriginalFilename());
        obj.put("signed", signed);
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/filesUpload")
    public ResponseEntity<?> filesUpload(Model model, @RequestParam("othersF") MultipartFile[] files, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttrs) throws IOException {
        JSONArray f = new JSONArray();
        List<OtherDocs> others = new ArrayList();
        try {
            for (int i = 0; i < files.length; i++) {
                OtherDocs other = docsData.getOtherDocs(files[i], "");
                others.add(other);
                JSONObject obj = new JSONObject();
                obj.put("name", files[i].getOriginalFilename());
                f.put(obj);
            }
            session.setAttribute("FilesOther", others);
        } catch (IOException e) {

        }

        return new ResponseEntity(f.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/notificationResponse")
    public ResponseEntity<?> notificationResponse(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        List<Office> offices = officeServiceImp.listOfficeByReceptor(user.getUsername());
        List<Office> officesDate = new ArrayList<Office>();
        JSONArray officesF = new JSONArray();
        for (Office o : offices) {
            if (o.getDEADLINE() != null) {
                if (o.getSTATE() != 2) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = formatter.format(o.getDEADLINE());
                    JSONObject obj = new JSONObject();
                    obj.put("Offnumber", o.getOFFNUMBER());
                    obj.put("Deadline", strDate);
                    obj.put("Emisor", o.getUSER_ID());
                    officesF.put(obj);
                }
            }
        }
        return new ResponseEntity(officesF.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/notificationExpediente")
    public ResponseEntity<?> notificationExpediente(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        List<Expediente> expedientes = expedienteServiceImp.listExpedienteByEmisor(user.getUsername());
        JSONArray expedientesV = null;
        if (!user.getUsername().equals("archivocentral@sanpablo.go.cr")) {
            expedientesV = fileData.expedientesVencidos(expedientes);
        } else {
            expedientesV = transferData.listTransfersState();
        }
        return new ResponseEntity(expedientesV.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/fileAmount")
    public ResponseEntity<?> fileAmount(Model model, @AuthenticationPrincipal User user, HttpSession session, @RequestParam("name") String name) {
        Expediente e = expedienteServiceImp.getExpediente(name);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(e.getDATE_CREATE());
        JSONObject obj = new JSONObject();
        obj.put("Cantidad", e.getOFFICE_AMOUNT());
        obj.put("Indx", e.getINDX());
        obj.put("Create", strDate);
        log.info(strDate);
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/authSignature")
    public ResponseEntity<?> authSignature(Model model, @AuthenticationPrincipal User user, @RequestParam("direccion") String dir, HttpSession session) {
        session.setAttribute("direccion", dir);
        log.info(dir);
        JSONObject obj = new JSONObject();
        obj.put("dir", "/offices/authSignature");
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/saveSignature")
    public String saveSignature(Model model, @AuthenticationPrincipal User user, HttpSession session) {

        return "offices/saveSignature";
    }

    @PostMapping("/signatureVerification")
    public ResponseEntity<?> signatureVerification(Model model, @AuthenticationPrincipal User user, HttpSession session, @RequestParam("name") String name) {
        JSONObject obj = signatureData.verificarCertificado(name, user.getUsername());
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/saveSignatureAuth")
    public ResponseEntity<?> saveSignatureAuth(Model model, @AuthenticationPrincipal User user, @RequestParam("name") String name, HttpSession session) {
        session.setAttribute("Signature", name);
        JSONObject obj = new JSONObject();
        obj.put("dir", session.getAttribute("direccion"));
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/saveSignatureBD")
    public ResponseEntity<?> saveSignatureBD(Model model, @AuthenticationPrincipal User user, @RequestParam("PEM") String pem, @RequestParam("CID") String cID, HttpSession session, RedirectAttributes redirectAttrs) {
        JSONObject obj = new JSONObject();
        try {

            String cert = cID.substring(15, 50);
            log.info(cert);
            Signature s = new Signature();
            s.setCERTIFICATE_ID(cert);
            s.setCERTIFICATE_PEM(pem);
            s.setUSER_ID(user.getUsername());
            signatureServiceImp.addSignature(s);
            obj.put("Guardado", true);
            session.setAttribute("mensajeFirma", "Firma Habilitada, ya puede utilizarla para acceder");
        } catch (Exception e) {
            obj.put("Guardado", false);
        }

        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/userDepartment")
    public ResponseEntity<?> userDepartment(Model model, @AuthenticationPrincipal User user, HttpSession session, @RequestParam("name") String name) {
        List<Usuario> usuarios = userData.listUsersByDepartment(name);
        JSONArray users = new JSONArray();
        for (Usuario o : usuarios) {
            JSONObject obj = new JSONObject();
            obj.put("Email", o.getTempUser().getEmail());
            obj.put("Name", o.getTempUser().getName());
            users.put(obj);
        }
        return new ResponseEntity(users.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/offNumber")
    public ResponseEntity<?> offnumBer(Model model, @AuthenticationPrincipal User user, HttpSession session, @RequestParam("emisor") String emisor, @RequestParam("receptor") String receptor) {

        Department depR = departmentData.getDepartment(receptor);
        Department depE = departmentData.getDepartment(emisor);
        String year = new SimpleDateFormat("yyyy").format(this.fecha);
        List<Office> offices = officeServiceImp.listOfficeByDepartment(emisor);
        Office of = offices.get(offices.size() - 1);
        int INDX = of.getINDX();
        String offNumber = "OFICIO-MSPH-" + depE.getCod() + "-" + depR.getCod() + "-" + (INDX + 1) + "-" + year;
        JSONObject obj = new JSONObject();
        obj.put("Offnumber", offNumber);
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/perfilBefore")
    public String beforePerfil(Model model, UsuarioSimple perfil, @AuthenticationPrincipal User user, HttpSession session) {
        session.setAttribute("mensajeFirma", null);
        return "redirect:/perfil";
    }

    @GetMapping("/perfil")
    public String showPerfil(Model model, UsuarioSimple perfil, @AuthenticationPrincipal User user, HttpSession session) {
        
        Usuario u = userData.getUser(user.getUsername());
        boolean signature = signatureServiceImp.userSignature(user.getUsername());
        perfil.setName(u.getTempUser().getName());
        perfil.setEmail(u.getTempUser().getEmail());
        perfil.setDepartment(u.getDepartment().getName());
        perfil.setPassword(u.getPassword());
        model.addAttribute("perfil", perfil);
        model.addAttribute("signatureUser", signature);
        return "/profile";
    }

    @PostMapping("/updatePerfil")
    public String updatePerfil(Model model, @AuthenticationPrincipal User user, @ModelAttribute("perfil") UsuarioSimple perfil, RedirectAttributes redirectAttrs, HttpServletResponse response, HttpSession session) {
        try {

            Usuario u = userData.getUser(perfil.getEmail());

            TempUser tm = u.getTempUser();
            tm.setName(perfil.getName());

            UserEntity us = userServiceImp.getUser(tm.getEmail());
            us.setPassword(perfil.getPassword());

            tempUserServiceImp.addTempUser(tm);
            userServiceImp.addUser(us);
            userData.init();
            redirectAttrs
                    .addFlashAttribute("mensaje", "Perfil Actualizado")
                    .addFlashAttribute("clase", "success");
        } catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al actualizar perfil")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/perfil";
    }

    @PostMapping("/getDataFiles")
    public ResponseEntity<?> getDataFiles(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        JSONArray data = new JSONArray();
        JSONObject departamentos = new JSONObject();
        departamentos.put("Dep", fileData.listFileByDepartment());
        data.put(departamentos);
        JSONObject years = new JSONObject();
        departamentos.put("Years", fileData.listFileByYear());
        data.put(years);
        JSONObject others = new JSONObject();
        others.put("Prestados", expedienteServiceImp.listarExpedientesByState(2).size());
        others.put("Trasladados", expedienteServiceImp.listarExpedientesByState(1).size());
        others.put("Eliminados", fileActServiceImp.listarFileActs().size());
        data.put(others);
        return new ResponseEntity(data.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/getDataFileLoan")
    public ResponseEntity<?> getDataFileLoan(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        JSONArray data = new JSONArray();
        JSONObject departamentos = new JSONObject();
        departamentos.put("DepFileLoan", fileLoanData.listFileLoanByDepartment());
        data.put(departamentos);
        JSONObject years = new JSONObject();
        departamentos.put("YearsFileLoan", fileLoanData.listFileLoanByYear());
        data.put(years);
        JSONObject others = new JSONObject();
        others.put("Prestados", expedienteServiceImp.listarExpedientesByState(2).size());
        data.put(others);
        return new ResponseEntity(data.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/getDataOffice")
    public ResponseEntity<?> getDataOffice(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        JSONArray data = new JSONArray();
        JSONObject departamentos = new JSONObject();
        departamentos.put("OfficeDep", officeData.listOfficeByDepartment());
        data.put(departamentos);
        JSONObject years = new JSONObject();
        departamentos.put("Annio", officeData.listOfficeByYear());
        data.put(years);
        JSONObject others = new JSONObject();
        others.put("Eliminados", officeServiceImp.listOfficeByState3().size());
        data.put(others);
        return new ResponseEntity(data.toString(), new HttpHeaders(), HttpStatus.OK);
    }
}
