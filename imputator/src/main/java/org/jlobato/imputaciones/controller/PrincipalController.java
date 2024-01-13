package org.jlobato.imputaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
	
	@GetMapping({"", "/"})
	public String main(Model model) {
		return "main";
	}
}
