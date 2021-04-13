package com.sisdi.controller;

import com.sisdi.data.OfficeData;
import com.sisdi.data.VersionData;
import com.sisdi.model.Office;
import com.sisdi.model.OfficeSimple;
import com.sisdi.model.Version;
import com.sisdi.service.OfficeServiceImp;
import com.sisdi.service.VersionService;
import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping(value = "/versions")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Autowired
    private OfficeServiceImp officeService;

    @Autowired
    private OfficeData officeData;

    @Autowired
    private VersionData versionData;

    private Date fecha = new Date();

    @GetMapping("/listVersions/{officeId}")
    public String listVersion(@PathVariable String officeId, Model model, @AuthenticationPrincipal User user) {
        Office officeAct = officeService.searchOffice(officeId);
        OfficeSimple os = officeData.getOfficeSimple(officeAct);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(fecha);
        os.setDateCreate(date);
        List<Version> versions = versionService.listVersionByOffice(officeId);
        model.addAttribute("date", fecha);
        model.addAttribute("officeActual", os);
        model.addAttribute("title", "Versiones");
        log.info("ejecutando el controlador Versiones");
        model.addAttribute("versions", versions);
        return "offices/versionOffice";
    }

    @PostMapping("/saveVersion")
    public String saveVersion(@ModelAttribute("officeActual") OfficeSimple office, RedirectAttributes redirectAttrs) throws ParseException {
        try {
            Version v = versionData.getVersion(office);
            Office off = officeData.getOffice(office, 0);
            off.setINDX(office.getId());
            log.info(office.toString());
            officeService.addOffice(off);
            versionService.save_version(v);

            redirectAttrs
                    .addFlashAttribute("mensaje", "Versión agregada correctamente")
                    .addFlashAttribute("clase", "success");
        } 
        catch (Exception e) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Error al agregar versión")
                    .addFlashAttribute("clase", "alert alert-danger");
        }
        return "redirect:/offices/editOffice/" + office.getOffnumber();
    }

}
