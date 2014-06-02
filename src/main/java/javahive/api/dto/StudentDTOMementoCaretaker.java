package javahive.api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 29.04.14.
 */


public class StudentDTOMementoCaretaker{
    private List<StudentDTOMemento> mementoList = new ArrayList<StudentDTOMemento>();
    public void setMemento(StudentDTOMemento memento)
    {
    	for(StudentDTOMemento student: mementoList)
    	{
    		if(student.getNumerIndeksu().equals(memento.getNumerIndeksu()))
    		{
    			student.setId(memento.getId());
    			student.setImie(memento.getImie());
    			student.setNazwisko(memento.getNazwisko());
    			student.setNumerIndeksu(memento.getNumerIndeksu());
    			student.setWieczny(memento.isWieczny());
    			return;
    		}
    	}
    	mementoList.add(memento);
    }
    
    public StudentDTOMemento getMementoOfStudent(String indexNumber)
    {
    	for(StudentDTOMemento student: mementoList)
    	{
    		if(student.getNumerIndeksu().equals(indexNumber))
    		{
    			return student;
    		}
    	}
		return null;
    }

}
