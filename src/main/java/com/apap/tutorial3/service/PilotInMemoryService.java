package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.PilotModel;

@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	

	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}

	@Override
	public void addPilot(PilotModel pilot) {
		// TODO Auto-generated method stub
		archivePilot.add(pilot);

	}

	@Override
	public List<PilotModel> getPilotList() {
		// TODO Auto-generated method stub
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		// TODO Auto-generated method stub
		PilotModel find = null;
		for(int x = 0;x < archivePilot.size();x++) {
			PilotModel pointer = archivePilot.get(x);
			if(pointer.getLicenseNumber().equals(licenseNumber)) {
				find = pointer;
				break;
			}
		}
		return find;
	}
	
	@Override
	public String deletePilot(String id) {
		String status = "";
		for(int x = 0;x < archivePilot.size();x++) {
			PilotModel pointer = archivePilot.get(x);
			if(pointer.getId().equals(id)) {
				archivePilot.remove(pointer);
				status = "berhasil";
			}
		}
		if(status.equals("")) {
			status = "tidak ditemukan";
		}
		return status;
	}

}
