package classes_for_db;

import java.math.BigInteger;
import java.util.List;

// author: ronfarizon

public class Demographics implements DbTableObject {

	// 15 Major fields noted
	
	private int zip; //MAJOR    
	private int pop; //MAJOR  
	
	// Race & Age
	private double pct_female;   
	private double median_age;  //MAJOR
	private int pop_18;      
	private double pct_female_pop_18;
	private int pop_65;
	private double pct_female_pop_65;
	private int white_pop;  //MAJOR    
	private double pct_white_pop;    
	private int black_pop; //MAJOR	
	private double pct_black_pop;
	private int native_pop; 	
	private double pct_native_pop;	
	private int asian_pop; //MAJOR	
	private double pct_asian_pop;	
	private int pac_islander_pop;  	
	private double pct_pac_islander_pop;	
	private int other_race_pop;	
	private double pct_other_race_pop;	
	private int hispanic_pop;  //MAJOR
	private double pct_hispanic_pop;	
	
	// Education
	// Note: all education stats for those 25+ years old
	private int pop_18_24;
	private int pop_25;   //MAJOR
	private double pct_less_than_hs;	
	private double pct_some_hs;		
	private double pct_hs_grads;	
	private double pct_some_col;	
	private double pct_ass_degree;	
	private double pct_bach_degree;	
	private double pct_grad_degree;	
	private double pct_hs_or_more;  //MAJOR
	private double pct_bach_or_more;	
	private int median_earnings;    //MAJOR	
	private int median_earnings_bach;   //MAJOR
	private int median_earnings_grad;
	
	// Housing
	private int num_households;   //MAJOR	
	private int fam_households;   
	private double pct_households_fam;  //MAJOR	
	private double avg_fam_size;
	private int num_fam_u18_child;	
	private double pct_household_same_sex;
	private double pct_household_opp_sex;
	private double pct_one_unit_struct;
	private double pct_two_plus_unit_struct;	
	private double pct_units_owner_occ;   //MAJOR
	private double pct_units_renter_occ;  //MAJOR 
	
	public Demographics() {}
	
	public int getZipCode() {
		return zip;
	}
	/**
	 * 
	 * @param zipCode
	 * @throws IllegalArgumentException if {@code zipCode} does not fall within the acceptable range
	 *         of 0 <= zipCode <= 99999.
	 */
	public void setZipCode(int zipCode) {
		if (zipCode < 0 | zipCode > 99999) {
			throw new IllegalArgumentException(
					"Argument for zipCode does not fall withing the acceptable range of 0 <= zipCode <= 99999. Argument given: "
							+ zipCode);
		} else {
			this.zip = zipCode;
		}
	}
	
	public int getPop() {
		return pop;
	}
	
	public void setPop(int pop) {
		if(pop < 0) {
			throw new IllegalArgumentException ("Argument for population must be positive. Argument given: " + pop);
		}
		else {
			this.pop = pop;
		}
	}
	
	public double getMedianAge() {
		return median_age;
	}
	
	public void setMedianAge(double med) {
		if(med < 0) {
			throw new IllegalArgumentException ("Argument for median age must be positive. Argument given: " + med);
		}
		else {
			this.median_age = med;
		}
	}

	public double getPctFemale() {
		return pct_female;
	}
	
