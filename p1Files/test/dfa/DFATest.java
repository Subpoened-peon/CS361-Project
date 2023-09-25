package test.dfa;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import fa.dfa.DFA;
/**
 * This class is the testing suite for the DFA class.
 * 
 * @author Nicholas Merritt
 * @author Kai Sorensen
 */
public class DFATest {
	
	
	//------------------- dfa1 tests ----------------------//
	private DFA dfa1() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));
		
		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("c"));
		assertFalse(dfa.setFinal("c"));
		
		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "a", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));
		
		assertFalse(dfa.addTransition("c", "b", '1'));
		assertFalse(dfa.addTransition("a", "c", '1'));
		assertFalse(dfa.addTransition("a", "b", '2'));
		
		return dfa;
	}
	
	@Test
	public void test1_1() {
		DFA dfa = dfa1();
		System.out.println("dfa1 instantiation pass");
	}

	@Test
	public void test1_2() {
		DFA dfa = dfa1();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(),"a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(),"b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa1 correctness pass");
	}
	
	@Test
	public void test1_3() {
		DFA dfa = dfa1();
		
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
		
		System.out.println("dfa1 accept pass");
	}
	
	@Test
	public void test1_4() {
		DFA dfa = dfa1();
		
		String dfaStr = dfa.toString();
		String expStr = " Q = { a b }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	a	b\n"
				+ "q0 = a\n"
				+ "F = { b }";
		
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		
		System.out.println("dfa1 toString pass");
	}
	
	
	
	@Test
	public void test1_5() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		
		//different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));
		
		//the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));
	
		System.out.println("dfa1Swap instantiation pass");
	}
	
	@Test
	public void test1_6() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("0"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("e"));
		
		System.out.println("dfa1Swap accept pass");
	}

//------------------- dfa2 tests ----------------------//
	private DFA dfa2() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');
		
		assertTrue(dfa.addState("3"));
		assertTrue(dfa.setFinal("3"));
		
		assertTrue(dfa.addState("0"));
		assertTrue(dfa.setStart("0"));
		
		assertTrue(dfa.addState("1"));
		assertTrue(dfa.addState("2"));
		
		
		assertFalse(dfa.setFinal("c"));
		assertFalse(dfa.setStart("a"));
		assertFalse(dfa.addState("2"));
		
		assertTrue(dfa.addTransition("0", "1", '0'));
		assertTrue(dfa.addTransition("0", "0", '1'));
		assertTrue(dfa.addTransition("1", "3", '0'));
		assertTrue(dfa.addTransition("1", "2", '1'));
		assertTrue(dfa.addTransition("2", "1", '0'));
		assertTrue(dfa.addTransition("2", "1", '1'));
		assertTrue(dfa.addTransition("3", "3", '0'));
		assertTrue(dfa.addTransition("3", "3", '1'));
		
		assertFalse(dfa.addTransition("3", "a", '1'));
		assertFalse(dfa.addTransition("c", "a", '1'));
		assertFalse(dfa.addTransition("3", "a", '2'));
		
		return dfa;
	}
	
	@Test
	public void test2_1() {
		DFA dfa = dfa2();
		System.out.println("dfa2 instantiation pass");
	}

	@Test
	public void test2_2() {
		DFA dfa = dfa2();
		assertNotNull(dfa.getState("0"));
		assertEquals(dfa.getState("1").getName(),"1");
		assertTrue(dfa.isStart("0"));
		assertNotNull(dfa.getState("3"));
		assertEquals(dfa.getState("3").getName(),"3");
		assertTrue(dfa.isFinal("3"));
		assertEquals(dfa.getSigma(), Set.of('0','1'));
		
		System.out.println("dfa2 correctness pass");
	}
	
	@Test
	public void test2_3() {
		DFA dfa = dfa2();
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
	
		System.out.println("dfa2 accept pass");
	}
	
	@Test
	public void test2_4() {
		DFA dfa = dfa2();
		
		String dfaStr = dfa.toString();
		String expStr = "Q={3 0 1 2}\n"
				+ "Sigma = {0 1}\n"
				+ "delta =\n"
				+ "	0	1\n"
				+ "3	3	3\n"
				+ "0	1	0\n"
				+ "1	3	2\n"
				+ "2	1	1\n"
				+ "q0 = 0\n"
				+ "F={3}\n";
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa2 toString pass");
	}
	
	
	
	@Test
	public void test2_5() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		//different DFA objects
		assertTrue(dfa != dfaSwap);
		//different DFA states
		assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
		assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
		assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
		assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
		assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));
		
		//ensure that the transitions of the original DFA don't change
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));
		
		System.out.println("dfa2Swap instantiation pass");
	}
	
	@Test
	public void test2_6() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("101"));
		assertTrue(dfaSwap.accepts("11"));
		assertFalse(dfaSwap.accepts("010"));
		assertTrue(dfaSwap.accepts("000100000000001"));
		assertFalse(dfaSwap.accepts("0001000000000101"));
		System.out.println("dfa2Swap accept pass");
	}	

