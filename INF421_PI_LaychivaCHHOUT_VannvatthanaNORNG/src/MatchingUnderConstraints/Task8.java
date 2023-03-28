package MatchingUnderConstraints;

import java.util.*;

public class Task8 {
	public static int count = 0;
	public static int count1 = 0;

	public static void main(String[] args) {
		System.out.println("The Result of Matching is :");
		//instance1Matches();
		// int SimulationRound = 1;
		// for (int i = 0; i < SimulationRound; i++) {
		//instance2Matches(50);
		// }
		// System.out.println("The average value of student from group A who is matched to his first choice is " + (double) (count / SimulationRound));
		// System.out.println("The average value of student from group B who is matched to his first choice is " + (double) (count1 / SimulationRound));
		instance3Matches(50);

	}

	public static void instance1Matches() {
		List<Student> students = new ArrayList<Student>();
		List<School> schools = new ArrayList<School>();

		Student stud_1 = new Student("i1", "A");
		Student stud_2 = new Student("i2", "A");
		Student stud_3 = new Student("i3", "A");
		Student stud_4 = new Student("i4", "B");
		//Student stud_5 = new Student("i5", "B");
		//Student stud_6 = new Student("i6", "B");

		ArrayList<Student> list1 = new ArrayList<Student>();
		list1.add(stud_1);
		list1.add(stud_2);
		list1.add(stud_3);
		ArrayList<Student> list2 = new ArrayList<Student>();
		list2.add(stud_4);
		// list2.add(stud_5);
		// list2.add(stud_6);

		Group[] sgroup = new Group[] { new Group("A", list1, 3), new Group("B", list2, 1) };

		School s1 = new School("s1", 2, sgroup);
		School s2 = new School("s2", 2, sgroup);

		schools.add(s1);
		schools.add(s2);

		students.add(stud_1);
		students.add(stud_2);
		students.add(stud_3);
		students.add(stud_4);
		// students.add(stud_5);
		// students.add(stud_6);

		Student i1 = students.get(0);
		Student i2 = students.get(1);
		Student i3 = students.get(2);
		Student i4 = students.get(3);
		// Student i5 = students.get(4);
		// Student i6 = students.get(5);

		i1.insertLeastPreferredSchool(s1);
		i1.insertLeastPreferredSchool(s2);
		i2.insertLeastPreferredSchool(s2);
		i2.insertLeastPreferredSchool(s1);
		i3.insertLeastPreferredSchool(s1);
		i4.insertLeastPreferredSchool(s2);
		// i5.insertLeastPreferredSchool(s1);
		// i6.insertLeastPreferredSchool(s2);

		int[] s1Pref = new int[] { 3, 2, 1, 0 };
		int[] s2Pref = new int[] { 3, 2, 1, 0 };

		for (int i : s1Pref) {
			s1.insertLeastPreferredStudent(students.get(i));
		}
		for (int i : s2Pref) {
			s2.insertLeastPreferredStudent(students.get(i));
		}
		// System.out.println("Preference list of school before apply 4/5th algorithm
		// "+s1.preferences);
		// s1.FairRanking();
		// System.out.println("Preference list of school after apply 4/5th algorithm
		// "+s1.preferences);
		// s2.FairRanking();

		FixedPoint matchMaker = new FixedPoint(students, schools);
		matchMaker.Testing();

		// MatchMaker matchMaker = new MatchMaker(students, schools);
		// matchMaker.codaMakeMatches();

		// for(Student s : students){
		// System.out.println(s + " is matched with " + s.getMatch());
		// }
		// for(School s : schools){
		// System.out.println(s + " is matched with " + s.getMatches());
		// }
	}

	public static void instance2Matches(int numStudent) {
		List<Student> students = new ArrayList<Student>();
		List<School> schools = new ArrayList<School>();
		ArrayList<Student> studentsInGroupA = new ArrayList<Student>();
		ArrayList<Student> studentsInGroupB = new ArrayList<Student>();
		// ArrayList<Integer> NumOfStudent1 = new ArrayList<Integer>();
		// ArrayList<Integer> NumOfStudent2 = new ArrayList<Integer>();

		java.util.Random randomLatent = new java.util.Random();
		java.util.Random NoiseFirstSchool = new java.util.Random();
		java.util.Random NoiseSecondSchool = new java.util.Random();
		java.util.Random StudentPref = new java.util.Random();

		double[] W = new double[numStudent];
		double[] W_tilde1 = new double[numStudent];
		double[] W_tilde2 = new double[numStudent];

		for (int i = 0; i < numStudent; i++) {
			W[i] = randomLatent.nextGaussian();
			W_tilde1[i] = W[i] + NoiseFirstSchool.nextGaussian();
			W_tilde2[i] = W[i] + NoiseSecondSchool.nextGaussian();
		}

		/*
		 * Add student to student list and create a preference list of students
		 */
		int m = 9 * numStudent / 10;
		for (int j = 0; j < numStudent; j++) {
			if (j < m) {
				studentsInGroupA.add(new Student("i" + Integer.toString(j + 1), "A"));
				students.add(new Student("i" + Integer.toString(j + 1), "A"));
			} else {
				studentsInGroupB.add(new Student("i" + Integer.toString(j + 1), "B"));
				students.add(new Student("i" + Integer.toString(j + 1), "B"));
			}
		}
		/*
		 * Add school section
		 */
		// int quotaGroup = (int) (0.9 * numStudent / 4);
		Group[] sgroup = new Group[] { new Group("A", studentsInGroupA, studentsInGroupA.size()),
				new Group("B", studentsInGroupB, studentsInGroupA.size()) };
		School s1 = new School("s1", numStudent / 4, sgroup);
		School s2 = new School("s2", numStudent / 4, sgroup);

		schools.add(s1);
		schools.add(s2);

		// System.out.println(students);
		for (Student student : students) {
			if (StudentPref.nextDouble() >= 0.5) {
				student.insertLeastPreferredSchool(s1);
				student.insertLeastPreferredSchool(s2);
			} else {
				student.insertLeastPreferredSchool(s2);
				student.insertLeastPreferredSchool(s1);
			}
		}

		/*
		 * Create list preference of school
		 */

		int[] s1pr = MakeSchool(W_tilde1);
		for (int i : s1pr) {
			s1.insertLeastPreferredStudent(students.get(i - 1));
		}
		int[] s2pr = MakeSchool(W_tilde2);
		for (int i : s2pr) {
			s2.insertLeastPreferredStudent(students.get(i - 1));
		}
		FixedPoint matchMaker = new FixedPoint(students, schools);
		matchMaker.Testing();

		// for (School s : schools) {
		// System.out.println(s + " is matched with " + s.getMatches());
		// for (Student student : s.getMatches()) {
		// System.out.println(student.toString() + " has " + student.getPreference());
		// if (student.getPreference().get(0).toString().equals(s.toString())) {
		// if (student.getStudentGroup().equals(s.group[0].name)) {
		// count++;
		// } else {
		// count1++;
		// }
		// }
		// }
		// }
		// System.out.println(count);
		// System.out.println(count1);
	}

