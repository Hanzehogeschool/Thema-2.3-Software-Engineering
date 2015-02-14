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
package multiformat;

/**
 * A generic numbering base
 */
public abstract class Base {

  private String name;
  private int base;
  private String digits;

  static final int MAX_PRECISION = 10;  // max # of number after point
  double EPSILON;

  Base(String p_name, int p_base, String p_digits){
    name = p_name;
    base = p_base;
    digits = p_digits;
	EPSILON = Math.pow(base,-MAX_PRECISION);
  }

  public String getName() { return name; }
  public int getBase() { return base; }

  /**
   * Takes the input string and calculates the double value, using the current numberbase. 
   * @param number A String representation like "101.101" or "1A3.E56".
   * @return The decimal double value of the number. 
   */
  double parse(String number) {
    // decodes the sign
    double sign = 1.0;
    if(number.charAt(0) == '-'){
      sign = -1.0;
      number = number.substring(1).trim();
    }else if(number.charAt(0) == '+'){
      sign = 1.0;
      number = number.substring(1).trim();
    }

    // parses the integer part and the decimal part
    int power;
    int index = number.indexOf('.');
    if(index >= 0) {
      power = index-1;
    }
    else {
      power = number.length()-1;
    }

    double result = 0.0;
    double mult = Math.pow(base,power);
    // process the number. "101.101" is 4*1 + 2*0 + 1*1 + 1*0.5 + ...
    for(int i = 0; i < number.length(); i++)
      if(number.charAt(i)!='.'){
        result += mult * digits.indexOf(number.charAt(i));
        mult /= base;
      }
    return result * sign;
  }

  /**
   * Translate the number to the string representation in the current base.
   * I.e. 9 results in "1001".
   * @param number The double value.
   * @return The string representation.
   */
  String toString(double number) {
    if(number == 0.0) return "0";
    StringBuffer result=new StringBuffer();
    if(number<0){
      result.append('-');
      number = -number;
    }

    // Determine the number of positions needed to represent the number 
    // in the current numberbase. I.e. to represent 9 you need four positions ("1001").
    // The exact formula is discussed in class.
    int power = (int)Math.floor(Math.log(number+EPSILON/2)/Math.log(base));
    if(power<0) power=0;
    double divider = Math.pow(base,power);
    int num_digits=0;

    // This process is the opposite of the one used in the parse method.
    for(int i=power;  (number > EPSILON && num_digits < MAX_PRECISION) || i>=-1; --i){
      double cipher = Math.floor((number+EPSILON/2) / divider);
      if(divider < 1.0){
        num_digits++;
        if(num_digits==1){
          result.append('.');
        }
      }
      result.append(digits.charAt((int)cipher));
      number -= cipher * divider;
      divider /= base;
    }
    return result.toString();
  }
}

