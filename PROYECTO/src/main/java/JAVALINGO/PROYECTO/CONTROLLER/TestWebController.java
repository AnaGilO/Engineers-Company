package JAVALINGO.PROYECTO.CONTROLLER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import JAVALINGO.PROYECTO.MODEL.Forum;
import JAVALINGO.PROYECTO.MODEL.Persona;
import JAVALINGO.PROYECTO.REPOSITORY.ForumRepository;
import JAVALINGO.PROYECTO.SERVICE.ForumService;
import JAVALINGO.PROYECTO.SERVICE.PersonaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

@Controller
public class TestWebController {
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Autowired
	PersonaService personaService;
	
	@Autowired
	ForumService forumService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null)
			session.invalidate();		
		return "login";
	}
	
	@RequestMapping("/")
	public String main_menu() {
		return "MainMenu";
	}
	
	@RequestMapping("/home")
	public String home (HttpSession session) {
		Persona persona = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
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
	    	 String user_name = persona.getNombre();
	    	 Integer is_admin = persona.getIs_admin();
	         session.setAttribute("user", persona);
	         session.setAttribute("id", id);
	         session.setAttribute("user_name", user_name);
	         session.setAttribute("is_admin", is_admin);	         
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
	
	@GetMapping("/forum")
	public String forum_answers(HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");
		if (p != null) {
			Query query = entityManager.createQuery("SELECT f FROM Forum f WHERE f.is_parent = 1");
			List<Forum> forum_questions = query.getResultList();
			model.addAttribute("lista_preguntas", forum_questions);
			return "forum";
		}
			
		else
			return "redirect:/login";
	}
	
	@Modifying
	@Transactional
	@PostMapping("forum/create_question")
	public String create_question(@RequestParam("question_forum") String question_forum,@RequestParam("forum_description") String forum_description,HttpSession session) {
		Persona p = (Persona) session.getAttribute("user");
		if (p != null) {
			Integer Id = (Integer) session.getAttribute("id");
			String user_name = (String) session.getAttribute("user_name");
			/*Query query = entityManager.createQuery("INSERT INTO Forum (forum_question_name,forum_description,persona_pk,Is_parent,parent_id) "
					+ "VALUES (:question_forum, :forum_description, :id, 1, NULL)");
			query.setParameter("question_forum", question_forum);
			query.setParameter("forum_description", forum_description);
			query.setParameter("id", Id);
			query.executeUpdate();*/
			Forum forum = new Forum();
			 forum.setForum_question_name(question_forum);
		     forum.setForum_description(forum_description);
		     forum.setPersona_pk(Id);
		     forum.setParent_id(null);
		     forum.setIs_parent(1);
		     forum.setUser_name(user_name);
		     forumService.save(forum);
			return "redirect:/forum";
		}
		return "redirect:/login";
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
	
	@RequestMapping("/forum/show_replies/{id}")
	public String show_replies(@PathVariable("id") Integer id, HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");	
		if (p == null)
				return "redirect:/login";
		else {
			Query query1 = entityManager.createQuery("SELECT f FROM Forum f WHERE f.forum_id = :id");
			query1.setParameter("id", id);
			List<Forum> forum_question = query1.getResultList();
			model.addAttribute("forum_question", forum_question);
			Query query2 = entityManager.createQuery("SELECT f FROM Forum f WHERE f.parent_id = :id");
			query2.setParameter("id", id);
			List<Forum> forum_answers = query2.getResultList();
			model.addAttribute("forum_answers", forum_answers);
			return "forum_replies";
		}
	}
	
	@PostMapping("/create_answer/{id}")
	public String create_answer(@PathVariable("id") Integer id_parent, HttpSession session, Model model, @RequestParam("comentario") String comentario) {
		Persona p = (Persona) session.getAttribute("user");	
		if (p == null)
			return "redirect:/login";
		else {
			Integer Id = (Integer) session.getAttribute("id");
			String user_name = (String) session.getAttribute("user_name");
			Forum forum = new Forum();
			 forum.setForum_question_name(null);
		     forum.setForum_description(comentario);
		     forum.setPersona_pk(Id);
		     forum.setParent_id(id_parent);
		     forum.setIs_parent(0);
		     forum.setUser_name(user_name);
		     forumService.save(forum);
			return "redirect:/forum/show_replies/{id}";
		}
	}
	
	@RequestMapping ("/admin")
	public String admin_panel(HttpSession session) {
		Persona persona = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (persona != null && is_admin != 1)
			return "redirect:/logout";
		else
			return "admin_panel";
	}
	
	@RequestMapping("/accounts")
	public String accounts(HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p != null && is_admin == 1) {
			Query query = entityManager.createQuery("SELECT f FROM Persona f WHERE f.is_admin = 0");
			List<Persona> accounts = query.getResultList();
			model.addAttribute("accounts", accounts);
			return "accounts";
		}
			
		else
			return "redirect:/login";
	}
	
	@RequestMapping("/create_account")
	public String create_account(Model model) {
		model.addAttribute("persona",new Persona());
		return "create_account";
	}
	
	
	@PostMapping("create_account/save")
	public String save_new_account(Persona p){
		personaService.save_account(p);
		return "redirect:/accounts";
	}
	
	@RequestMapping("/admin_exercises")
	public String admin_exercises(HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p != null && is_admin == 1) {
			Query query = entityManager.createQuery("SELECT f FROM Persona f WHERE f.is_admin = 0");
			List<Persona> accounts = query.getResultList();
			model.addAttribute("accounts", accounts);
			return "accounts";
		}
			
		else
			return "redirect:/login";
	}
}
