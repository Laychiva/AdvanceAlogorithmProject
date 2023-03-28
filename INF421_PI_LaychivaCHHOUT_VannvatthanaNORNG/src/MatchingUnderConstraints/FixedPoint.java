package MatchingUnderConstraints;

import java.util.ArrayList;
import java.util.List;

/*Task 7
 * Fixed Point Algorithm.
 */

public class FixedPoint {
	private List<Integer> P = new ArrayList<Integer>();
	private List<Student> students = new ArrayList<Student>();
	private List<School> schools = new ArrayList<School>();

	public FixedPoint(List<Student> students, List<School> schools) {
		this.students = students;
		this.schools = schools;
	}
	/*
	 * Find fixed point of school
	 */
	public int findFixedPoint(School school) {
		for( int p : this.getP()) {
			if(this.Mapping_T(school, p) == p)
				return p;
		}
		return 1;
	}
	/*
	 * cutoff adjustment function,
	 */
	
	private int Mapping_T(School school, int p) {
		if (this.satisfy_constraints(school, this.demandeAtSchool(school, p))) {
			return p;
		}
		return p + 1;
	}
	private boolean satisfy_constraints(School school, List<Student> demande) {
		if (demande.size() <= school.capacity) {
			return true;
		}
		return false;
	}
	
	/*
	 * Return demade at school 
	 */
	private List<Student> demandeAtSchool(School school, int p) {
		List<Student> demande = new ArrayList<Student>();
		int arr[] = new int[school.group.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;
		for (Student student : school.preferences) {
			if (student.preferences.contains(school)
					&& school.preferences.indexOf(student) < school.preferences.size() - p + 1) { // check first
																									// condition
				for (School sch : schools) {
					if (!sch.name.equals(school.name)) {
						if (!cutofflist(sch, p).contains(student) && student.preferences.indexOf(sch) == -1) {
							for (int i = 0; i < school.group.length; i++) {
								if (student.getStudentGroup().equals(school.group[i].name)) {
									if (arr[i] < school.group[i].quota) {
										demande.add(student);
										arr[i]++;
										continue;
									}
								}
							}
							break;
						} else if (!cutofflist(sch, p).contains(student)
								&& student.preferences.indexOf(school) < student.preferences.indexOf(sch)) {// check
																											// second
																											// condition
							for (int i = 0; i < school.group.length; i++) {
								if (student.getStudentGroup().equals(school.group[i].name)) {
									if (arr[i] < school.group[i].quota) {
										demande.add(student);
										arr[i]++;
										continue;
									}
								}
							}
							break;
						}
					}
				}
			}
		}
		return demande;
	}
	/*
	 * Get cutofflist of school s with index cutoff profile p
	 */
	public List<Student> cutofflist(School school, int p) {
		List<Student> cutofflist = new ArrayList<Student>();
		for (Student student : school.preferences) {
			if (school.preferences.indexOf(student) > this.students.size() - p) {
				cutofflist.add(student);
			}
		}
		return cutofflist;
	}
	public List<Integer> getP() {
		for (int i = 1; i <= this.students.size() + 2; i++) {
			P.add(i);
		}
		return P;
	}
	public void Testing() {	
		for(School school : this.schools) {
			int p = this.findFixedPoint(school);
			System.out.println(school.name +" is matched with " + demandeAtSchool(school,p));
		}
	}
}