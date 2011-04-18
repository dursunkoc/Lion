package com.aric.ruleengine.operators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.aric.ruleengine.operators.Equal;
import com.aric.ruleengine.operators.IOperator;

public class EqualTest {
	private static IOperator op;

	public class SampleModelObject {
		private String name;private Double wage;private Integer age;private Long id;
		public SampleModelObject(Integer age, Long id, String name, Double wage) {this.age=age;this.id = id;this.name = name;this.wage = wage;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public Double getWage() {return wage;}
		public void setWage(Double wage) {this.wage = wage;}
		public Integer getAge() {return age;}
		public void setAge(Integer age) {this.age = age;}
		public Long getId() {return id;}
		public void setId(Long id) {this.id = id;}
		
		
		@Override
		public boolean equals (Object o) {
			if (o instanceof SampleModelObject) {
				SampleModelObject a = (SampleModelObject) o;
				return this.age.equals(a.age) &&
				this.id.equals(a.id) &&
				this.name.equals(a.name)&&
				this.wage.equals(a.wage);	
			}
			return false;
		}
	}

	@BeforeClass
	public static void beforeClass() {
		op = (IOperator) new Equal();
	}

	@Test
	public void testCompareIntegerIntegerWhenNotEqual() {
		Integer i1 = Integer.parseInt("1");
		Integer i2 = Integer.parseInt("2");
		boolean compare = op.compare(i1, i2);
		Assert.assertFalse(compare);
	}

	@Test
	public void testCompareIntegerIntegerWhenEqual() {
		Integer i1 = Integer.parseInt("12");
		Integer i2 = Integer.parseInt("12");
		boolean compare = op.compare(i1, i2);
		Assert.assertTrue(compare);
	}

	@Test
	public void testCompareStringStringWhenNotEqual() {
		String s1 = "Veli";
		String s2 = "Ali";
		boolean compare = op.compare(s1, s2);
		Assert.assertFalse(compare);
	}

	@Test
	public void testCompareStringStringWhenEqual() {
		String s1 = "Veli";
		String s2 = "Veli";
		boolean compare = op.compare(s1, s2);
		Assert.assertTrue(compare);
	}

	@Test
	public void testCompareDateDateWhenEqual() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date d1 = dateFormat.parse("23.07.1919 13:47:56");
		Date d2 = dateFormat.parse("23.07.1919 13:47:56");
		boolean compare = op.compare(d1, d2);
		Assert.assertTrue(compare);

		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		d1 = dateFormat.parse("23.07.1919");
		d2 = dateFormat.parse("23.07.1919");
		compare = op.compare(d1, d2);
		Assert.assertTrue(compare);

		dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		d1 = dateFormat.parse("23.07.1919 00:00:00");
		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		d2 = dateFormat.parse("23.07.1919");
		compare = op.compare(d1, d2);
		Assert.assertTrue(compare);
	}
	
	@Test
	public void testCompareDateDateWhenNotEqual() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date d1 = dateFormat.parse("23.07.1919 13:47:56");
		Date d2 = dateFormat.parse("23.07.1908 13:47:56");
		boolean compare = op.compare(d1, d2);
		Assert.assertFalse(compare);
	}
	
	@Test
	public void testCompareEqualsImplementedEqualsImplementedWhenEqual() {
		SampleModelObject model1 = new SampleModelObject(23,426L,"Dursun",10000.0);
		SampleModelObject model2 = new SampleModelObject(23,426L,"Dursun",10000.0);
		boolean compare = op.compare(model1, model2);
		Assert.assertTrue(compare);
	}
	@Test
	public void testCompareEqualsImplementedEqualsImplementedWhenNotEqual() {
		SampleModelObject model1 = new SampleModelObject(23,426L,"Dursun",10000.0);
		SampleModelObject model2 = new SampleModelObject(27,426L,"Dursun",10000.0);
		boolean compare = op.compare(model1, model2);
		Assert.assertFalse(compare);
	}

}