//------------------- dfa3 tests ----------------------//
private DFA dfa3() {
	DFA dfa = new DFA();
	dfa.addSigma('2');
	dfa.addSigma('1');
	
	assertTrue(dfa.addState("G"));
	assertTrue(dfa.addState("D"));
	
	assertTrue(dfa.setFinal("G"));
	assertTrue(dfa.setFinal("D"));
	
	assertTrue(dfa.addState("A"));
	assertTrue(dfa.setStart("D"));
	assertTrue(dfa.setStart("A"));
	
	assertTrue(dfa.addState("B"));
	assertTrue(dfa.addState("C"));
	assertTrue(dfa.addState("E"));
	assertTrue(dfa.addState("F"));
	
	assertFalse(dfa.addState("A"));
	assertFalse(dfa.setFinal("K"));
	assertFalse(dfa.setStart("BK"));
	
	assertTrue(dfa.addTransition("A", "B", '1'));
	assertTrue(dfa.addTransition("A", "C", '2'));
	
	assertTrue(dfa.addTransition("B", "D", '1'));
	assertTrue(dfa.addTransition("B", "E", '2'));
	
	assertTrue(dfa.addTransition("C", "F", '1'));
	assertTrue(dfa.addTransition("C", "G", '2'));
	
	assertTrue(dfa.addTransition("C", "F", '1'));
	assertTrue(dfa.addTransition("C", "G", '2'));
	
	assertTrue(dfa.addTransition("D", "D", '1'));
	assertTrue(dfa.addTransition("D", "E", '2'));
	
	assertTrue(dfa.addTransition("E", "D", '1'));
	assertTrue(dfa.addTransition("E", "E", '2'));
	
	assertTrue(dfa.addTransition("F", "F", '1'));
	assertTrue(dfa.addTransition("F", "G", '2'));
	
	assertTrue(dfa.addTransition("G", "F", '1'));
	assertTrue(dfa.addTransition("G", "G", '2'));
	
	
	assertFalse(dfa.addTransition("FF", "F", '1'));
	assertFalse(dfa.addTransition("F", "GG", '2'));
	
	assertFalse(dfa.addTransition("G", "F", 'K'));
	assertFalse(dfa.addTransition("A", "K", '7'));
	
	return dfa;
}

@Test
public void test3_1() {
	DFA dfa = dfa3();
	
	System.out.println("dfa3 instantiation pass");
}

@Test
public void test3_2() {
	DFA dfa = dfa3();
	assertNotNull(dfa.getState("A"));
	assertNull(dfa.getState("K"));
	assertEquals(dfa.getState("C").getName(),"C");
	assertTrue(dfa.isStart("A"));
	assertFalse(dfa.isStart("D"));
	assertNotNull(dfa.getState("G"));
	assertEquals(dfa.getState("E").getName(),"E");
	assertTrue(dfa.isFinal("G"));
	assertFalse(dfa.isFinal("B"));
	assertEquals(dfa.getSigma(), Set.of('2','1'));

	System.out.println("dfa3 correctness pass");
}

@Test
public void test3_3() {
	DFA dfa = dfa3();
	assertTrue(dfa.accepts("121212121"));
	assertTrue(dfa.accepts("12221212121"));
	assertFalse(dfa.accepts("12"));
	assertFalse(dfa.accepts("2"));
	assertFalse(dfa.accepts("1212"));

	System.out.println("dfa3 accept pass");
}

@Test
public void test3_4() {
	DFA dfa = dfa3();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={GDABCEF}\n"
			+ "Sigma = {2 1}\n"
			+ "delta =\n"
			+ "	2	1\n"
			+ "G	G	F\n"
			+ "D	E	D\n"
			+ "A	C	B\n"
			+ "B	E	D\n"
			+ "C	G	F\n"
			+ "E	E	D\n"
			+ "F	G	F\n"
			+ "q0 = A\n"
			+ "F = {G D}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa3 toString pass");
}



