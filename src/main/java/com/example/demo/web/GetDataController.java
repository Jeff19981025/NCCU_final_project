package com.example.demo.web;

import java.io.IOException;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Data;
import com.example.demo.model.MedicalData;
import com.example.demo.model.User;
import com.example.demo.repository.DataRepository;
import com.example.demo.repository.MedicalDataRepository;
import com.example.demo.repository.UserRepository;

@Controller
@Service
public class GetDataController {

	private UserRepository userRepository;
	private DataRepository dataRepository;
	private MedicalDataRepository medicaldataRepository;
	public GetDataController(UserRepository userRepository, DataRepository dataRepository,MedicalDataRepository medicaldataRepository) {
		super();
		this.userRepository = userRepository;
		this.dataRepository = dataRepository;
		this.medicaldataRepository=medicaldataRepository;
	}

	@PostMapping("/authcode")
	public String authcode(@RequestParam("authcode") String authcode, Principal principal) throws IOException {

		TakeAccessToken take = new TakeAccessToken();
		String res = take.takeaccesstoken(authcode);
		System.out.print(res+'\n');

		System.out.print(principal.getName()+'\n');
		User user = userRepository.findByEmail(principal.getName());

		
        
		Data data1 = new Data();
		data1.setData_json(res);
		data1.setUser(user);
		
		System.out.print(data1.getId()+"\n");
		System.out.print(data1.getData_json()+"\n");
		System.out.print(data1.getCreation_date()+"\n");

		dataRepository.save(data1);

		return "authcode";
	}
	@GetMapping("/authorization")
	public String hospitalauth(@RequestParam("code") String authcode, Principal principal) throws IOException {

		System.out.print("this is authcode:"+authcode+"\n");
		String res=HospitalAuth.accesstoken(authcode);
		System.out.print(res+'\n');
		System.out.print(principal.getName()+'\n');

		User user = userRepository.findByEmail(principal.getName());        
		MedicalData data1 = new MedicalData();
		data1.setData_json(res);
		data1.setUser(user);
		
		System.out.print(data1.getId()+"\n");
		System.out.print(data1.getData_json()+"\n");
		System.out.print(data1.getUtilCalendar()+"\n");

		medicaldataRepository.save(data1);

		return "authcode";
	}
}
