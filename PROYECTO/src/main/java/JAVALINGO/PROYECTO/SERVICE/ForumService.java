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
		forumRepository.saveAndFlush(forum);
	}
}