@Test
public void test3_5() {
	DFA dfa = dfa3();
	DFA dfaSwap = dfa.swap('2', '1');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
	assertTrue(dfa.getState("G") != dfaSwap.getState("G"));
	assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
	assertEquals(dfa.isStart("D"), dfaSwap.isStart("D"));
	assertEquals(dfa.isFinal("A"), dfaSwap.isFinal("A"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts("121212121"));
	assertTrue(dfa.accepts("12221212121"));
	assertFalse(dfa.accepts("12"));
	assertFalse(dfa.accepts("2"));
	assertFalse(dfa.accepts("1212"));

	System.out.println("dfa3Swap instantiation pass");
}

@Test
public void test3_6() {
	DFA dfa = dfa3();
	DFA dfaSwap = dfa.swap('2', '1');
	assertTrue(dfaSwap.accepts("212121212"));
	assertTrue(dfaSwap.accepts("21112121212"));
	assertFalse(dfaSwap.accepts("21"));
	assertFalse(dfaSwap.accepts("1"));
	assertFalse(dfaSwap.accepts("2121"));
	
	System.out.println("dfa3Swap accept pass");
}

//------------------- dfa4 tests ----------------------//
private DFA dfa4() {
	DFA dfa = new DFA();
	dfa.addSigma('a');
	dfa.addSigma('b');
	dfa.addSigma('c');

	assertTrue(dfa.addState("q1"));
	assertTrue(dfa.addState("q2"));
	assertTrue(dfa.addState("q3"));

	assertTrue(dfa.setStart("q1"));
	assertTrue(dfa.setFinal("q1"));

	assertFalse(dfa.addState("q2"));
	assertFalse(dfa.setStart("qq"));
	assertFalse(dfa.setFinal("a"));

	assertTrue(dfa.addTransition("q1", "q2", 'a'));
	assertTrue(dfa.addTransition("q2", "q1", 'a'));
	assertTrue(dfa.addTransition("q2", "q2", 'b'));
	assertTrue(dfa.addTransition("q2", "q3", 'c'));
	assertTrue(dfa.addTransition("q3", "q2", 'b'));
	assertTrue(dfa.addTransition("q3", "q3", 'a'));
	
	assertFalse(dfa.addTransition("qq", "q2", 'c'));
	assertFalse(dfa.addTransition("q3", "q3", 'c'));
	assertFalse(dfa.addTransition("q1", "q1", 'a'));

	return dfa;
}

@Test
public void test4_1() {
	DFA dfa = dfa4();

	System.out.println("dfa4 instantiation pass");
}

@Test
public void test4_2() {
	DFA dfa = dfa4();
	assertNotNull(dfa.getState("q1"));
	assertNull(dfa.getState("q4"));
	assertEquals(dfa.getState("q1").getName(),"q1");
	assertTrue(dfa.isStart("q1"));
	assertFalse(dfa.isStart("q2"));
	assertNotNull(dfa.getState("q2"));
	assertEquals(dfa.getState("q2").getName(),"q2");
	assertTrue(dfa.isFinal("q1"));
	assertFalse(dfa.isFinal("q2"));
	assertNotNull(dfa.getState("q3"));
	assertEquals(dfa.getState("q3").getName(),"q3");
	assertEquals(dfa.getSigma(), Set.of('a','b', 'c'));


	System.out.println("dfa4 correctness pass");
}

@Test
public void test4_3() {
	DFA dfa = dfa4();
	assertTrue(dfa.accepts(""));
	assertTrue(dfa.accepts("aa"));
	assertTrue(dfa.accepts("acba"));
	assertTrue(dfa.accepts("aaaa"));
	assertTrue(dfa.accepts("aaaaabbbbbba"));
	assertTrue(dfa.accepts("aaabcaba"));

	assertFalse(dfa.accepts("a"));
	assertFalse(dfa.accepts("ab"));
	assertFalse(dfa.accepts("ac"));
	assertFalse(dfa.accepts("acc"));
	assertFalse(dfa.accepts("acbaabcc"));

	System.out.println("dfa4 accept pass");
}

@Test
public void test4_4() {
	DFA dfa = dfa4();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={q1q2q3}\n"
			+ "Sigma = {a b c}\n"
			+ "delta =\n"
			+ "		a	b	c\n"
			+ "q1	q2	qerr qerr\n"
			+ "q2	q1	q2	q3\n"
			+ "q3	q3	q2	qerr\n"
			+ "q0 = q1\n"
			+ "F = {q1}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa4 toString pass");
}

