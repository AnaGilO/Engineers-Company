package JAVALINGO.PROYECTO.CONTROLLER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import JAVALINGO.PROYECTO.MODEL.Persona;
import JAVALINGO.PROYECTO.SERVICE.PersonaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

@Controller
public class TestWebController {
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Autowired
	PersonaService personaService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/home")
	public String home (HttpSession session) {
		Persona persona = (Persona) session.getAttribute("user");
		if (persona != null) {
			return "menu";
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		Query query = entityManager.createQuery("SELECT p FROM Persona p WHERE p.nombre = :username AND p.password = :password");
		query.setParameter("username", username);
	    query.setParameter("password", password);
	    List<Persona> personas = query.getResultList();
	    if (personas.size() == 1) {
	    	 Persona persona = personas.get(0);
	    	 Integer id = persona.getPersona_pk();
	         session.setAttribute("user", persona);
	         session.setAttribute("id", id);
	         return "redirect:/home";
	    }else {
	    	return "login";
	    }
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("persona",new Persona());
		return "registration";
	}
	
	@PostMapping("register/save")
	public String save_register(Persona p){
		personaService.save(p);
		return "redirect:/login";
	}
	
	@RequestMapping("/forum")
	public String forum_answers() {
		return "forum";
	}
	@RequestMapping("/profile")
	public ModelAndView profile(HttpSession session) {
			Persona p = (Persona) session.getAttribute("user");
			ModelAndView mav = new ModelAndView();
			if (p == null){
				  mav.setViewName("redirect:/login");
				  return mav;
			}else {
			Integer Id = (Integer) session.getAttribute("id");
			Query query = entityManager.createQuery("SELECT p FROM Persona p WHERE p.persona_pk= :id");
			query.setParameter("id", Id);
			List<Persona> personas = query.getResultList();
	        mav.setViewName("profile");
			mav.addObject("personas", personas);
		    //mav.addObject("Mail_persona", Mail_persona);
			return mav;
			}
		
	}
}
