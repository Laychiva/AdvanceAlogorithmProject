package MatchingUnderConstraints;

import java.util.ArrayList;
import java.util.List;

/*
 * Group Class
 */

public class Group {
	String name;
	List<Student> studentsInGroup = new ArrayList<Student>();
	int quota;

	public Group(String name, ArrayList<Student> studentinGroup, int quota) {
		this.name = name;
		this.studentsInGroup = studentinGroup;
		this.quota = quota;
	}

	public Group(String name, ArrayList<Student> studentinGroup) {
		this.name = name;
		this.studentsInGroup = studentinGroup;
		this.quota = 0;
	}
}