@Test
public void test4_5() {
	DFA dfa = dfa4();
	DFA dfaSwap = dfa.swap('a', 'c');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("q1") != dfaSwap.getState("q1"));
	assertTrue(dfa.getState("q2") != dfaSwap.getState("q2"));
	assertTrue(dfa.getState("q3") != dfaSwap.getState("q3"));
	assertEquals(dfa.isStart("q1"), dfaSwap.isStart("q1"));
	assertEquals(dfa.isFinal("q1"), dfaSwap.isFinal("q1"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts(""));
	assertTrue(dfa.accepts("aa"));
	assertTrue(dfa.accepts("acba"));
	assertTrue(dfa.accepts("aaaa"));
	assertTrue(dfa.accepts("aaaaabbbbbba"));
	assertTrue(dfa.accepts("aaabcaba"));

	assertFalse(dfa.accepts("a"));
	assertFalse(dfa.accepts("ab"));
	assertFalse(dfa.accepts("ac"));
	assertFalse(dfa.accepts("acc"));
	assertFalse(dfa.accepts("acbaabcc"));

	System.out.println("dfa4Swap instantiation pass");
}

@Test
public void test4_6() {
	DFA dfa = dfa4();
	DFA dfaSwap = dfa.swap('a', 'c');
	assertTrue(dfaSwap.accepts(""));
	assertTrue(dfaSwap.accepts("cc"));
	assertTrue(dfaSwap.accepts("cabc"));
	assertTrue(dfaSwap.accepts("cccc"));
	assertTrue(dfaSwap.accepts("cccccbbbbbbc"));
	assertTrue(dfaSwap.accepts("cccbacbc"));

	assertFalse(dfaSwap.accepts("c"));
	assertFalse(dfaSwap.accepts("cb"));
	assertFalse(dfaSwap.accepts("ca"));
	assertFalse(dfaSwap.accepts("caa"));
	assertFalse(dfaSwap.accepts("cabccbaa"));
	
	System.out.println("dfa4Swap accept pass");
}

//------------------- dfa5 tests ----------------------//
private DFA dfa5() {
	DFA dfa = new DFA();
	dfa.addSigma('g');
	dfa.addSigma('s');
	dfa.addSigma('r');

	assertTrue(dfa.addState("1"));
	assertTrue(dfa.addState("2"));
	assertTrue(dfa.addState("3"));
	assertTrue(dfa.addState("4"));
	assertTrue(dfa.addState("5"));

	assertTrue(dfa.setStart("1"));
	assertTrue(dfa.setFinal("3"));
	assertTrue(dfa.setFinal("5"));

	assertFalse(dfa.addState("1"));
	assertFalse(dfa.isFinal("6"));
	
	assertTrue(dfa.addTransition("1", "1", 's'));
	assertTrue(dfa.addTransition("1", "2", 'g'));
	assertTrue(dfa.addTransition("2", "1", 'r'));
	assertTrue(dfa.addTransition("2", "2", 's'));
	assertTrue(dfa.addTransition("2", "3", 'g'));
	assertTrue(dfa.addTransition("3", "3", 's'));
	assertTrue(dfa.addTransition("3", "4", 'g'));
	assertTrue(dfa.addTransition("3", "2", 'r'));
	assertTrue(dfa.addTransition("4", "4", 's'));
	assertTrue(dfa.addTransition("4", "5", 'g'));
	assertTrue(dfa.addTransition("4", "3", 'r'));
	assertTrue(dfa.addTransition("5", "4", 'r'));
	assertTrue(dfa.addTransition("5", "5", 's'));
	
	assertFalse(dfa.addTransition("5", "6", 'g'));
	assertFalse(dfa.addTransition("6", "5", 'g'));

	return dfa;
}

@Test
public void test5_1() {
	DFA dfa = dfa5();

	System.out.println("dfa5 instantiation pass");
}

@Test
public void test5_2() {
	DFA dfa = dfa5();
	assertNotNull(dfa.getState("1"));
	assertNotNull(dfa.getState("2"));
	assertNotNull(dfa.getState("3"));
	assertNotNull(dfa.getState("4"));
	assertNotNull(dfa.getState("5"));
	assertNull(dfa.getState("6"));
	assertEquals(dfa.getState("1").getName(),"1");
	assertEquals(dfa.getState("2").getName(),"2");
	assertEquals(dfa.getState("3").getName(),"3");
	assertEquals(dfa.getState("4").getName(),"4");
	assertEquals(dfa.getState("5").getName(),"5");
	assertTrue(dfa.isStart("1"));
	assertFalse(dfa.isStart("q2"));
	assertTrue(dfa.isFinal("3"));
	assertTrue(dfa.isFinal("5"));
	assertFalse(dfa.isFinal("6"));
	assertEquals(dfa.getSigma(), Set.of('s','g', 'r'));


	System.out.println("dfa5 correctness pass");
}

