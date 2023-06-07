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


/**
 * In this class is were all the logic is call and were we send all the information to the views
 * @author Roberto García Román
 *
 */
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
	
	/**
	 * If we the user is trying to access to the login page we give them the login view
	 * @return login view
	 * @author Roberto García Román
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	/**
	 * If the user wants to log out first we check if we have any session variable
	 * and then we destroy all the session variables to redirect them to the login again
	 * @param session
	 * @return login view
	 * @author Roberto García Román
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null)
			session.invalidate();		
		return "login";
	}
	/**
	 * First view loaded when the web page is loaded
	 * @return Index view
	 * @author Roberto García Román
	 */
	@RequestMapping("/")
	public String main_menu() {
		return "MainMenu";
	}
	/**
	 * Once we log in the page we check if the login was successful with the session variables
	 * once this is checked we see if its an admin for loading some elements in the view exclusively for them
	 * And them we load the home view. If he is not log in he is redirected to the login view
	 * @param session
	 * @return home view || login view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function we have all the logic implemented in the login.
	 * First We request all the params inserted in the view. For checking if this user exists I made a simple query
	 * that search in my database the usert with the params that where inserted in the view.
	 * If the query returns a user is that an user with that password and email exists so I create the needed
	 * session variables and return the home view. If the query does not return any element the person who tried
	 * to log in will be redirected to login view
	 * @param username
	 * @param password
	 * @param session
	 * @return home view || login view
	 * @author Roberto García Román
	 * 
	 */
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
	/**
	 * We prepare our system for inserting a new User to our database and then
	 * we load the register view
	 * @param model
	 * @return register view
	 * @author Roberto García Román
	 */
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("persona",new Persona());
		return "registration";
	}
	/**
	 * In this function we have the register functionality
	 * The only thing we use here is a save and flush for the migration of the database
	 * and allowing us to insert a new user. In the register view we have assigned with ThymeLeaf
	 * for each input which field is in the database so with this info we assigned to the person we have created when loading the view
	 * @param Person
	 * @return login view
	 * @author Roberto García Román
	 */
	@PostMapping("register/save")
	public String save_register(Persona p){
		personaService.save(p);
		return "redirect:/login";
	}
	/**
	 * Here we load the forum view.
	 * First we check if the user trying to access has logged in our web app
	 * If not we redirect to the login view.
	 * Once this is checked we list all the forum main questions with a query for the database
	 * Finally we add this list to the model for printing the information in the view
	 * @param session
	 * @param model
	 * @return forum view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function is implemented the logic for creating a new forum question.
	 * We request the params inserted in a form from the view, and we create a new
	 * forum object for saving this in our database. If everything is okey we redirect
	 * again to our forum view
	 * @param question_forum
	 * @param forum_description
	 * @param session
	 * @return forum view || login view
	 * @author Roberto García Román
	 */
	@Modifying
	@Transactional
	@PostMapping("forum/create_question")
	public String create_question(@RequestParam("question_forum") String question_forum,@RequestParam("forum_description") String forum_description,HttpSession session) {
		Persona p = (Persona) session.getAttribute("user");
		if (p != null) {
			Integer Id = (Integer) session.getAttribute("id");
			String user_name = (String) session.getAttribute("user_name");
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
	/**
	 * In this function we have the functionality for deleting forum question.
	 * We pick the id of the questions in the view and then we delete it from the database.
	 * @param id
	 * @return forum view
	 * @author Roberto García Román
	 */
	@RequestMapping("/forum/delete/{id}")
	public String delete_forum_element(@PathVariable("id") Integer id) {
		forumService.delete_forum_questons(id);
		return "redirect:/forum";
	}
	
	/**
	 * In this function we render in the view all the model information that in this cases is the info
	 * of the user that is stored in our session when anyones logged in.
	 * Is a different approach of how to render a view with Spring having an object
	 * call ModelAndView.
	 * What we make is just creating a query searching for the unique identifier of and user to show the information
	 * in the view.
	 * @param session
	 * @return profile view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function we render all the functionalities that we can have in a forum question.
	 * The forum question information and the answers of the users for this forum question.
	 * We made this creating to querys to the database one for the information of the question
	 * and other for all the answers to this question. Then we load them in the model for listing the
	 * info in our view
	 * @param id
	 * @param session
	 * @param model
	 * @return forum_replies view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function is where we create the answers to specific forum questions.
	 * First we request the parameters inserted in a form from the view.
	 * Once we catch all the parameters we insert this information In our database
	 * @param id_parent
	 * @param session
	 * @param model
	 * @param comentario
	 * @return forum replies view
	 * @author Roberto García Román
	 */
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
	/**
	 * Here we update the votes that each question have increasing in one.
	 * This is thanks that when in the view you click in a button we get the
	 * forum identification and we make an update in our database
	 * @param forum_id
	 * @return forum view
	 * @author Roberto García Román
	 */
	@PostMapping("/incrementar_votos")
	public String incrementar_votos(@RequestParam("incrementar_forum_id") Integer forum_id) {
		Forum forum = forumService.getById(forum_id);
		forumService.update_votes(forum, 1);
		return "redirect:/forum";
	}
	/**
	 * Here we update the votes that each question have decreasing in one.
	 * This is thanks that when in the view you click in a button we get the
	 * forum identification and we make an update in our database
	 * @param forum_id
	 * @return forum view
	 * @author Roberto García Román
	 */
	@PostMapping("/decrementar_votos")
	public String decrementar_votos(@RequestParam("decrementar_forum_id") Integer forum_id) {
		Forum forum = forumService.getById(forum_id);
		forumService.update_votes(forum, -1);
		return "redirect:/forum";
	}
	/**
	 * Here we load the view that only the admins have access.
	 * For making this possible first we check in our session variables for seeing if it is an admin.
	 * When this is checked we load the admin panel view
	 * @param session
	 * @return admin panel view
	 * @author Roberto García Román
	 */
	@RequestMapping ("/admin")
	public String admin_panel(HttpSession session) {
		Persona persona = (Persona) session.getAttribute("user");
		Integer is_admin = (Integer) session.getAttribute("is_admin");
		if (persona != null && is_admin != 1)
			return "redirect:/logout";
		else
			return "admin_panel";
	}
	/**
	 * In this function we load all the accounts that our web page has.
	 * As only the admins are allowed to access we check if is an admin and then
	 * we load to the model all the accounts that are not admin for preventing that
	 * an admin modifies the permissions of other admin.
	 * @param session
	 * @param model
	 * @return accounts view
	 * @author Roberto García Román
	 */
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
	/**
	 * Here we load in the model a new user for preparing the system to create a new one.
	 * Then we load the create account view
	 * @param model
	 * @return create account view
	 * @author Roberto García Román
	 */
	@RequestMapping("/create_account")
	public String create_account(Model model) {
		model.addAttribute("persona",new Persona());
		return "create_account";
	}
	
	/**
	 * Here we execute the save and flush for the user the params
	 * inserted in the form of the view.
	 * Finally we reload the accounts view
	 * @param p
	 * @return accounts view
	 * @author Roberto García Román
	 */
	@PostMapping("create_account/save")
	public String save_new_account(Persona p){
		personaService.save_account(p);
		return "redirect:/accounts";
	}
	
	/**
	 * Here we have the function for deleting non admin accounts
	 * We check if this action is being executed from an admin user
	 * an then we delete the accounts with the id passed from the view.
	 * @param id
	 * @param session
	 * @return
	 */
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
	/**
	 * For editing an account we reuse the create account view but in this case we pass
	 * the id of the account we want to edit making this that all the params of this account
	 * appears already in the inputs. As we are loading the create account view we are also
	 * reusing the function for creating an account.
	 * @param id
	 * @param session
	 * @param model
	 * @return create account view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this method we render the view for showing a list of exerises.
	 * Also we pass which is going to be the first question for each exercise.
	 * @param model
	 * @return exercises view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this method we render the question assigned via GET and the answers for the question
	 * that is rendered in our view.
	 * @param id
	 * @param model
	 * @param question_order
	 * @return exercises with question view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this method we render all the exercises that are available in our web app.
	 * Only the admin can access to this part so we check if the user is admin with our
	 * session variables
	 * @param session
	 * @param model
	 * @return admin exercises view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function we prepare the system for creating a new question.
	 * And then we render the view for creating an exercise
	 * @param model
	 * @return create exercise view
	 * @author Roberto García Román
	 */
	@RequestMapping("/create_exercise")
	public String create_exercise(Model model) {
		model.addAttribute("exercise",new Exercises());
		return "create_exercise";
	}
	/**
	 * Once all the parameters are inserted in the form of the view,
	 * we insert this params in our database
	 * @param exercise
	 * @return admin exercises view
	 * @author Roberto García Román
	 */
	@PostMapping("create_exercise/save")
	public String save_new_exercise(Exercises e){
		exercisesService.save(e);
		return "redirect:/admin_exercises";
	}
	/**
	 * In this function, after picking the id of the exercise from the view,
	 * we delete the exercise assigned with that id
	 * @param id
	 * @param session
	 * @return admin exercises view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function once we received the Id from the view,
	 * we render the edit exercise view with this 3 elements.
	 * The exercise information, The test questions that are already in this exercise
	 * and the possibles answers for the questions
	 * @param id
	 * @param session
	 * @param model
	 * @return edit exercise view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function we create questions for the exercise.
	 * First we use the parameters from a form in the view,
	 * then I use a query to have access to the exercise id of that question
	 * And finally if its the first question I assign the order to 1
	 * if not I calculate the new order
	 * @param question_name
	 * @param exercise_id
	 * @param question_experience
	 * @return edit exercise view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function we create a possible answer for a specific question
	 * As I am requesting all the elements from the view I just insert this elements in the database
	 * @param question_id
	 * @param answer_name
	 * @param select_type_answer
	 * @param exercise_id
	 * @return exercise edit view
	 * @author Roberto García Román
	 */
	@PostMapping("/create_answer")
	public String create_answer(@RequestParam("question_id") Integer question_id, @RequestParam("answer") String answer_name, @RequestParam("select_type_answer") Integer select_type_answer, @RequestParam("exercise_id") Integer exercise_id) {
		Test_answers answer = new Test_answers();
		answer.setAnswer(answer_name);
		answer.setIs_correct(select_type_answer);
		answer.setQuestion_id(question_id);
		test_answersService.save(answer);
		return "redirect:/exercise/edit/" + exercise_id;
	}
	/**
	 * In this method I render the view with the possible daily challenge for today
	 * and then I load the possible answers to teh model
	 * @param model
	 * @return daily challenges view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function I render the view of the admin daily challenges
	 * with all the challenges of all the days
	 * @param model
	 * @return admin challenges view
	 * @author Roberto García Román
	 */
	@RequestMapping("/admin_challenges")
	public String admin_challenges(Model model) {
		Query query = entityManager.createQuery("SELECT d FROM daily_challenges d WHERE challenge_parent IS NULL");
		List<daily_challenges> challenge_list = query.getResultList();
		model.addAttribute("challenge_list",challenge_list);
		return "admin_challenges";
	}
	/**
	 * In this function I prepare the system for creating a new challenge
	 * and the I render the create challenge view
	 * @param model
	 * @return create challenge view
	 * @author Roberto García Román
	 */
	@RequestMapping("/create_challenge")
	public String create_challenge(Model model) {
		model.addAttribute("challenge", new daily_challenges());
		return "create_challenge";
	}
	/**
	 * In this functions with all the parameters, that are assigned in the form of the view,
	 * I create a new challenge
	 * @param daily challenge
	 * @return admin challenge view
	 * @author Roberto García Román
	 */
	@PostMapping("/create_challenge/save")
	public String save_challenge(daily_challenges c) {
		daily_challengeService.save(c);
		return "redirect:/admin_challenges";
	}
	/**
	 * In this function, thanks to Id I receive from GET,
	 * I delete the challenge with that id
	 * @param Id
	 * @return admin challenge view
	 * @author Roberto García Román
	 */
	@RequestMapping("/challenge/delete/{id}")
	public String delete_challenge(@PathVariable("id") Integer Id) {
		daily_challengeService.delete_challenge(Id);
		return "redirect:/admin_challenges";
	}
	/**
	 * In this method I render the edit challenge view.
	 * The information to render is from the database.
	 * First I add to the model all the information needed of the challenge
	 * And then the possible answers for this challenge
	 * @param Id
	 * @param model
	 * @return edit challenge view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this method, whit the information that is requested from the view,
	 * I create a possible answers to the challenge
	 * @param parent_id
	 * @param challenge_answer
	 * @param iscorrect
	 * @return edit challenge view
	 * @author Roberto García Román
	 */
	@PostMapping("/create_challenge_answer")
	public String create_challenge_answer(@RequestParam("parent_id") Integer parent_id, @RequestParam("challenge_answer") String challenge_answer, @RequestParam("select_type_answer") Integer iscorrect) {
		daily_challenges answer = new daily_challenges();
		answer.setChallenge_parent(parent_id);
		answer.setIs_correct(iscorrect);
		answer.setChallenge_answer(challenge_answer);
		daily_challengeService.save(answer);
		return "redirect:/challenge/edit/" + parent_id;
	}
	/**
	 * In this method I update the user experience if the question is correct and also is where I check
	 * the level of the user for seeing if he level up or not
	 * @param session
	 * @param model
	 * @param exercise_id
	 * @param order
	 * @param question_id
	 * @param answer_id
	 * @return result of question view
	 * @author Roberto García Román
	 */
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
	/**
	 * In this function I render the result with some parameters
	 * for then in the view know where I need to redirect 
	 * @param exercise_id
	 * @param order
	 * @param is_correct
	 * @param model
	 * @return result of question view
	 * @author Roberto García Román
	 */
	@RequestMapping("result/{id}/{order}/{correct}")
	public String show_result(@PathVariable("id") Integer exercise_id, @PathVariable("order") Integer order, @PathVariable("correct") Integer is_correct, Model model){
		model.addAttribute("exercise_id",exercise_id);
		model.addAttribute("order", order);
		model.addAttribute("correct", is_correct);
		return "result";
	}
	/**
	 * In this method I check if the selected answer in the challenge is correct
	 * and then I assigned the experience to the user and see if I need to increase his level
	 * or not.
	 * @param model
	 * @param is_correct
	 * @param session
	 * @param id
	 * @return result challenge
	 * @author Roberto García Román
	 */
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
