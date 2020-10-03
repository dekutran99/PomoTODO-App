package model;
import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Project;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

public class TestProject {

    private Project project;
    private Calendar cal = Calendar.getInstance();
    private Date deadline;
    private DueDate dueDate;

//    @Test
//    public void testConstructor() {
//        project = new Project("test");
//        assertEquals("test", project.getDescription());
//        assertEquals("[]", project.getTasks().toString());
//    }

//    @Test
//    public void testAddTaskAndToString() {
//        cal.set(Calendar.YEAR, 2018);
//        cal.set(Calendar.DATE, 3);
//        cal.set(Calendar.HOUR_OF_DAY, 21);
//        cal.set(Calendar.MINUTE, 33);
//        deadline = cal.getTime();
//        dueDate = new DueDate(deadline);
//
//        project = new Project("test");
//        Task task1 = new Task("cs");
//        project.add(task1);
//        task1.setDueDate(dueDate);
//
//        assertEquals("[\n" +
//                "{\n" +
//                "\tDescription: cs\n" +
//                "\tDue date: Sat Mar 03 2018 09:33 PM\n" +
//                "\tStatus: TODO\n" +
//                "\tPriority: DEFAULT\n" +
//                "\tTags:  \n" +
//                "}]", project.getTasks().toString());
//    }

//    @Test
//    public void testRemove() {
//        cal.set(Calendar.YEAR, 2018);
//        cal.set(Calendar.DATE, 3);
//        cal.set(Calendar.HOUR_OF_DAY, 21);
//        cal.set(Calendar.MINUTE, 33);
//        deadline = cal.getTime();
//        dueDate = new DueDate(deadline);
//
//        project = new Project("test");
//        Task task1 = new Task("cs");
//        Task task2 = new Task("math");
//        project.add(task1);
//        project.add(task2);
//        task1.setDueDate(dueDate);
//        task2.setDueDate(dueDate);
//
//        project.remove(task1);
//
//        assertEquals("[\n" +
//                "{\n" +
//                "\tDescription: math\n" +
//                "\tDue date: Sat Mar 03 2018 09:33 PM\n" +
//                "\tStatus: TODO\n" +
//                "\tPriority: DEFAULT\n" +
//                "\tTags:  \n" +
//                "}]", project.getTasks().toString());
//    }