@Test
public void test5_3() {
	DFA dfa = dfa5();
	assertTrue(dfa.accepts("gg"));
	assertTrue(dfa.accepts("gggg"));
	assertTrue(dfa.accepts("gsg"));
	assertTrue(dfa.accepts("gsgsgsg"));
	assertTrue(dfa.accepts("grgrgg"));
	assertTrue(dfa.accepts("gggr"));
	assertTrue(dfa.accepts("ggssssssssssssrg"));

	assertFalse(dfa.accepts("r"));
	assertFalse(dfa.accepts("g"));
	assertFalse(dfa.accepts("ggr"));
	assertFalse(dfa.accepts("ssg"));
	assertFalse(dfa.accepts("ggggg"));

	System.out.println("dfa5 accept pass");
}

@Test
public void test5_4() {
	DFA dfa = dfa5();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={12345}\n"
			+ "Sigma = {g s r}\n"
			+ "delta =\n"
			+ "		g	s	r\n"
			+ "1	2	1 qerr\n"
			+ "2	3	2	1\n"
			+ "3	4	3	2\n"
			+ "4	5	4	3\n"
			+ "5	qerr	5	4\n"
			+ "q0 = 1\n"
			+ "F = {3 5}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa5 toString pass");
}

@Test
public void test5_5() {
DFA dfa = dfa5();
	DFA dfaSwap = dfa.swap('g', 'r');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
	assertTrue(dfa.getState("2") != dfaSwap.getState("2"));
	assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
	assertTrue(dfa.getState("4") != dfaSwap.getState("4"));
	assertTrue(dfa.getState("5") != dfaSwap.getState("5"));
	assertEquals(dfa.isStart("1"), dfaSwap.isStart("1"));
	assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));
	assertEquals(dfa.isFinal("5"), dfaSwap.isFinal("5"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts("gg"));
	assertTrue(dfa.accepts("gggg"));
	assertTrue(dfa.accepts("gsg"));
	assertTrue(dfa.accepts("gsgsgsg"));
	assertTrue(dfa.accepts("grgrgg"));
	assertTrue(dfa.accepts("gggr"));
	assertTrue(dfa.accepts("ggssssssssssssrg"));

	assertFalse(dfa.accepts("r"));
	assertFalse(dfa.accepts("g"));
	assertFalse(dfa.accepts("ggr"));
	assertFalse(dfa.accepts("ssg"));
	assertFalse(dfa.accepts("ggggg"));

	System.out.println("dfa5Swap instantiation pass");
}

@Test
public void test5_6() {
	DFA dfa = dfa5();
	DFA dfaSwap = dfa.swap('g', 'r');
	assertTrue(dfaSwap.accepts("rr"));
	assertTrue(dfaSwap.accepts("rgrr"));
	assertTrue(dfaSwap.accepts("rssssssrrr"));
	assertTrue(dfaSwap.accepts("rrrr"));
	assertTrue(dfaSwap.accepts("rgrgssrr"));
	assertTrue(dfaSwap.accepts("rrrrggssss"));

	assertFalse(dfaSwap.accepts("r"));
	assertFalse(dfaSwap.accepts("rg"));
	assertFalse(dfaSwap.accepts("g"));
	assertFalse(dfaSwap.accepts("ssgg"));
	assertFalse(dfaSwap.accepts("gggg"));
	
	System.out.println("dfa5Swap accept pass");
}

