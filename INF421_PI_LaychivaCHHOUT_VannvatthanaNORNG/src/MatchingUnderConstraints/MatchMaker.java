package MatchingUnderConstraints;

import java.util.List;

/*
 * This function is to make a match
 */

public class MatchMaker {

	private List<Student> students;
	private List<School> schools;

	public MatchMaker(List<Student> students, List<School> school) {
		this.students = students;
		this.schools = school;
	}

	public void MakeMatches() {
		boolean madeMatch = false;

		do {
			madeMatch = false;
			for (School c : schools) {
				if (c.canMakeProposal()) {
					madeMatch = c.makeProposals();
				}
			}
		} while (madeMatch);
	}
}