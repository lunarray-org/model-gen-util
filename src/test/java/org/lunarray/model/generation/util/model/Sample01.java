/* 
 * Model Tools.
 * Copyright (C) 2013 Pal Hargitai (pal@lunarray.org)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lunarray.model.generation.util.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.lunarray.model.descriptor.model.annotations.Embedded;
import org.lunarray.model.descriptor.model.annotations.Key;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.EntityPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;

@EntityPresentationHint(descriptionKey = "Sample01.name", resourceBundle = "org.lunarray.model.generation.util.test.Labels")
public class Sample01
		implements Serializable {

	public static final List<Sample01> DATA;
	public static final Sample01 SAMPLE_01;
	public static final Sample01 SAMPLE_02;
	public static final Sample01 SAMPLE_03;

	/** Serial id. */
	private static final long serialVersionUID = 5150265041358441709L;

	@Embedded
	private Sample02 inlineValue = new Sample02();

	@PresentationHint(render = RenderType.DROPDOWN, inLine = BooleanInherit.TRUE)
	private Sample02 inlineValue2 = Sample02.SAMPLE_02;

	@PresentationHint(labelKey = "Sample01.sampleEnum", render = RenderType.RADIO)
	private SampleEnum sampleEnum = SampleEnum.VALUE_02;

	@PresentationHint(render = RenderType.DATE_PICKER)
	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint = @PresentationHint(visible = BooleanInherit.TRUE, render = RenderType.TIME_PICKER)) })
	private Date someCalendar;

	@PresentationHint(render = RenderType.DATE_TIME_PICKER, format = "ddmmyyyy")
	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(visible = BooleanInherit.TRUE)) })
	private Date someDate = new Date();

	private boolean testBoolean;

	@PresentationHint(labelKey = "Sample01.testValue", immutable = BooleanInherit.TRUE)
	@QualifierPresentationHints({
			@QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(immutable = BooleanInherit.FALSE)),

			@QualifierPresentationHint(name = Qualifier02.class, hint = @PresentationHint(visible = BooleanInherit.TRUE)) })
	private String testValue = "value1";

	private final Integer testValue2 = 5;

	@Key
	@PresentationHint(labelKey = "Sample01.testValue3", render = RenderType.TEXT_AREA)
	private String testValue3;

	private Integer testValue4 = 5;

	static {
		SAMPLE_01 = new Sample01();
		Sample01.SAMPLE_01.setInlineValue(Sample02.SAMPLE_01);
		Sample01.SAMPLE_01.setInlineValue2(Sample02.SAMPLE_02);
		Sample01.SAMPLE_01.setSampleEnum(SampleEnum.VALUE_01);
		Sample01.SAMPLE_01.setSomeCalendar(new Date());
		Sample01.SAMPLE_01.setSomeDate(new Date());
		Sample01.SAMPLE_01.setTestBoolean(false);
		Sample01.SAMPLE_01.setTestValue("Test01");
		Sample01.SAMPLE_01.setTestValue("Test03");
		SAMPLE_02 = new Sample01();
		Sample01.SAMPLE_02.setInlineValue(Sample02.SAMPLE_03);
		Sample01.SAMPLE_02.setInlineValue2(Sample02.SAMPLE_01);
		Sample01.SAMPLE_02.setSampleEnum(SampleEnum.VALUE_02);
		Sample01.SAMPLE_02.setSomeCalendar(new Date());
		Sample01.SAMPLE_02.setSomeDate(new Date());
		Sample01.SAMPLE_02.setTestBoolean(false);
		Sample01.SAMPLE_02.setTestValue("Test04");
		Sample01.SAMPLE_02.setTestValue("Test06");
		SAMPLE_03 = new Sample01();
		Sample01.SAMPLE_03.setInlineValue(Sample02.SAMPLE_02);
		Sample01.SAMPLE_03.setInlineValue2(Sample02.SAMPLE_03);
		Sample01.SAMPLE_03.setSampleEnum(SampleEnum.VALUE_02);
		Sample01.SAMPLE_03.setSomeCalendar(new Date());
		Sample01.SAMPLE_03.setSomeDate(new Date());
		Sample01.SAMPLE_03.setTestBoolean(true);
		Sample01.SAMPLE_03.setTestValue("Test07");
		Sample01.SAMPLE_03.setTestValue("Test09");
		DATA = new LinkedList<Sample01>();
		Sample01.DATA.add(Sample01.SAMPLE_01);
		Sample01.DATA.add(Sample01.SAMPLE_02);
		Sample01.DATA.add(Sample01.SAMPLE_03);
	}

	public String echoMethod(final String argument) {
		return argument;
	}

	public String echoMethodException(final String argument) {
		throw new IllegalArgumentException("Test exception.");
	}

	public Integer echoMethodInt(final Integer argument) {
		return argument;
	}

	public List<Integer> echoMethodList(final List<Integer> argument) {
		return argument;
	}

	public Sample02 echoMethodMapped(final Sample02 argument) {
		return argument;
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public Sample02 getInlineValue() {
		return this.inlineValue;
	}

	public Sample02 getInlineValue2() {
		return this.inlineValue2;
	}

	public SampleEnum getSampleEnum() {
		return this.sampleEnum;
	}

	public Date getSomeCalendar() {
		return this.someCalendar;
	}

	public Date getSomeDate() {
		return this.someDate;
	}

	public String getTestValue() {
		return this.testValue;
	}

	public Integer getTestValue2() {
		return this.testValue2;
	}

	public String getTestValue3() {
		return this.testValue3;
	}

	/**
	 * Gets the value for the testValue4 field.
	 * 
	 * @return The value for the testValue4 field.
	 */
	public Integer getTestValue4() {
		return this.testValue4;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean isTestBoolean() {
		return this.testBoolean;
	}

	public void setInlineValue(final Sample02 inlineValue) {
		this.inlineValue = inlineValue;
	}

	public void setInlineValue2(final Sample02 inlineValue2) {
		this.inlineValue2 = inlineValue2;
	}

	public void setSampleEnum(final SampleEnum sampleEnum) {
		this.sampleEnum = sampleEnum;
	}

	public void setSomeCalendar(final Date someCalendar) {
		this.someCalendar = someCalendar;
	}

	public void setSomeDate(final Date someDate) {
		this.someDate = someDate;
	}

	public void setTestBoolean(final boolean testBoolean) {
		this.testBoolean = testBoolean;
	}

	public void setTestValue(final String testValue) {
		this.testValue = testValue;
	}

	public void setTestValue3(final String testValue3) {
		this.testValue3 = testValue3;
	}

	/**
	 * Sets a new value for the testValue4 field.
	 * 
	 * @param testValue4
	 *            The new value for the testValue4 field.
	 */
	public void setTestValue4(final Integer testValue4) {
		this.testValue4 = testValue4;
	}
}