//------------------- dfa6 tests ----------------------//
private DFA dfa6() {
	DFA dfa = new DFA();

	dfa.addSigma('a');
	dfa.addSigma('b');

	assertTrue(dfa.addState("0"));
	assertTrue(dfa.addState("1"));
	assertTrue(dfa.addState("2"));
	assertTrue(dfa.addState("3"));
	assertTrue(dfa.addState("4"));
	assertTrue(dfa.addState("5"));

	assertTrue(dfa.setStart("0"));
	assertTrue(dfa.setFinal("3"));
	assertTrue(dfa.setFinal("4"));
	assertTrue(dfa.setFinal("5"));

	assertFalse(dfa.addState("4"));
	assertFalse(dfa.isFinal("1010"));
	
	assertTrue(dfa.addTransition("0", "0", 'b'));
	assertTrue(dfa.addTransition("0", "1", 'a'));
	assertTrue(dfa.addTransition("1", "1", 'a'));
	assertTrue(dfa.addTransition("1", "2", 'b'));
	assertTrue(dfa.addTransition("2", "0", 'b'));
	assertTrue(dfa.addTransition("2", "3", 'a'));
	assertTrue(dfa.addTransition("3", "3", 'a'));
	assertTrue(dfa.addTransition("3", "4", 'b'));
	assertTrue(dfa.addTransition("4", "5", 'b'));
	assertTrue(dfa.addTransition("4", "3", 'a'));
	assertTrue(dfa.addTransition("5", "5", 'b'));
	assertTrue(dfa.addTransition("5", "3", 'a'));
	
	
	assertFalse(dfa.addTransition("5", "6", 'a'));
	assertFalse(dfa.addTransition("6", "5", 'b'));
	assertFalse(dfa.addTransition("1010", "1010", 'a'));

	return dfa;
}

@Test
public void test6_1() {
	DFA dfa = dfa6();

	System.out.println("dfa6 instantiation pass");
}

@Test
public void test6_2() {
	DFA dfa = dfa6();
	assertNotNull(dfa.getState("0"));
	assertNotNull(dfa.getState("1"));
	assertNotNull(dfa.getState("2"));
	assertNotNull(dfa.getState("3"));
	assertNotNull(dfa.getState("4"));
	assertNotNull(dfa.getState("5"));
	assertNull(dfa.getState("6"));
	assertEquals(dfa.getState("0").getName(),"0");
	assertEquals(dfa.getState("1").getName(),"1");
	assertEquals(dfa.getState("2").getName(),"2");
	assertEquals(dfa.getState("3").getName(),"3");
	assertEquals(dfa.getState("4").getName(),"4");
	assertEquals(dfa.getState("5").getName(),"5");
	assertTrue(dfa.isStart("0"));
	assertFalse(dfa.isStart("q2"));
	assertTrue(dfa.isFinal("3"));
	assertTrue(dfa.isFinal("4"));
	assertTrue(dfa.isFinal("5"));
	assertFalse(dfa.isFinal("6"));
	assertEquals(dfa.getSigma(), Set.of('a','b'));


	System.out.println("dfa6 correctness pass");
}

@Test
public void test6_3() {
	DFA dfa = dfa6();
	assertTrue(dfa.accepts("aba"));
	assertTrue(dfa.accepts("abab"));
	assertTrue(dfa.accepts("ababb"));
	assertTrue(dfa.accepts("ababba"));
	assertTrue(dfa.accepts("aaaaaaba"));
	assertTrue(dfa.accepts("bbbbbbbbaba"));
	assertTrue(dfa.accepts("bbbaaabaaab"));
	assertTrue(dfa.accepts("bbbaaabaabbabbabbbbbba"));

	assertFalse(dfa.accepts(""));
	assertFalse(dfa.accepts("abb"));
	assertFalse(dfa.accepts("abbba"));
	assertFalse(dfa.accepts("a"));
	assertFalse(dfa.accepts("bab"));

	System.out.println("dfa6 accept pass");
}

@Test
public void test6_4() {
	DFA dfa = dfa6();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={012345}\n"
			+ "Sigma = {a b}\n"
			+ "delta =\n"
			+ "		a	b\n"
			+ "0	1	0\n"
			+ "1	a	b\n"
			+ "2	0	3\n"
			+ "3	3	4\n"
			+ "4	3	5\n"
			+ "5	3	5\n"
			+ "q0 = 0\n"
			+ "F = {3 4 5}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa6 toString pass");
}

