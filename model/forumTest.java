package es.uma.ingsoftware.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class forumTest {

	private forum forum;

    @BeforeEach
    public void setUp() {
        forum = new forum();
    }

    @Test
    public void testSetAndGetForum_id() {
        int testforumId = 1;
        forum.setForum_id(testforumId);
        assertEquals(testforumId, forum.getForum_id());
    }

    @Test
    public void testSetAndGetIs_parent() {
        int testIsParent = 1;
        forum.setIs_parent(testIsParent);
        assertEquals(testIsParent, forum.getIs_parent());
    }

    @Test
    public void testSetAndGetForum_text() {
        String testforumText = "Test forum text";
        forum.setForum_text(testforumText);
        assertEquals(testforumText, forum.getForum_text());
    }

    @Test
    public void testSetAndGetParent_id() {
        int testParentId = 1;
        forum.setParent_id(testParentId);
        assertEquals(testParentId, forum.getParent_id());
    }

}