	public void setPctFemale(double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException ("Argument for pct female must be between 0 and 100. Argument given: " + pct);
		}
		else {
			this.pct_female = pct;
		}
	}
	
	public int getPopOver(int age) {
		if(age == 18) {
			return pop_18;
		}
		else if(age == 65) {
			return pop_65;
		}
		else if(age == 25) {
			return pop_25;
		}
		else {
			throw new IllegalArgumentException("Data available only for population over 18, 25, and 65 years of age");
		}
	}
	
	public void setPopOver(int age, int pop) {
		if(pop < 0) {
			throw new IllegalArgumentException("Population must be positive");
		}
		else if(pop > this.pop) {
			throw new IllegalArgumentException("Population of age segment can't be greater than overall population");
		}
		else if(age == 18) {
			this.pop_18 = pop;
		}
		else if(age == 25) {
			this.pop_25 = pop;
		}
		else if(age == 65) {
			this.pop_65 = pop;
		}
		else {
			throw new IllegalArgumentException("Data available only for population over 18, 25, and 65 years of age");
		}
	}
	
	public double getPctFemaleOver(int age) {
		if(age == 18) {
			return pct_female_pop_18;
		}
		else if(age == 65) {
			return pct_female_pop_65;
		}
		else {
			throw new IllegalArgumentException("% Female data is available only for populations over 18 and 65 "
					+ "years of age");
		}
	}
	
	public void setPctFemaleOver(int age, double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else if(age == 18) {
			this.pct_female_pop_18 = pct;
		}
		else if(age == 25) {
			this.pct_female_pop_65 = pct;
		}
		else {
			throw new IllegalArgumentException("% Female data is available only for populations over 18 and 65 "
					+ "years of age");
		}
	}
	
	public int getRacePop(String race) {
		if(race.toLowerCase().equals("white")) {
			return white_pop;
		}
		else if(race.toLowerCase().equals("black")) {
			return black_pop;
		}
		else if(race.toLowerCase().equals("asian")) {
			return asian_pop;
		}
		else if(race.toLowerCase().equals("native") || race.toLowerCase().equals("native american")) {
			return native_pop;
		}
		else if(race.toLowerCase().equals("pacific islander") || race.toLowerCase().equals("islander")) {
			return pac_islander_pop;
		}
		else if(race.toLowerCase().equals("other") || race.toLowerCase().equals("other race")) {
			return other_race_pop;
		}
		else {
			throw new IllegalArgumentException("Expected races: white, black, asian, islander, native, or other. Given: " + race);
		}
	}
	
	public void setRacePop(String race, int pop) {
		if(pop < 0) {
			throw new IllegalArgumentException("Population must be positive");
		}
		else if(pop > this.pop) {
			throw new IllegalArgumentException("Population of race segment can't be greater than overall population");
		}
		else if(race.toLowerCase().equals("white")) {
			this.white_pop = pop;
		}
		else if(race.toLowerCase().equals("black")) {
			this.black_pop = pop;
		}
		else if(race.toLowerCase().equals("asian")) {
			this.asian_pop = pop;
		}
		else if(race.toLowerCase().equals("native") || race.toLowerCase().equals("native american")) {
			this.native_pop = pop;
		}
		else if(race.toLowerCase().equals("pacific islander") || race.toLowerCase().equals("islander")) {
			this.pac_islander_pop = pop;
		}
		else if(race.toLowerCase().equals("other") || race.toLowerCase().equals("other race")) {
			this.other_race_pop = pop;
		}
		else {
			throw new IllegalArgumentException("Expected races: white, black, asian, islander, native, or other. Given: " + race);
		}	
	}
	
	public double getRacePctPop(String race) {
		if(race.toLowerCase().equals("white")) {
			return pct_white_pop;
		}
		else if(race.toLowerCase().equals("black")) {
			return pct_black_pop;
		}
		else if(race.toLowerCase().equals("asian")) {
			return pct_asian_pop;
		}
		else if(race.toLowerCase().equals("native") || race.toLowerCase().equals("native american")) {
			return pct_native_pop;
		}
		else if(race.toLowerCase().equals("pacific islander") || race.toLowerCase().equals("islander")) {
			return pct_pac_islander_pop;
		}
		else if(race.toLowerCase().equals("other") || race.toLowerCase().equals("other race")) {
			return pct_other_race_pop;
		}
		else {
			throw new IllegalArgumentException("Expected races: white, black, asian, islander, native, or other. Given: " + race);
		}
	}
	
	public void setPctRacePop(String race, double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else if(race.toLowerCase().equals("white")) {
			this.pct_white_pop = pct;
		}
		else if(race.toLowerCase().equals("black")) {
			this.pct_black_pop = pct;
		}
		else if(race.toLowerCase().equals("asian")) {
			this.pct_asian_pop = pct;
		}
		else if(race.toLowerCase().equals("native") || race.toLowerCase().equals("native american")) {
			this.pct_native_pop = pct;
		}
		else if(race.toLowerCase().equals("pacific islander") || race.toLowerCase().equals("islander")) {
			this.pct_pac_islander_pop = pct;
		}
		else if(race.toLowerCase().equals("other") || race.toLowerCase().equals("other race")) {
			this.pct_other_race_pop = pct;
		}
		else {
			throw new IllegalArgumentException("Expected races: white, black, asian, islander, native, or other. Given: " + race);
		}	
	}
	
	public int getHispanicPop() {
		return hispanic_pop;
	}
	
	public void setHispanicPop(int pop) {
		if(pop < 0) {
			throw new IllegalArgumentException("Population must be positive");
		}
		else if(pop > this.pop) {
			throw new IllegalArgumentException("Population of race segment can't be greater than overall population");
		}
		else {
			this.hispanic_pop = pop;
		}
	}
	
	public double getHispanicPctPop() {
		return pct_hispanic_pop;
	}
	
	public void setHispanicPctPop(double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else {
			this.pct_hispanic_pop = pct;
		}
	}
	
	public int getPop1824() {
		return pop_18_24;
	}
	
	public void setPop1824(int pop) {
		if(pop < 0) {
			throw new IllegalArgumentException("Population must be positive");
		}
		else if(pop > this.pop) {
			throw new IllegalArgumentException("Population of age segment can't be greater than overall population");
		}
		else {
			this.pop_18_24 = pop;
		}
	}
	
	public double getPctEdu(String edu) {
		if(edu.toLowerCase().equals("less hs") || edu.toLowerCase().equals("less than hs")) {
			return pct_less_than_hs;
		}
		else if(edu.toLowerCase().equals("some hs")) {
			return pct_some_hs;
		}
		else if(edu.toLowerCase().equals("hs grads") || edu.toLowerCase().equals("hs grad")) {
			return pct_hs_grads;
		}
		else if(edu.toLowerCase().equals("some college") || edu.toLowerCase().equals("some col")) {
			return pct_some_col;
		}
		else if(edu.toLowerCase().equals("associate's degree") || edu.toLowerCase().equals("associate degree") ||
							edu.toLowerCase().equals("associate's") || edu.toLowerCase().equals("associates degree")) {
			return pct_ass_degree;
			
		}
		else if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
							edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
			return pct_bach_degree;
		}
		else if(edu.toLowerCase().equals("grad degree") || edu.toLowerCase().equals("graduate's degree") ||
				edu.toLowerCase().equals("graduate's") || edu.toLowerCase().equals("graduate degree")) {
			return pct_grad_degree;
		}
		else {
			throw new IllegalArgumentException("Expected education levels: less than hs, hs grad, some college, "
					+ "associate's degree, bachelor's degree, graduate degree");
		}
	}
		
		public void setPctEdu(String edu, double pct) {
			if(pct < 0 || pct > 100) {
				throw new IllegalArgumentException("Percentage must be between 0 and 100");
			}
			else if(edu.toLowerCase().equals("less hs") || edu.toLowerCase().equals("less than hs")) {
				this.pct_less_than_hs = pct;
			}
			else if(edu.toLowerCase().equals("some hs")) {
				this.pct_some_hs = pct;
			}
			else if(edu.toLowerCase().equals("hs grads") || edu.toLowerCase().equals("hs grad")) {
				this.pct_hs_grads = pct;
			}
			else if(edu.toLowerCase().equals("some college") || edu.toLowerCase().equals("some col")) {
				this.pct_some_col = pct;
			}
			else if(edu.toLowerCase().equals("associate's degree") || edu.toLowerCase().equals("associate degree") ||
								edu.toLowerCase().equals("associate's") || edu.toLowerCase().equals("associates degree")) {
				this.pct_ass_degree = pct;
			}
			else if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
								edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
				this.pct_bach_degree = pct;
			}
			else if(edu.toLowerCase().equals("grad degree") || edu.toLowerCase().equals("graduate's degree") ||
					edu.toLowerCase().equals("graduate's") || edu.toLowerCase().equals("graduate degree")) {
				this.pct_grad_degree = pct;
			}
			else {
				throw new IllegalArgumentException("Expected education levels: less than hs, hs grad, some college, "
						+ "associate's degree, bachelor's degree, graduate degree");
			}
		}
		
	public double getPctEduOrMore(String edu) {
		if(edu.toLowerCase().equals("hs grads") || edu.toLowerCase().equals("hs grad") ||
				edu.toLowerCase().equals("high school") || edu.toLowerCase().equals("hs")) {
			return pct_hs_or_more;
		}
		else if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
							edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
			return pct_bach_or_more;
		}
		else {
			throw new IllegalArgumentException("Expected education levels: high school, bachelor's degree");
		}
	}
	
	public void setPctEduOrMore(String edu, double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else if(edu.toLowerCase().equals("hs grads") || edu.toLowerCase().equals("hs grad") ||
				edu.toLowerCase().equals("high school") || edu.toLowerCase().equals("hs")) {
			this.pct_hs_or_more = pct;
		}
		else if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
							edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
			this.pct_bach_or_more = pct;
		}
		else {
			throw new IllegalArgumentException("Expected education levels: high school, bachelor's degree");
		}
	}
	
	public int getMedianEarnings() {
		return median_earnings;
	}
	
	public void setMedianEarnings(int earn) {
		if(earn < 0) {
			throw new IllegalArgumentException("Earnings can't be negative");
		}
		this.median_earnings = earn;
	}
	
	public int getMedianEarningsEdu(String edu) {
		if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
				edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
			return median_earnings_bach;
		}
		else if(edu.toLowerCase().equals("grad degree") || edu.toLowerCase().equals("graduate's degree") ||
				edu.toLowerCase().equals("graduate's") || edu.toLowerCase().equals("graduate degree")) {
			return median_earnings_grad;
		}
		else {
			throw new IllegalArgumentException("Education expected: bachelor's degree, graduate degree");
		}
	}
	
	public void setMedianEarningsEdu(int earn, String edu) {
		if(earn < 0) {
			throw new IllegalArgumentException("Earnings can't be negative");
		}
		else if(edu.toLowerCase().equals("bach degree") || edu.toLowerCase().equals("bachelor's degree") ||
				edu.toLowerCase().equals("bachelor's") || edu.toLowerCase().equals("bachelors degree")) {
			this.median_earnings_bach = earn;
		}
		else if(edu.toLowerCase().equals("grad degree") || edu.toLowerCase().equals("graduate's degree") ||
				edu.toLowerCase().equals("graduate's") || edu.toLowerCase().equals("graduate degree")) {
			 this.median_earnings_grad = earn;
		}
		else {
			throw new IllegalArgumentException("Education expected: bachelor's degree, graduate degree");
		}
	}
	
	public int getNumHouseholds() {
		return num_households;
	}
	
	public void setNumHouseholds(int num) {
		if(num < 0) {
			throw new IllegalArgumentException("Number of households can't be negative");
		}
		this.num_households = num;
	}
	
	public int getFamHouseholds() {
		return fam_households;
	}
	
	public void setFamHouseholds(int num) {
		if(num < 0) {
			throw new IllegalArgumentException("Number of households can't be negative");
		}
		this.fam_households = num;
	}
	
	public double getPctFamilyHouseholds() {
		return pct_households_fam;
	}
	
	public void setPctFamilyHouseholds(double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		this.pct_households_fam = pct;
	}
	
	public double getAvgFamSize() {
		return avg_fam_size;
	}
	
	public void setAvgFamSize(double avg) {
		if(avg < 0) {
			throw new IllegalArgumentException("Average Family Size can't be negative");
		}
		this.avg_fam_size = avg;
	}
	
	public int getNumFamU18Child() {
		return num_fam_u18_child;
	}
	
	public void setNumFamU18Child(int num) {
		if(num < 0) {
			throw new IllegalArgumentException("Number of households can't be negative");
		}
		this.num_fam_u18_child = num;
	}
	
	public double getPctUnmarriedSex(String pref) {
		if(pref.toLowerCase().equals("same") || pref.toLowerCase().equals("same sex") || pref.toLowerCase().equals("gay")) {
			return pct_household_same_sex;
		}
		else if(pref.toLowerCase().equals("opposite") || pref.toLowerCase().equals("opposite sex") || 
				pref.toLowerCase().equals("opp") || pref.toLowerCase().equals("straight")) {
			return pct_household_opp_sex;
		}
		else {
			throw new IllegalArgumentException("Expected same or opp");
		}
	}
	
	public void setPctUnmarriedSex(double pct, String pref) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else if(pref.toLowerCase().equals("same") || pref.toLowerCase().equals("same sex") || pref.toLowerCase().equals("gay")) {
			this.pct_household_same_sex = pct;
		}
		else if(pref.toLowerCase().equals("opposite") || pref.toLowerCase().equals("opposite sex") || 
				pref.toLowerCase().equals("opp") || pref.toLowerCase().equals("straight")) {
			this.pct_household_opp_sex = pct;
		}
		else {
			throw new IllegalArgumentException("Expected same or opp");
		}
	}
	
	public double getPctOneUnitStruct() {
		return pct_one_unit_struct;
	}
	
	public void setPctOneUnitStruct(double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		this.pct_one_unit_struct = pct;
	}
	
	public double getPctTwoPlusUnitStruct() {
		return pct_two_plus_unit_struct;
	}
	
	public void setPctTwoPlusUnitStruct(double pct) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		this.pct_two_plus_unit_struct = pct;
	}
	
	public double getPctOcc(String occ) {
		if(occ.toLowerCase().equals("owner") || occ.toLowerCase().equals("own")) {
			return pct_units_owner_occ;
		}
		else if(occ.toLowerCase().equals("renter") || occ.toLowerCase().equals("rent")) {
			return pct_units_renter_occ;
		}
		else {
			throw new IllegalArgumentException("Expected owner or renter");
		}
	}
	
	public void setPctOcc(double pct, String occ) {
		if(pct < 0 || pct > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
		else if(occ.toLowerCase().equals("owner") || occ.toLowerCase().equals("own")) {
			this.pct_units_owner_occ = pct;
		}
		else if(occ.toLowerCase().equals("renter") || occ.toLowerCase().equals("rent")) {
			this.pct_units_renter_occ = pct;
		}
		else {
			throw new IllegalArgumentException("Expected owner or renter");
		}
	}
	

	@Override
	public List<DbTableObject> getDelegateObjects() {
		// TODO Auto-generated method stub
		return null;
	}
	
}