package Engineers.Company.model;

import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class forum {
	@Id
	@GeneratedValue	
	private int forum_id;
	@Nonnull
	private int Is_parent;
	@Nonnull
	private String forum_text;
	
	private int parent_id;

	public int getForum_id() {
		return forum_id;
	}

	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public int getIs_parent() {
		return Is_parent;
	}

	public void setIs_parent(int is_parent) {
		Is_parent = is_parent;
	}

	public String getForum_text() {
		return forum_text;
	}

	public void setForum_text(String forum_text) {
		this.forum_text = forum_text;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	
	
	
}