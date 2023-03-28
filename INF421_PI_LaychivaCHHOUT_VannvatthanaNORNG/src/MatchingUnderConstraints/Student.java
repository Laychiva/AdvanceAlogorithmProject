package MatchingUnderConstraints;

import java.util.ArrayList;

public class Student {

	ArrayList<School> preferences = new ArrayList<School>();
	private School match;
	String name;
	private String studentGroup;
	double quality;

	/**
	 * @param name  The name of this Student
	 * @param group The group of this student
	 */
	public Student(String name, String group) {
		this.name = name;
		this.setStudentGroup(group);
	}

	public Student(String name) {
		this.name = name;
	}

	public Student(String name, double quality) {
		this.name = name;
		this.quality = quality;
	}

	/**
	 * 
	 * @param s The School to insert
	 * @return true if s was successfully inserted, false if s was present in the
	 *         preference List
	 */
	public boolean insertLeastPreferredSchool(School c) {
		if (c == null || this.preferences.contains(c)) {
			return false;
		}
		this.preferences.add(c);
		// System.out.println(this.preferences);
		return true;
	}

	/**
	 * 
	 * @param s                 The School to insert
	 * @param preferenceRanking The position in the preference List to insert s
	 * @return true if s was successfully inserted, false if s was already present
	 *         in the preference List
	 */
	public boolean insertSchool(School s, int preferenceRanking) {
		if (s == null || this.preferences.contains(s)) {
			return false;
		}

		if (preferenceRanking > this.preferences.size()) {
			return this.preferences.add(s);
		}

		this.preferences.add(preferenceRanking, s);
		return true;
	}

	/**
	 * Determines whether this Student is unmatched and has School to which it has
	 * not proposed and with which it is willing to match.
	 * 
	 * @return true if this Student can make proposals, false otherwise.
	 */
	public boolean canMakeProposal() {
		return this.match == null && this.preferences.size() > 0;
	}

	/**
	 * This method unmatches this Student from its current School. We use this
	 * method in the School-Optimal Deferred Acceptance algorithm.
	 */
	public void unmatch() {
		this.match = null;
	}

	/**
	 * 
	 * Allowing the Student to process a School's proposal. The Student can accept
	 * the School's proposal, unmatching its current mate if necessary; or reject
	 * s's proposal.
	 * 
	 * @param s The School proposing to this Student
	 * @return true if this Student accepts s's proposal, false otherwise
	 */
	public boolean acceptProposal(School s) {
		if (!this.preferences.contains(s)) {
			return false;
		}
		if (this.match == null) {
			this.match = s;
			return true;
		}
		int index = this.preferences.indexOf(s);
		int matchIndex = this.preferences.indexOf((this.match));

		if (index < matchIndex) {
			this.match.unmatchStudent(this);
			this.match = s;
			return true;
		}

		return false;
	}

	/**
	 * @return School which is this Student's current mate
	 */
	public School getMatch() {
		return this.match;
	}

	/**
	 * @return String A String representation of this Student
	 */
	// public String toString(){
	// return "Student " + this.name + " is in " + this.studentGroup;
	// }
	public String toString() {
		return this.name;
	}

	public String getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(String studentGroup) {
		this.studentGroup = studentGroup;
	}

	public ArrayList<School> getPreference() {
		return this.preferences;
	}

}