@Test
public void test6_5() {
	DFA dfa = dfa6();
	DFA dfaSwap = dfa.swap('a', 'b');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
	assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
	assertTrue(dfa.getState("2") != dfaSwap.getState("2"));
	assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
	assertTrue(dfa.getState("4") != dfaSwap.getState("4"));
	assertTrue(dfa.getState("5") != dfaSwap.getState("5"));
	assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
	assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));
	assertEquals(dfa.isFinal("4"), dfaSwap.isFinal("4"));
	assertEquals(dfa.isFinal("5"), dfaSwap.isFinal("5"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts("aba"));
	assertTrue(dfa.accepts("abab"));
	assertTrue(dfa.accepts("ababb"));
	assertTrue(dfa.accepts("ababba"));
	assertTrue(dfa.accepts("aaaaaaba"));
	assertTrue(dfa.accepts("bbbbbbbbaba"));
	assertTrue(dfa.accepts("bbbaaabaaab"));
	assertTrue(dfa.accepts("bbbaaabaabbabbabbbbbba"));

	assertFalse(dfa.accepts(""));
	assertFalse(dfa.accepts("abb"));
	assertFalse(dfa.accepts("abbba"));
	assertFalse(dfa.accepts("a"));
	assertFalse(dfa.accepts("bab"));

	System.out.println("dfa6Swap instantiation pass");
}


@Test
public void test6_6() {
	DFA dfa = dfa6();
	DFA dfaSwap = dfa.swap('a', 'b');
	assertTrue(dfaSwap.accepts("bab"));
	assertTrue(dfaSwap.accepts("baba"));
	assertTrue(dfaSwap.accepts("aaabbbab"));
	assertTrue(dfaSwap.accepts("babaa"));
	assertTrue(dfaSwap.accepts("babaaaaaaa"));
	assertTrue(dfaSwap.accepts("babaaaaaaabbbaa"));

	assertFalse(dfaSwap.accepts("aba"));
	assertFalse(dfaSwap.accepts("aaaaabbbbbaa"));
	assertFalse(dfaSwap.accepts(""));
	assertFalse(dfaSwap.accepts("ababa"));
	assertFalse(dfaSwap.accepts("aabbbbbb"));
	
	System.out.println("dfa6Swap accept pass");
}

//------------------- dfa7 tests ----------------------//
private DFA dfa7() {
	DFA dfa = new DFA();

	dfa.addSigma('1');
	dfa.addSigma('2');
	dfa.addSigma('3');

	assertTrue(dfa.addState("A"));
	assertTrue(dfa.addState("B"));
	assertTrue(dfa.addState("C"));
	assertTrue(dfa.addState("D"));
	assertTrue(dfa.addState("E"));
	assertTrue(dfa.addState("F"));

	assertTrue(dfa.setStart("A"));
	assertTrue(dfa.setFinal("D"));
	assertTrue(dfa.setFinal("F"));
	

	assertFalse(dfa.addState("A"));
	assertFalse(dfa.isFinal("Final"));
	
	assertTrue(dfa.addTransition("A", "B", '1'));
	assertTrue(dfa.addTransition("A", "E", '2'));
	assertTrue(dfa.addTransition("A", "D", '3'));
	assertTrue(dfa.addTransition("B", "A", '1'));
	assertTrue(dfa.addTransition("B", "C", '2'));
	assertTrue(dfa.addTransition("B", "F", '3'));
	assertTrue(dfa.addTransition("C", "F", '1'));
	assertTrue(dfa.addTransition("C", "B", '2'));
	assertTrue(dfa.addTransition("C", "D", '3'));
	assertTrue(dfa.addTransition("D", "B", '1'));
	assertTrue(dfa.addTransition("D", "E", '2'));
	assertTrue(dfa.addTransition("D", "C", '3'));
	assertTrue(dfa.addTransition("E", "E", '1'));
	assertTrue(dfa.addTransition("E", "F", '2'));
	assertTrue(dfa.addTransition("E", "D", '3'));
	assertTrue(dfa.addTransition("F", "E", '1'));
	assertTrue(dfa.addTransition("F", "A", '2'));
	assertTrue(dfa.addTransition("F", "C", '3'));
	
	assertFalse(dfa.addTransition("F", "F", '4'));
	assertFalse(dfa.addTransition("A", "DD", '1'));
	assertFalse(dfa.addTransition("1010", "1010", 'a'));
	return dfa;
}

@Test
public void test7_1() {
	DFA dfa = dfa7();

	System.out.println("dfa7 instantiation pass");
}

