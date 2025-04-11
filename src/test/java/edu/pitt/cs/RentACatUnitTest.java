package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		// INITIALIZE THE TEST FIXTURE

		// 1. Create a new RentACat object and assign to r using a call to
		// RentACat.createInstance(InstanceType).
		// Passing InstanceType.IMPL as the first parameter will create a real RentACat
		// object using your RentACatImpl implementation.
		// Passing InstanceType.MOCK as the first parameter will create a mock RentACat
		// object using Mockito.
		// Which type is the correct choice for this unit test? I'll leave it up to you.
		// The answer is in the Unit Testing Part 2 lecture. :)
		// TODO: Fill in
		r = RentACat.createInstance(InstanceType.IMPL);

		// 2. Create a Cat with ID 1 and name "Jennyanydots", assign to c1 using a call
		// to Cat.createInstance(InstanceType, int, String).
		// Passing InstanceType.IMPL as the first parameter will create a real cat using
		// your CatImpl implementation.
		// Passing InstanceType.MOCK as the first parameter will create a mock cat using
		// Mockito.
		// Which type is the correct choice for this unit test? Again, I'll leave it up
		// to you.
		// TODO: Fill in
		c1 = Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");

		// 3. Create a Cat with ID 2 and name "Old Deuteronomy", assign to c2 using a
		// call to Cat.createInstance(InstanceType, int, String).
		// TODO: Fill in
		c2 = Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");
		// 4. Create a Cat with ID 3 and name "Mistoffelees", assign to c3 using a call
		// to Cat.createInstance(InstanceType, int, String).
		// TODO: Fill in
		c3 = Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");
		// 5. Redirect system output from stdout to the "out" stream
		// First, make a back up of System.out (which is the stdout to the console)
		stdout = System.out;
		// Second, update System.out to the PrintStream created from "out"
		// TODO: Fill in. Refer to the textbook chapter 14.6 on Testing System Output.
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
	 * hapter on using reflection on how to do this. Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() throws Exception {
		// TODO: Fill in
		Method m = r.getClass().getDeclaredMethod("getCat", int.class);
		m.setAccessible(true);
		Object ret = m.invoke(r, 2);
		// check for null return value
		assertNull(ret);
		// check for output
		String expected = "Invalid cat ID." + newline;
		String actual = out.toString();
		assertEquals(expected, actual);

	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
	 * hapter on using reflection on how to do this. Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() throws Exception {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// execution
		try {
			Method m = r.getClass().getDeclaredMethod("getCat", int.class);
			m.setAccessible(true);
			Object ret = m.invoke(r, 2);
			// check return value
			assertNotNull(ret);
			// check id 2
			assertEquals(2, ((Cat) ret).getId());
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			System.err.println("Failure!");
		}

	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		// TODO: Fill in
		String ret = r.listCats();
		assertEquals("", ret);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// execution
		String ret = r.listCats();
		// check return value
		assertEquals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", ret);
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameFailureNumCats0() {
		// TODO: Fill in
		// execution
		boolean ret = r.renameCat(2, "Garfield");
		// check return value for false
		assertFalse(ret);
		// check name
		Mockito.verify(c2, times(0)).renameCat("Garfield");
		// check output
		String expected = "Invalid cat ID." + newline;
		String actual = out.toString();
		assertEquals(expected, actual);
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameNumCat3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// execution
		boolean ret = r.renameCat(2, "Garfield");
		// check return value
		assertTrue(ret);
		// check rename
		Mockito.verify(c2, times(1)).renameCat("Garfield");
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatNumCats3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// exeuction
		boolean ret = r.rentCat(2);
		// check return value
		assertTrue(ret);
		// check c2 is rented out
		Mockito.verify(c2, times(1)).rentCat();
		// check output
		String expected = "Old Deuteronomy has been rented." + newline;
		String actual = out.toString();
		assertEquals(expected, actual);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not rented as a result of the execution steps.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		r.rentCat(2);
		// execution steps
		Mockito.when(c2.getRented()).thenReturn(true);
		out.reset();
		// check return for false
		assertFalse(r.rentCat(2));
		// check output
		String expected = "Sorry, Old Deuteronomy is not here!" + newline;
		String actual = out.toString();
		assertEquals(expected, actual);
		// verify behavior
		Mockito.verify(c2).rentCat();

	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnCatNumCats3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		r.rentCat(2);
		// Execution steps
		Mockito.when(c2.getRented()).thenReturn(true);
		out.reset();
		// check return value
		assertTrue(r.returnCat(2));
		String expected = "Welcome back, Old Deuteronomy!" + newline;
		String actual = out.toString();
		assertEquals(expected, actual);
		Mockito.verify(c2).returnCat();

	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not returned as a result of the execution steps.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		// TODO: Fill in
		// preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// execution
		boolean ret = r.returnCat(2);
		// check return value
		assertFalse(ret);
		// check return
		Mockito.verify(c2, times(0)).returnCat();
		// check output
		String expected = "Old Deuteronomy is already here!" + newline;
		String actual = out.toString();
		assertEquals(expected, actual);
	}

}