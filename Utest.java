/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arwa
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import task2.DAOException;
import task2.DAOImpl;
import task2.Product;

@RunWith(MockitoJUnitRunner.class)
public class Utest {
    
        
        @Mock 
	Connection conn;
	@Mock
	PreparedStatement psmt;
	@InjectMocks
	DAOImpl newDAO = new DAOImpl();

        
        @Test
        public void testProductCon()
        {
	Product newp = new Product(1);
	assertEquals(1, newp.getId());
        }


	@Test
	public void testSetType()
	{	
	Product newp = new Product (5);
	newp.setType("medicine");
	Assert.assertTrue(newp.getType().equals("medicine"));
	}

	@Test
	public void testsetManufacturer()
	{	
	Product newp = new Product (23);
	newp.setManufacturer("medblue");
	Assert.assertTrue(newp.getManufacturer().equals("medblue"));
	}

	@Test
	public void testsetProductionDate()
	{	
	Product newp = new Product (77);
	newp.setProductionDate("2015-3-22");
	Assert.assertTrue(newp.getProductionDate().equals("2015-3-22"));
	}

	@Test
	public void testsetExpiryDate()
	{	
	Product newp = new Product (323);
	newp.setExpiryDate("2020-1-2");
	Assert.assertTrue(newp.getExpiryDate().equals("2020-1-2"));
	}
        
        
	@Test
        public void InserteHappyTest1() throws SQLException, DAOException
        {
	when(conn.prepareStatement(anyString())).thenReturn(psmt);
	when(psmt.executeUpdate()).thenReturn(1);
	Product p1 = new Product(123456789);
	newDAO.insertProduct(p1);
	}


        @Test
        public void InserteHappyTest2() throws SQLException, DAOException{
	when(conn.prepareStatement(anyString())).thenReturn(psmt);
	ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
	Product newp2 = new Product(111);
	newp2.setType("Asprine");
        newp2.setManufacturer("serenex");
        newp2.setProductionDate("2014-12-1");
        newp2.setExpiryDate("2019-12-22");
	newDAO.insertProduct(newp2);
	verify(psmt, times(1)).setInt(anyInt(), integerCaptor.capture());
        Assert.assertTrue(integerCaptor.getAllValues().get(0).equals(111));
        verify(psmt, times(4)).setString(anyInt(), stringCaptor.capture());
        Assert.assertTrue(stringCaptor.getAllValues().get(0).equals("Asprine"));
        Assert.assertTrue(stringCaptor.getAllValues().get(1).equals("serenex"));
        Assert.assertTrue(stringCaptor.getAllValues().get(2).equals("2014-12-1"));
        Assert.assertTrue(stringCaptor.getAllValues().get(3).equals("2019-12-22"));
        }
        
        
        @Test (expected = DAOException.class)
        public void ExceptionCase() throws SQLException, DAOException{
	when(conn.prepareStatement(anyString())).thenReturn(psmt);
	when(psmt.executeUpdate()).thenThrow(new SQLException());
	Product newp3 = new Product(41);
	newDAO.insertProduct(newp3);
        }
        
        @Test
        public void IntegrateTest() throws SQLException, DAOException {
        DAOImpl newDAOobj = new DAOImpl();
        Product newp = new Product(99);
	newp.setType("drug");
        newp.setManufacturer("zogenix");
        newp.setProductionDate("2016-11-25");
        newp.setExpiryDate("2020-12-31");
	newDAOobj.insertProduct(newp);
        Assert.assertNotNull(newDAOobj.getProduct(99));
       newDAOobj.deleteProduct(99);
       Assert.assertNull(newDAOobj.getProduct(99));
        
        }

}