	private static int[] MakeSchool(double[] w_tilde) {
		int[] testing = new int[w_tilde.length];
		double[] sort = w_tilde.clone();
		Arrays.sort(sort);
		for (int i = 0; i < w_tilde.length; i++) {
			for (int j = 0; j < w_tilde.length; j++) {
				if (sort[i] == w_tilde[j]) {
					testing[i] = j + 1;
				}
			}
		}
		return testing;
	}

	public static void instance3Matches(int numStudent) {
		List<Student> students = new ArrayList<Student>();
		List<School> schools = new ArrayList<School>();
		ArrayList<Student> studentsInGroupA = new ArrayList<Student>();
		ArrayList<Student> studentsInGroupB = new ArrayList<Student>();
		ArrayList<Student> studentsInGroupC = new ArrayList<Student>();
		ArrayList<Student> studentsInGroupD = new ArrayList<Student>();

		java.util.Random randomLatent = new java.util.Random();
		java.util.Random NoiseFirstSchool = new java.util.Random();
		java.util.Random NoiseSecondSchool = new java.util.Random();
		java.util.Random StudentPref = new java.util.Random();

		double[] W = new double[numStudent];
		double[] W_tilde1 = new double[numStudent];
		double[] W_tilde2 = new double[numStudent];

		for (int i = 0; i < numStudent; i++) {
			W[i] = randomLatent.nextGaussian();
			W_tilde1[i] = W[i] + NoiseFirstSchool.nextGaussian();
			W_tilde2[i] = W[i] + NoiseSecondSchool.nextGaussian();
		}

		/*
		 * Add student to student list and create a preference list of students
		 */
		for (int j = 0; j < numStudent; j++) {
			if (j < 10 * numStudent / 20) {
				studentsInGroupA.add(new Student("i" + Integer.toString(j + 1), "A"));
				students.add(new Student("i" + Integer.toString(j + 1), "A"));
			} else if (j >= 10 * numStudent / 20 && j < 16 * numStudent / 20) {
				studentsInGroupB.add(new Student("i" + Integer.toString(j + 1), "B"));
				students.add(new Student("i" + Integer.toString(j + 1), "B"));
			} else if (j >= 16 * numStudent / 20 && j < 19 * numStudent / 20) {
				studentsInGroupC.add(new Student("i" + Integer.toString(j + 1), "C"));
				students.add(new Student("i" + Integer.toString(j + 1), "C"));
			} else {
				studentsInGroupD.add(new Student("i" + Integer.toString(j + 1), "D"));
				students.add(new Student("i" + Integer.toString(j + 1), "D"));
			}
		}
		// System.out.println(studentsInGroupA);
		// System.out.println(studentsInGroupB);
		// System.out.println(studentsInGroupC);
		// System.out.println(studentsInGroupD);

		/*
		 * Add school section
		 */
		Group[] sgroup = new Group[] { new Group("A", studentsInGroupA, studentsInGroupA.size()),
				new Group("B", studentsInGroupB, studentsInGroupB.size()),
				new Group("C", studentsInGroupC, studentsInGroupC.size()),
				new Group("D", studentsInGroupD, studentsInGroupD.size()) };
		School s1 = new School("s1", numStudent / 4, sgroup);
		School s2 = new School("s2", numStudent / 4, sgroup);

		schools.add(s1);
		schools.add(s2);

		// System.out.println(students);
		for (Student student : students) {
			if (StudentPref.nextDouble() >= 0.5) {
				student.insertLeastPreferredSchool(s1);
				student.insertLeastPreferredSchool(s2);
			} else {
				student.insertLeastPreferredSchool(s2);
				student.insertLeastPreferredSchool(s1);
			}
		}

		/*
		 * Create list preference of school
		 */

		int[] s1pr = MakeSchool(W_tilde1);
		for (int i : s1pr) {
			s1.insertLeastPreferredStudent(students.get(i - 1));
		}
		int[] s2pr = MakeSchool(W_tilde2);
		for (int i : s2pr) {
			s2.insertLeastPreferredStudent(students.get(i - 1));
		}
		FixedPoint matchMaker = new FixedPoint(students, schools);
		matchMaker.Testing();
	}
}