    @Test
    public void testGetProgress() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        project.add(task2);
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.TODO);

        assertEquals(0, project.getProgress());


    }

    @Test
    public void testGetProgress2() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        Task task3 = new Task("chem");
        Task task4 = new Task("bio");
        project.add(task1);
        project.add(task2);
        project.add(task3);
        project.add(task4);
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.TODO);
        task3.setStatus(Status.IN_PROGRESS);
        task4.setStatus(Status.UP_NEXT);


        assertEquals(0, project.getProgress());


    }

    @Test
    public void testIsCompletedAllDone() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        project.add(task2);
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.DONE);
        assertEquals(false, project.isCompleted());
    }

    @Test
    public void testGetProgress3() {
        project = new Project("test");
        assertEquals(0, project.getProgress());
    }

    @Test
    public void testGetProgress4() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        project.add(task2);
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.DONE);

        assertEquals(0, project.getProgress());


    }

    @Test
    public void testGetNumberOfTasks() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        project.add(task2);
        assertEquals(2, project.getNumberOfTasks());
    }

    @Test
    public void testIsCompletedNotAllDone() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        project.add(task2);
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.UP_NEXT);
        assertEquals(false, project.isCompleted());
    }

    @Test
    public void testContains() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        project.add(task1);
        assertEquals(true, project.contains(task1));
    }

    @Test
    public void testProjectDoesntContain() {
        project = new Project("test");
        try {
            project.contains(null);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    public void testDoesNotContains() {
        project = new Project("test");
        Task task1 = new Task("cs");
        Task task2 = new Task("math");
        Task task3 = new Task("chem");
        project.add(task1);
        project.add(task2);
        assertEquals(false, project.contains(task3));
    }

    @Test
    public void testExceptionProject() {
        try {
            project = new Project(null);
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    public void testProjectsEqual() {
        project = new Project("test");
        assertEquals(true, project.equals(project));
    }

    @Test
    public void testProjectEqualsAndNotEquals() {
        project = new Project("test");
        Project sameObject = new Project("test");
        assertTrue(project.equals(sameObject));
        assertTrue(project.hashCode() == sameObject.hashCode());
        Project project1 = new Project("math");
        assertFalse(project.equals(project1));
    }

    @Test
    public void testProjectEqualsWithNotProject() {
        project = new Project("test");
        assertEquals(false, project.equals(new String()));
    }



    @Test
    public void testIterator() {
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Task task1 = new Task("task1");
        task1.setProgress(100);
        Task task2 = new Task("task2");
        task2.setProgress(50);
        Task task3 = new Task("task3");
        task3.setProgress(25);
        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        Task task4 = new Task("task4");
        task4.setProgress(0);
        project2.add(task4);
        Project project3 = new Project("Project3");
        Task task5 = new Task("task5");
        task5.setEstimatedTimeToComplete(30);
        project3.add(task5);
        project2.add(project3);
        project3.add(project1);
        task1.setEstimatedTimeToComplete(10);
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(8);
        task4.setEstimatedTimeToComplete(30);
    }

    @Test
    void testCreateDefaultIterator() {

        project = new Project("Tets");
        Task task1 = new Task("task1");
        task1.setPriority(new Priority(4));

        Task task2 = new Task("task2");
        task2.setPriority(new Priority(2));

        Task task3 = new Task("task3");
        task3.setPriority(new Priority(3));

        Task task4 = new Task("task4");
        task4.setPriority(new Priority(1));

        Task task5 = new Task("task5");
        task5.setPriority(new Priority(3));

        Project project2 = new Project("Project2");
        project2.setPriority(new Priority(1));

        Project project3 = new Project("Project3");
        project3.setPriority(new Priority(4));

        project.add(task2);
        project.add(task4);
        project.add(task1);
        project.add(task5);
        project.add(task3);
        project.add(project3);
        project.add(project2);

        project.sorted();

        Iterator i = project.iterator();
        System.out.println(i.hasNext());
    }

    @Test
    void testTimeToCompleteAndIsCompleted() {
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Task task1 = new Task("task1");
        task1.setProgress(100);
        task1.setPriority(new Priority(4));
        Task task2 = new Task("task2");
        task2.setPriority(new Priority(1));
        task2.setProgress(50);
        Task task3 = new Task("task3");
        task3.setPriority(new Priority(3));
        task3.setProgress(25);
        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        Task task4 = new Task("task4");
        task4.setProgress(0);
        task4.setPriority(new Priority(1));
        project2.add(task4);
        Project project3 = new Project("Project3");
        Task task5 = new Task("task5");
        task5.setEstimatedTimeToComplete(30);
        project3.add(task5);
        project3.setPriority(new Priority(4));
        project2.add(project3);
        project3.add(project1);
        task1.setEstimatedTimeToComplete(10);
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(8);
        task4.setEstimatedTimeToComplete(30);

        assertEquals(80, project2.getEstimatedTimeToComplete());
        assertEquals(50, project3.getEstimatedTimeToComplete());
        assertEquals(20, project1.getEstimatedTimeToComplete());

        Project project4 = new Project("test");
        project4.add(new Task("cs"));
        project4.add(new Task("math"));

        assertFalse(project4.isCompleted());

    }


    @Test
    void testGetEstimatedTimeToComplete() {
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Task task1 = new Task("task1");
        task1.setProgress(100);
        task1.setPriority(new Priority(4));
        Task task2 = new Task("task2");
        task2.setPriority(new Priority(1));
        task2.setProgress(50);
        Task task3 = new Task("task3");
        task3.setPriority(new Priority(3));
        task3.setProgress(25);
        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        Task task4 = new Task("task4");
        task4.setProgress(0);
        task4.setPriority(new Priority(1));
        project2.add(task4);
        Project project3 = new Project("Project3");
        Task task5 = new Task("task5");
        task5.setEstimatedTimeToComplete(30);
        project3.add(task5);
        project3.setPriority(new Priority(4));
        project2.add(project3);
        project3.add(project1);
        task1.setEstimatedTimeToComplete(10);
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(8);
        task4.setEstimatedTimeToComplete(30);

        assertEquals(80, project2.getEstimatedTimeToComplete());
    }

    @Test
    void testGetDescription() {
        project = new Project("test");
        assertEquals("test", project.getDescription());
    }

    @Test
    void testRemove() {
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Task task1 = new Task("task1");
        task1.setProgress(100);
        task1.setPriority(new Priority(4));
        Task task2 = new Task("task2");
        task2.setPriority(new Priority(1));
        task2.setProgress(50);
        Task task3 = new Task("task3");
        task3.setPriority(new Priority(3));
        task3.setProgress(25);
        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        Task task4 = new Task("task4");
        task4.setProgress(0);
        task4.setPriority(new Priority(1));
        project2.add(task4);
        Project project3 = new Project("Project3");
        Task task5 = new Task("task5");
        task5.setEstimatedTimeToComplete(30);
        project3.add(task5);
        project3.setPriority(new Priority(4));
        project2.add(project3);
        project3.add(project1);
        task1.setEstimatedTimeToComplete(10);
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(8);
        task4.setEstimatedTimeToComplete(30);

        project1.remove(task1);

        List<Todo> testList = new ArrayList<Todo>();
        testList.add(task2);
        testList.add(task3);
    }


}