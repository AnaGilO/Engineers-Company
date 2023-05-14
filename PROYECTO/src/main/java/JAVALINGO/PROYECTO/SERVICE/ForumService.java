package JAVALINGO.PROYECTO.SERVICE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Forum;
import JAVALINGO.PROYECTO.REPOSITORY.ForumRepository;

@Service
public class ForumService {
	@Autowired
	ForumRepository forumRepository;

	public void save(Forum forum) {
		forum.setVotes(0);
		forumRepository.saveAndFlush(forum);
	}
	
	public Forum getById(Integer id) {
		return forumRepository.getReferenceById(id);
	}
	
	public void update_votes(Forum forum, int i) {
		forum.setVotes(forum.getVotes() + i);
		forumRepository.saveAndFlush(forum);
	}
	
	public void delete_forum_questons(Integer id) {
		forumRepository.deleteById(id);
	}
}
