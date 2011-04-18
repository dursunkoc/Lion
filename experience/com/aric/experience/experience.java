/**
 * 
 */
package com.aric.experience;

import com.aric.esb.environment.Environment;
import com.aric.esb.environment.VariableFactory;
import com.aric.ruleengine.statement.ELSupportedStatement;
import com.aric.ruleengine.statement.StatementResult;

/**
 * @author Dursun KOC
 * 
 */
public class experience {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * TODO Environment bir interface haline gelmeli ve ELSupportedStatement
		 * in Environment daha özel olmalı.
		 */
		ELSupportedStatement statement = new ELSupportedStatement(
				"env['dursun'].value.name=='Dursun'");
		Environment environment = new Environment();
		Person dursun = new Person("Dursun", "KOÇ");
		environment.addVariable("dursun", VariableFactory.newInstance(dursun));
		StatementResult execute = statement.execute(environment);
		System.out.println(execute.getResult());
	}

	private static class Person {
		private String name;
		private String surname;

		/**
		 * @param name
		 * @param surname
		 */
		public Person(String name, String surname) {
			this.name = name;
			this.surname = surname;
		}

		public String getName() {
			return name;
		}

		public String getSurname() {
			return surname;
		}
	}
}
