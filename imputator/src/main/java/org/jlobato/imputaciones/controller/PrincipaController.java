package org.jlobato.imputaciones.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipaController {
	
	@GetMapping({"", "/"})
	public String main(Model model) {
		return "main";
	}
}
