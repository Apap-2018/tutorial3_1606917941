package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value="id", required=true) String id,
			@RequestParam(value="licenseNumber", required=true) String licenseNumber,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="flyHour", required=true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	@RequestMapping(value = {"/pilot/view/license-number","/pilot/view/license-number/{licenseNumber}"})
	public String viewpath(@PathVariable Optional<String> licenseNumber, Model model) {
		String page = "errorview";
		if (licenseNumber.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive != null) {
				model.addAttribute("pilot", archive);
				page = "view-pilot";
			} else {
				model.addAttribute("errorMsg", "nomor licenseNumber tidak ditemukan");
			}
		} else {
			model.addAttribute("errorMsg", "nomor licenseNumber kosong");
		}
		return page;
	}
	@RequestMapping(value = {"/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}", "/pilot/update/license-number/fly-hour/{flyHour}", "/pilot/update/license-number/{licenseNumber}/fly-hour"})
	public String update(@PathVariable Optional<String> licenseNumber,
						@PathVariable Optional<Integer> flyHour, Model model) {
		if (licenseNumber.isPresent() & flyHour.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive != null) {
				archive.setFlyHour(flyHour.get());
				model.addAttribute("result", "Update Fly Hour Berhasil");
			} else {
				model.addAttribute("result", "nomor licenseNumber tidak ditemukan");
			}
		} else {
			model.addAttribute("result", "nomor licenseNumber atau flyHour kosong");
		}
		return "update";
	}
	
	@RequestMapping(value= {"pilot/delete/id/","pilot/delete/id/{id}"})
	public String delete(@PathVariable Optional<String> id, Model model) {
		if (id.isPresent()) {
			String status = pilotService.deletePilot(id.get());
			if (status.equals("berhasil")) {
				model.addAttribute("deleteconfirm", "Berhasil di Hapus");
			}else {
				model.addAttribute("deleteconfirm", "Penghapusan Gagal");
			}
			
		}else {
			model.addAttribute("deleteconfirm", "Penghapusan Gagal");
		}
		return "delete";
	}

}
