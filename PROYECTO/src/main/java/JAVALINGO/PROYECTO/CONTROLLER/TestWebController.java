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

import JAVALINGO.PROYECTO.MODEL.Exercises;
import JAVALINGO.PROYECTO.MODEL.Forum;
import JAVALINGO.PROYECTO.MODEL.Persona;
import JAVALINGO.PROYECTO.MODEL.daily_challenges;
import JAVALINGO.PROYECTO.MODEL.Test_answers;
import JAVALINGO.PROYECTO.MODEL.Test_questions;
import JAVALINGO.PROYECTO.REPOSITORY.ForumRepository;
import JAVALINGO.PROYECTO.SERVICE.ExercisesService;
import JAVALINGO.PROYECTO.SERVICE.ForumService;
import JAVALINGO.PROYECTO.SERVICE.PersonaService;
import JAVALINGO.PROYECTO.SERVICE.Test_answersService;
import JAVALINGO.PROYECTO.SERVICE.Test_questionsService;
import JAVALINGO.PROYECTO.SERVICE.daily_challengesService;
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
	
	@Autowired
	ExercisesService exercisesService;
	
	@Autowired
	daily_challengesService daily_challengeService;
	
	@Autowired
	Test_questionsService test_questionsService;
	
	@Autowired
	Test_answersService test_answersService;
	
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
		Integer is_admin = (Integer) session.getAttribute("is_admin");
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
	
	@RequestMapping("/forum/delete/{id}")
	public String delete_forum_element(@PathVariable("id") Integer id) {
		forumService.delete_forum_questons(id);
		return "redirect:/forum";
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
	
	@PostMapping("/incrementar_votos")
	public String incrementar_votos(@RequestParam("incrementar_forum_id") Integer forum_id) {
		Forum forum = forumService.getById(forum_id);
		forumService.update_votes(forum, 1);
		return "redirect:/forum";
	}
	
	@PostMapping("/decrementar_votos")
	public String decrementar_votos(@RequestParam("decrementar_forum_id") Integer forum_id) {
		Forum forum = forumService.getById(forum_id);
		forumService.update_votes(forum, -1);
		return "redirect:/forum";
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
	
	
	@RequestMapping("/accounts/delete/{id}")
	public String delete_account(@PathVariable("id") Integer id, HttpSession session) {
		Persona p = (Persona) session.getAttribute("user");	
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p == null || is_admin != 1)
				return "redirect:/login";
		else {
			personaService.delete_persona(id);
			return "redirect:/accounts ";
		}
	}
	
	@RequestMapping("/accounts/edit/{id}")
	public String edit_account(@PathVariable("id") Integer id, HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");	
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p == null || is_admin != 1)
				return "redirect:/login";
		else {
			model.addAttribute("persona", personaService.getById(id));
			return "create_account";
		}
	}
	
	@RequestMapping("/exercises")
	public String show_exercises(Model model) {
		Query query1 = entityManager.createQuery("SELECT e FROM Exercises e");
		List<Exercises> exercises = query1.getResultList();
		Query query2 = entityManager.createQuery("SELECT q FROM Test_questions q ORDER BY question_order ASC");
		List<Test_questions> Test_questions = query2.getResultList();
		Test_questions for_th = Test_questions.get(0);
		model.addAttribute("exercises", exercises);
		model.addAttribute("question", for_th);
		return "Exercises";
	}
	
	@RequestMapping("/exercise_questions/{id}/{order}")
	public String show_questions(@PathVariable("id") Integer id, Model model, @PathVariable("order") Integer question_order) {
		Query query3 = entityManager.createQuery("SELECT q FROM Test_questions q WHERE q.exercise_id =:id ORDER BY question_order DESC");
		query3.setParameter("id", id);
		List<Test_questions> check_order = query3.getResultList();
		if (question_order > check_order.get(0).getQuestion_order())
			return "redirect:/exercises";
		
		Query query1 = entityManager.createQuery("SELECT q FROM Test_questions q WHERE exercise_id = :id AND question_order = :question_order");
		query1.setParameter("id", id);
		query1.setParameter("question_order", question_order);
		List<Test_questions> questions = query1.getResultList();
		model.addAttribute("questions", questions);
		Query query2 = entityManager.createQuery("SELECT a FROM Test_answers a");
		List<Test_answers> answers = query2.getResultList();
		model.addAttribute("answers", answers);
		return "ExerciseTemplate";
	}
	
	@RequestMapping("/admin_exercises")
	public String admin_exercises(HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p != null && is_admin == 1) {
			Query query = entityManager.createQuery("SELECT e FROM Exercises e");
			List<Exercises> exercises = query.getResultList();
			model.addAttribute("exercises", exercises);
			return "admin_exercises";
		}
			
		else
			return "redirect:/login";
	}
	
	@RequestMapping("/create_exercise")
	public String create_exercise(Model model) {
		model.addAttribute("exercise",new Exercises());
		return "create_exercise";
	}
	
	@PostMapping("create_exercise/save")
	public String save_new_exercise(Exercises e){
		exercisesService.save(e);
		return "redirect:/admin_exercises";
	}
	
	@RequestMapping("/exercise/delete/{id}")
	public String delete_exercise(@PathVariable("id") Integer id, HttpSession session) {
		Persona p = (Persona) session.getAttribute("user");	
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p == null || is_admin != 1)
				return "redirect:/login";
		else {
			exercisesService.delete_persona(id);
			return "redirect:/admin_exercises ";
		}
	}
	
	@RequestMapping("/exercise/edit/{id}")
	public String edit_exercise(@PathVariable("id") Integer id, HttpSession session, Model model) {
		Persona p = (Persona) session.getAttribute("user");	
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (p == null || is_admin != 1)
				return "redirect:/login";
		else {
			model.addAttribute("exercise", exercisesService.getById(id));
			Query query1 = entityManager.createQuery("SELECT e FROM Exercises e WHERE e.excercise_id = :id");
			query1.setParameter("id", id);
			List<Exercises> exercise_to_show = query1.getResultList();
			model.addAttribute("exercises_for_question", exercise_to_show);
			Query query2 = entityManager.createQuery("SELECT q FROM Test_questions q WHERE q.exercise_id =:id");
			query2.setParameter("id", id);
			List<Test_questions> questions = query2.getResultList();
			Query query3 = entityManager.createQuery("SELECT a FROM Test_answers a");
			List<Test_answers> answers = query3.getResultList();
			model.addAttribute("answers", answers);
			model.addAttribute("questions", questions);
			return "edit_exercise";
		}
	}
	
	@PostMapping("/create_question")
	public String create_question(@RequestParam("question_name") String question_name, @RequestParam("exercise_id") Integer exercise_id, @RequestParam("question_experience") Integer question_experience) {
		Test_questions question = new Test_questions();
		question.setExercise_id(exercise_id);
		question.setQuestion(question_name);
		question.setExperience(question_experience);
		Query query = entityManager.createQuery("SELECT q FROM Test_questions q WHERE q.exercise_id =:id ORDER BY question_order DESC");
		query.setParameter("id", exercise_id);
		List<Test_questions> to_check_order = query.getResultList();
		if(to_check_order.isEmpty())
				question.setQuestion_order(1);
		else {
			Test_questions for_order = to_check_order.get(0);
			question.setQuestion_order(for_order.getQuestion_order() + 1);
		}
		test_questionsService.save(question);
		return "redirect:/exercise/edit/" + exercise_id;
	}
	
	@PostMapping("/create_answer")
	public String create_answer(@RequestParam("question_id") Integer question_id, @RequestParam("answer") String answer_name, @RequestParam("select_type_answer") Integer select_type_answer, @RequestParam("exercise_id") Integer exercise_id) {
		Test_answers answer = new Test_answers();
		answer.setAnswer(answer_name);
		answer.setIs_correct(select_type_answer);
		answer.setQuestion_id(question_id);
		test_answersService.save(answer);
		return "redirect:/exercise/edit/" + exercise_id;
	}
	
	@RequestMapping("/daily_challenge")
	public String daily_challenges(Model model) {
		Query query1 = entityManager.createQuery("SELECT c FROM daily_challenges c WHERE challenge_day = CURRENT_DATE()");
		List<daily_challenges> challenge_of_day = query1.getResultList();
		model.addAttribute("question", challenge_of_day);
		Query query2 = entityManager.createQuery("SELECT c FROM daily_challenges c");
		List<daily_challenges> answers = query2.getResultList();
		model.addAttribute("answers", answers);
		return "dailyChallenge";
	}
	
	@RequestMapping("/admin_challenges")
	public String admin_challenges(Model model) {
		Query query = entityManager.createQuery("SELECT d FROM daily_challenges d WHERE challenge_parent IS NULL");
		List<daily_challenges> challenge_list = query.getResultList();
		model.addAttribute("challenge_list",challenge_list);
		return "admin_challenges";
	}
	@RequestMapping("/create_challenge")
	public String create_challenge(Model model) {
		model.addAttribute("challenge", new daily_challenges());
		return "create_challenge";
	}
	@PostMapping("/create_challenge/save")
	public String save_challenge(daily_challenges c) {
		daily_challengeService.save(c);
		return "redirect:/admin_challenges";
	}
	
	@RequestMapping("/challenge/delete/{id}")
	public String delete_challenge(@PathVariable("id") Integer Id) {
		daily_challengeService.delete_challenge(Id);
		return "redirect:/admin_challenges";
	}
	@RequestMapping("/challenge/edit/{id}")
	public String edit_challenge(@PathVariable("id") Integer Id, Model model) {
		model.addAttribute("challenge", daily_challengeService.getById(Id));
		Query query1 = entityManager.createQuery("SELECT c FROM daily_challenges c WHERE challenge_id = :id");
		query1.setParameter("id", Id);
		List<daily_challenges> only_one = query1.getResultList();
		model.addAttribute("for_answers", only_one);
		Query query2 = entityManager.createQuery("SELECT c FROM daily_challenges c WHERE challenge_parent = :id ");
		query2.setParameter("id", Id);
		List<daily_challenges> answers = query2.getResultList();
		model.addAttribute("answers", answers);
		return "edit_challenge";
	}
	
	@PostMapping("/create_challenge_answer")
	public String create_challenge_answer(@RequestParam("parent_id") Integer parent_id, @RequestParam("challenge_answer") String challenge_answer, @RequestParam("select_type_answer") Integer iscorrect) {
		daily_challenges answer = new daily_challenges();
		answer.setChallenge_parent(parent_id);
		answer.setIs_correct(iscorrect);
		answer.setChallenge_answer(challenge_answer);
		daily_challengeService.save(answer);
		return "redirect:/challenge/edit/" + parent_id;
	}
	
	@RequestMapping("add_experience_exercise/{id}/{order}/{question_id}/{answer_id}")
	public String add_experience_exercise(HttpSession session,Model model, @PathVariable("id") Integer exercise_id, @PathVariable("order") Integer order, @PathVariable("question_id") Integer question_id, @PathVariable("answer_id") Integer answer_id) {
		Persona p = (Persona) session.getAttribute("user");
		
		Test_questions question = test_questionsService.getById(question_id);
		Test_answers answer = test_answersService.getById(answer_id);
		Integer is_correct = answer.getIs_correct();
		if (answer.getIs_correct() == 1)
			p.setExperience(p.getExperience() + question.getExperience());
		if (p.getExperience() >= 100) {
			p.setLevel(p.getLevel() + 1);
			p.setExperience(0);
		}
		personaService.Experience(p);
		return "redirect:/result/" + exercise_id + "/" + order + "/" + is_correct;
	}
	
	@RequestMapping("result/{id}/{order}/{correct}")
	public String show_result(@PathVariable("id") Integer exercise_id, @PathVariable("order") Integer order, @PathVariable("correct") Integer is_correct, Model model){
		model.addAttribute("exercise_id",exercise_id);
		model.addAttribute("order", order);
		model.addAttribute("correct", is_correct);
		return "result";
	}
	
	@RequestMapping("result_daily_challenge/{correct}/{id}")
	public String result_daily_challenge(Model model, @PathVariable("correct") Integer is_correct, HttpSession session, @PathVariable("id") Integer id) {
		model.addAttribute("correct", is_correct);
		Persona p = (Persona) session.getAttribute("user");
		daily_challenges challenge = daily_challengeService.getById(id);
		if (is_correct == 1) {
			p.setExperience(p.getExperience() + challenge.getExperience());
		}
		if (p.getExperience() >= 100) {
			p.setLevel(p.getLevel() +1);
			p.setExperience(0);
		}
		personaService.Experience(p);
		return "result_challenge";
	}

}
