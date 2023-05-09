package JAVALINGO.PROYECTO.MODEL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Forum {
	@Id
	@GeneratedValue	
	private Integer forum_id;
	
	private Integer is_parent;
	
	private String forum_question_name;
	
	@Column(length=1000)
	private String forum_description;
	
	private Integer parent_id;
	
	private Integer persona_pk;
	
	private String user_name;
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getPersona_pk() {
		return persona_pk;
	}

	public void setPersona_pk(Integer persona_pk) {
		this.persona_pk = persona_pk;
	}

	public Integer getForum_id() {
		return forum_id;
	}

	public void setForum_id(Integer forum_id) {
		this.forum_id = forum_id;
	}

	public Integer getIs_parent() {
		return is_parent;
	}

	public void setIs_parent(Integer is_parent) {
		this.is_parent = is_parent;
	}

	public String getForum_question_name() {
		return forum_question_name;
	}

	public void setForum_question_name(String forum_question_name) {
		this.forum_question_name = forum_question_name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getForum_description() {
		return forum_description;
	}

	public void setForum_description(String forum_description) {
		this.forum_description = forum_description;
	}
}
