package com.sisdi.controller;

import com.sisdi.data.FileData;
import com.sisdi.data.SignatureData;
import com.sisdi.data.TransferData;
import com.sisdi.data.UserData;
import com.sisdi.model.Expediente;
import com.sisdi.model.Office;
import com.sisdi.service.ExpedienteServiceImp;
import com.sisdi.service.OfficeServiceImp;
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
import signature.PDFSignatureInfo;
import signature.PDFSignatureInfoParser;

/*
    El controlador de la pagina principal de SISDI
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private UserData userData;
    
    @Autowired
    private SignatureData signatureData;
    
     @Autowired
    private FileData fileData;
     
      @Autowired
    private TransferData transferData;
    
    @Autowired
    private OfficeServiceImp officeServiceImp;
    
    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;
    
    private Date fecha = new Date();

    @GetMapping("/login")
    public String login() {
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
        return "error";
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

    @PostMapping("/notificationResponse")
    public ResponseEntity<?> notificationResponse(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        List<Office> offices = officeServiceImp.listOfficeByReceptor(user.getUsername());
        List<Office> officesDate = new ArrayList<Office>();
        JSONArray officesF=new JSONArray();
        for (Office o : offices) {
            if (o.getDEADLINE() != null) {
                if (o.getSTATE() != 2) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                    String strDate= formatter.format(o.getDEADLINE());  
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
        JSONArray expedientesV= null;
       if(!user.getUsername().equals("archivocentral@sanpablo.go.cr") ){
            expedientesV=fileData.expedientesVencidos(expedientes);
       }else{
            expedientesV=transferData.listTransfersState();
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
    
     @PostMapping("/signatureVerification")
    public ResponseEntity<?> signatureVerification(Model model, @AuthenticationPrincipal User user, HttpSession session, @RequestParam("name") String name) {
        JSONObject obj = signatureData.verificarCertificado(name);
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/saveSignatureAuth")
    public ResponseEntity<?> saveSignatureAuth(Model model, @AuthenticationPrincipal User user, @RequestParam("name") String name, HttpSession session) {
        session.setAttribute("Signature", name);
        JSONObject obj = new JSONObject();
        obj.put("dir", session.getAttribute("direccion"));
        return new ResponseEntity(obj.toString(), new HttpHeaders(), HttpStatus.OK);
    }

}
