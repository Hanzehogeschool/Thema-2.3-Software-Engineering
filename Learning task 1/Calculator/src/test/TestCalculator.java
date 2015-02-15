/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package test;
import junit.framework.TestCase;
import multiformat.*;


public class TestCalculator extends TestCase {

	public TestCalculator(String arg0) {
		super(arg0);
	}

	public void testOperations(){
	
		Calculator calc = new Calculator();
		
		try{
		calc.addOperand("3.2");
		assertEquals("0.0",calc.firstOperand());
		assertEquals("3.2",calc.secondOperand());

		calc.addOperand("2.8");
		assertEquals("3.2",calc.firstOperand());
		assertEquals("2.8",calc.secondOperand());
		
		calc.add();
		assertEquals("0.0",calc.firstOperand());
		assertEquals("6.0",calc.secondOperand());
		}catch(FormatException e){
			fail("Unexpected format exception");
		}
	}
	
	
}