@Test
public void test7_2() {
	DFA dfa = dfa6();
	assertNotNull(dfa.getState("A"));
	assertNotNull(dfa.getState("B"));
	assertNotNull(dfa.getState("C"));
	assertNotNull(dfa.getState("D"));
	assertNotNull(dfa.getState("E"));
	assertNotNull(dfa.getState("F"));
	assertNull(dfa.getState("G"));
	assertNull(dfa.getState("Z"));
	assertEquals(dfa.getState("A").getName(),"A");
	assertEquals(dfa.getState("B").getName(),"B");
	assertEquals(dfa.getState("C").getName(),"C");
	assertEquals(dfa.getState("D").getName(),"D");
	assertEquals(dfa.getState("E").getName(),"E");
	assertEquals(dfa.getState("F").getName(),"F");
	assertTrue(dfa.isStart("A"));
	assertFalse(dfa.isStart("q2"));
	assertTrue(dfa.isFinal("D"));
	assertTrue(dfa.isFinal("F"));
	assertFalse(dfa.isFinal("B"));
	assertFalse(dfa.isFinal("E"));
	assertEquals(dfa.getSigma(), Set.of('1','2', '3'));


	System.out.println("dfa7 correctness pass");
}

@Test
public void test7_3() {
	DFA dfa = dfa7();
	assertTrue(dfa.accepts("3"));
	assertTrue(dfa.accepts("13"));
	assertTrue(dfa.accepts("121"));
	assertTrue(dfa.accepts("123"));
	assertTrue(dfa.accepts("12322"));
	assertTrue(dfa.accepts("113"));
	assertTrue(dfa.accepts("1122"));
	assertTrue(dfa.accepts("1112221211113223113"));

	assertFalse(dfa.accepts(""));
	assertFalse(dfa.accepts("abb"));
	assertFalse(dfa.accepts("321111"));
	assertFalse(dfa.accepts("11122232"));
	assertFalse(dfa.accepts("111231"));

	System.out.println("dfa7 accept pass");
}

@Test
public void test7_4() {
	DFA dfa = dfa7();
	
	String dfaStr = dfa.toString();
	String expStr = "Q={ABCDEF}\n"
			+ "Sigma = {1 2 3}\n"
			+ "delta =\n"
			+ "		1	2	3\n"
			+ "A	B	E	D\n"
			+ "B	A	C	F\n"
			+ "C	F	B	D\n"
			+ "D	B	E	C\n"
			+ "E	E	F	D\n"
			+ "F	E	A	C\n"
			+ "q0 = A\n"
			+ "F = {D F}\n";
	
	assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
	System.out.println("dfa7 toString pass");
}

@Test
public void test7_5() {
	DFA dfa = dfa7();
	DFA dfaSwap = dfa.swap('1', '3');
	assertTrue(dfa != dfaSwap);
	assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
	assertTrue(dfa.getState("B") != dfaSwap.getState("B"));
	assertTrue(dfa.getState("C") != dfaSwap.getState("C"));
	assertTrue(dfa.getState("D") != dfaSwap.getState("D"));
	assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
	assertTrue(dfa.getState("F") != dfaSwap.getState("F"));
	assertEquals(dfa.isStart("A"), dfaSwap.isStart("A"));
	assertEquals(dfa.isFinal("D"), dfaSwap.isFinal("D"));
	assertEquals(dfa.isFinal("F"), dfaSwap.isFinal("F"));
	assertEquals(!dfa.isFinal("A"), !dfaSwap.isFinal("A"));
	
	//transitions of the original dfa should not change
	assertTrue(dfa.accepts("3"));
	assertTrue(dfa.accepts("13"));
	assertTrue(dfa.accepts("121"));
	assertTrue(dfa.accepts("123"));
	assertTrue(dfa.accepts("12322"));
	assertTrue(dfa.accepts("113"));
	assertTrue(dfa.accepts("1122"));
	assertTrue(dfa.accepts("1112221211113223113"));

	assertFalse(dfa.accepts(""));
	assertFalse(dfa.accepts("abb"));
	assertFalse(dfa.accepts("321111"));
	assertFalse(dfa.accepts("11122232"));
	assertFalse(dfa.accepts("111231"));

	System.out.println("dfa7Swap instantiation pass");
}

@Test
public void test7_6() {
	DFA dfa = dfa7();
	DFA dfaSwap = dfa.swap('1', '3');
	assertTrue(dfaSwap.accepts("1"));
	assertTrue(dfaSwap.accepts("321"));
	assertTrue(dfaSwap.accepts("321232"));
	assertTrue(dfaSwap.accepts("122"));
	assertTrue(dfaSwap.accepts("323"));
	assertTrue(dfaSwap.accepts("3223"));

	assertFalse(dfaSwap.accepts("123"));
	assertFalse(dfaSwap.accepts("3"));
	assertFalse(dfaSwap.accepts(""));
	assertFalse(dfaSwap.accepts("121"));
	assertFalse(dfaSwap.accepts("113"));
	
	System.out.println("dfa7Swap accept pass");
}
}
