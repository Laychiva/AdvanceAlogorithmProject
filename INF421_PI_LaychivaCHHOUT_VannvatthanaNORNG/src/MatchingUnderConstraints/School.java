package MatchingUnderConstraints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class School {

	PriorityQueue<Student> matches;
	ArrayList<Student> preferences;
	String name;
	int capacity;
	Group[] group;

	/**
	 * @param name     The name of this School
	 * @param capacity The number of Students this School can admit
	 */
	public School(String name, int capacity, Group[] group) {
		this.name = name;
		this.preferences = new ArrayList<Student>();
		this.capacity = capacity;
		this.group = group;

		// ranks Students based on their positions in the preferences List
		// this Comparator ensures that the PriorityQueue is stores Students
		// in order of increasing preference
		Comparator<Student> ranking = new Comparator<Student>() {

			public int compare(Student i1, Student i2) {
				int indexOne = School.this.preferences.indexOf(i1);
				int indexTwo = School.this.preferences.indexOf(i2);

				return indexTwo - indexOne;
			}
		};

		this.matches = new PriorityQueue<Student>(capacity, ranking);

	}
	
	
	public School(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.preferences = new ArrayList<Student>();

		Comparator<Student> ranking = new Comparator<Student>() {

			public int compare(Student i1, Student i2) {
				int indexOne = School.this.preferences.indexOf(i1);
				int indexTwo = School.this.preferences.indexOf(i2);

				return indexTwo - indexOne;
			}
		};

		this.matches = new PriorityQueue<Student>(capacity, ranking);
	}

	/**
	 * 
	 * @param s The Student to insert
	 * @return true if s was successfully inserted, false if s was present in the
	 *         preference List
	 */
	public boolean insertLeastPreferredStudent(Student i) {
		if (i == null || this.preferences.contains(i)) {
			return false;
		}
		return this.preferences.add(i);
	}

	/**
	 * 
	 * @param i is the Student to insert
	 * @param preferenceRanking The order in the preferences List to insert i
	 * @return true if i was successfully inserted, false if i was present in the
	 *         preference List
	 */
	public boolean insertStudent(Student i, int preferenceRanking) {
		if (i == null || this.preferences.contains(i)) {
			return false;
		}

		if (preferenceRanking >= this.preferences.size()) {
			return this.preferences.add(i);
		}

		this.preferences.add(preferenceRanking, i);
		return true;
	}

	/**
	 * In this method, a School can make proposals if it has not reached its quota of admitted
	 * Students and it has Students to which it has not proposed (and with which it
	 * is willing to match).
	 * 
	 * @return true if this School can make proposals, false otherwise
	 */
	public boolean canMakeProposal() {
		// System.out.print(this.matches);
		return this.matches.size() < this.capacity && this.preferences.size() > 0;
	}

	/**
	 * @param s The Student to remove from this College's admitted students
	 */
	public void unmatchStudent(Student s) {
		// System.out.println(this + " unmatched from" + s);
		this.matches.remove(s);
	}

	/**
	 * In this method, this School proposes to Students in preference order until it has fulfilled
	 * its quota or runs out of Students to which it can propose.
	 * 
	 * @return true if this School added a Student to its matches, false otherwise
	 */
	public boolean makeProposals() {
		// System.out.println(this.preferences);
		boolean madeMatch = false;
		int arr[] = new int[group.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;
		while (this.preferences.size() > 0 && this.matches.size() < this.capacity) {
			Student temp = this.preferences.remove(0);
			if (temp.acceptProposal(this)) {
				for (int i = 0; i < group.length; i++) {
					if (temp.getStudentGroup().equals(group[i].name)) {
						// System.out.println(group[i].quota);
						if (arr[i] < group[i].quota) { // check group quota 
							this.matches.add(temp);
							madeMatch = true;
							arr[i]++;
							// System.out.println();
							continue;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * @return PriorityQueue<Student> The admitted Students for this College
	 */
	public PriorityQueue<Student> getMatches() {
		return this.matches;
	}

	/**
	 * @return A String representation of this College
	 */
	public String toString() {
		return this.name;
	}

	/*
	 ************************* Task5 *********************************** 
	 */

	public void FairRanking() {
    	int n_group = this.group.length; // total number of groups in I
    	int n_student = this.preferences.size(); // total number of students in I
    	//System.out.println(this.preferences);
    	/** Getting the fraction of each group in I **/
    	double[] frac = new double[n_group]; // fraction of students of each group in I
    	for(int j = 0; j < n_group; j++) 
    		frac[j] = (double)this.group[j].studentsInGroup.size() / n_student;
    	//for(double dou : frac) {
    	//	System.out.println(dou);
    	//}
    	
    	for(int k = 1; k <= n_student; k++) {
    		int[] groupCount = CountStudentsInGroup(this,k); // from rank k or better, count the number of students in each group
    		double[] limInf = new double[n_group];
    		double[] limSup = new double[n_group];
    		
    		for(int j = 0; j < n_group; j++) {
    			limInf[j] = (double)(k) * 0.8 * frac[j];
    			limSup[j] = (double)(k) * 1.2 * frac[j];
    			//System.out.println(groupCount[j]);
    			if (groupCount[j] < Math.floor(limInf[j])) {
    				while(groupCount[j] < limInf[j]) {
    					for(int i = k; i <= n_student; i++) {
    						if(this.preferences.get(i).getStudentGroup().equals(this.group[j].name)) {
    							Student tmp = this.preferences.get(i);
    							for(int l = i; l >= k; l--) {
    								this.preferences.set(l, this.preferences.get(l-1));
    							}
    						this.preferences.set(k, tmp);
    						//System.out.println(this.preferences);
    						break;
    						}
    					}
    					groupCount[j]++;
    				}
    			}
    			if(groupCount[j] > Math.ceil(limSup[j])) {
    				while(groupCount[j] > limSup[j]) {
    					for(int i = k; i >= 0; i--) {
    						if(this.preferences.get(i).getStudentGroup().equals(this.group[j].name)) {
    							Student tmp = this.preferences.get(i);
    							for(int l = i; l < k; l++) {
    								this.preferences.set(l, this.preferences.get(l+1));;
    							}
    						this.preferences.set(k, tmp);
    						break;
    						}
    					}
    					groupCount[j]--;
    				}
    			}
    		}
    	}
    }

	/**
	 * Count the number of students in each group for students ranked k or better in
	 * the list of preference of school s
	 **/
	public int[] CountStudentsInGroup(School s, int k) {

		int[] countGroup = new int[s.group.length];
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < s.group.length; j++) {
				if (s.preferences.get(i).getStudentGroup().equals(s.group[j].name))
					countGroup[j]++;
			}
		}
		return countGroup;
	}
}